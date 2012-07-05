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

import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.DefaultUnmarshallers._
import net.modelbased.sensapp.library.system._

import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.comparison.data.{ Comparison, Evaluation }

/**
 * This singleton implements the comparator service. It accepts a mapping oracle,
 * and a list of mappings to evaluate against the oracle, performs the needed
 * comparisons and store them at a specific URL in the comparison repository.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(val partners: PartnerHandler) extends HttpSpraySupport {

  private[this] val MAPPING_REPOSITORY_URL = "/mediation/repository/mappings"
  private[this] val COMPARISON_REPOSITORY_URL = "/mediation/repositories/compositions"

  val httpClientName = "comparator"

  /**
   * Fetch all the mappings, and trigger all the needed comparisons
   */
  def compare(request: Request) = {
    var result: List[String] = Nil
    val oracle = fetch(request.oracle)
    for (uri <- request.toCompare) {
      val model = this.fetch(uri)
      val eval = evaluate(oracle, model)
      val response = publishEvaluation(eval)

    }
  }

  /**
   * Fetch a given mapping, form its URI.
   */
  private[this] def fetch(uri: String): Mapping =
    null

  def publishEvaluation(evaluation: Evaluation): Future[String] = {
    val repository = partners("comparison-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
    }
    conduit.pipeline(Post(COMPARISON_REPOSITORY_URL, None))
      .onSuccess {
        case x => conduit.close(); x
      } 
      .onFailure {
        case e: UnsuccessfulResponseException => {
          conduit.close
          system.log.info("Exception while sending evaluation data [" + COMPARISON_REPOSITORY_URL + "]: " + e.responseStatus)
          throw new RuntimeException("Unable to reach comparison repository [" + COMPARISON_REPOSITORY_URL + "]")
        }
      }
  }

  /**
   * Compare one mapping against the oracle and returns the associated report
   *
   * @param oracle the mapping that will be considered as correct during the evaluation
   *
   * @param subject the mapping that must be compared to the oracle
   *
   * @return a report describing how close is the subject from the oracle
   */
  def evaluate(oracle: Mapping, subject: Mapping): Evaluation = {
    null
  }
    

}