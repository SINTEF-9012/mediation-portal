/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.comparator
 *
 * SensApp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SensApp is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with SensApp. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package net.modelbased.mediation.service.comparator

import net.modelbased.sensapp.library.system._

import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._
import cc.spray.json.DefaultJsonProtocol._

import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

/**
 * This singleton implements the comparator service. It accepts a mapping oracle,
 * and a list of mappings to evaluate against the oracle, fetch all mappings,
 * performs the needed comparisons and store them at a specific URL in the
 * comparison repository.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(val partners: PartnerHandler) extends HttpSpraySupport {

  private[this] val MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings"
  private[this] val COMPARISON_REPOSITORY_URL = "/mediation/repositories/comparisons"

  val httpClientName = "comparator"

    
  /**
   * Fetch all the mappings, and trigger all the needed comparisons
   */
  def process(request: Request): String = {
    var result: List[String] = Nil
    val oracle = fetch(request.oracle)
    
    val evaluations = request.toCompare.foldLeft(List[Evaluation]()){ 
      (acc, v) =>
      	val model = this.fetch(v)
      	model.evaluateAgainst(oracle) :: acc
    }
    publishEvaluation(evaluations)
    
    return COMPARISON_REPOSITORY_URL + "/" + request.oracle
  }


  /**
   * Fetch a given mapping, form its URI.
   *
   * @param mappingUid the identifier of the mapping to fetch
   *
   * @return the corresponding mapping object
   */
  private[this] def fetch(mappingUid: String): Mapping = {
    println("Fetching mapping '%s' ... ".format(mappingUid))
    val repository = partners("mapping-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest ~> sendReceive ~> unmarshal[MappingData] 
    }
    var r = conduit.pipeline(Get(MAPPING_REPOSITORY_URL + "/" + mappingUid, None))
    Await.result(r, 5 seconds) match {
      case m: MappingData => m
    }
  }

  
  /**
   * Publish a given evaluation on the comparison repository
   *
   * @param evaluation the evaluation that needs to be published
   *
   * @return the REST response as a string
   */
  private[this] def publishEvaluation(evaluations: List[Evaluation]): List[String] = {
    println("Publishing %d evaluations ... ".format(evaluations.size))
    val repository = partners("comparison-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest[List[Evaluation]] ~> sendReceive ~> unmarshal[List[String]]
    }
    var r = conduit.pipeline(Post(COMPARISON_REPOSITORY_URL, evaluations))
    Await.result(r, 5 seconds)
  }


}