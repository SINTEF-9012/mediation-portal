/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.mediator
 *
 * Mediation Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Mediation Portal is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Mediation Portal. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package net.modelbased.mediation.service.mediator

import akka.dispatch._

import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._

import net.modelbased.sensapp.library.system._
import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._

/**
 * Implementation of the Mediator service as template method pattern
 *
 * @author Sebastien Mosser - SINTEF ICT
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(partners: PartnerHandler) extends HttpSpraySupport {

  /**
   * Define the URL used in this settings
   */
  object Urls {
    val MAPPING_REPOSITORY = "/mediation/repositories/mappings"

    val MODEL_REPOSITORY = "/mediation/repositories/models"
  }

  val httpClientName = "mediator"

  /**
   * Bind each mediation algorithm to a specific name. This is definitely quick
   * and dirty
   *
   * @todo refactor
   */
  private[this] val mediations: Map[String, Mediation] = Map(
    "xsd-random" -> new RandomXsdMediation(), 
    "xsd-syntactic" -> new SyntacticXsdMediation()) 
    
    
  /**
   * @return the list of mediation algorithm supported by the mediator service
   */
  def algorithms: List[String] =
     mediations.keySet.toList

  /**
   * Query the model repository to retrieve a model from the given model id. This
   * is a synchronous method that will timeout after 5 second, the model repository
   * does not respond.
   *
   * @param modelId the ID of the needed model
   *
   * @return the model whose id match the given one, or none if there is no model
   * with such an ID.
   */
  private[this] def fetch(modelId: String): Option[Model] = {
    val repository = partners("model-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest ~> sendReceive ~> unmarshal[Model]
    }
    val result = conduit.pipeline(Get(Urls.MODEL_REPOSITORY + "/" + modelId, None))
    try {
      Some(Await.result(result, 5 seconds))

    } catch {
      case e: Exception => None

    }
  }

  /**
   * Publish a mapping in the repository
   *
   * @param mapping the mapping that must be published
   *
   * @return a string representing the status of the publication
   */
  private[this] def publishMapping(mapping: Mapping): String = {
    val repository = partners("mapping-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest[MappingData] ~> sendReceive ~> unmarshal[String]
    }
    val result = conduit.pipeline(Post(Urls.MAPPING_REPOSITORY, mapping)) 
    Await.result(result, 5 seconds)
  }

  /**
   * Process a mediation request: it fetches both the source and target models,
   * create an initial mapping, and run the given mediation algorithm
   */
  def process(request: Request): Either[String, Mapping] = {
    println("Fetching source ...")
    fetch(request.source) match {
      case None => Left("Unable to retrieve the source model '%s' from the repository.".format(request.source))
      case Some(source) =>
        println("OK, fetching target ...")
        fetch(request.target) match {
          case None => Left("Unable to retrieve the target model '%s' from the repository.".format(request.target))
          case Some(target) =>
            mediations.get(request.algo) match {
              case None => Left("Unknown algorithm '%s' (existing algorithms are %s)".format(request.algo, mediations.keys.mkString(", ")))
              case Some(mediation) =>
                println("OK, running mediation ...");
                val result = mediation(new Mapping(), source, target)
                println("mapping: " + result.toString)
                println("OK, publishing mapping ...");
                publishMapping(result)
                println("OK, Medition Complete!");
                Right(result)
            }
        }
    }
  }

} 