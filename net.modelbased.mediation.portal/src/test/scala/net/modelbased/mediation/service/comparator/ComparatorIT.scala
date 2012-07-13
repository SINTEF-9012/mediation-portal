/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.portal
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

import org.specs2.mutable._
import scala.xml._
import scala.io.Source
import net.modelbased.sensapp.library.system._
import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._
import cc.spray.json.DefaultJsonProtocol._
import net.modelbased.mediation.service.comparator._
import net.modelbased.mediation.service.comparator.RequestJsonProtocol._
import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.comparison._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

/**
 * Simple integration test for the Comparator service. We ensure that given a set
 * of models, the comparator service performs properly the needed comparisons and
 * effectively stores the result in the comparison repository.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ComparatorIT extends SpecificationWithJUnit with HttpSpraySupport {

  val httpClientName = "test-comparator"

  val MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings"

  val COMPARISON_REPOSITORY_URL = "/mediation/repositories/comparisons"

  val COMPARATOR_URL = "/comparator"

    
  "The comparator service" should {

    /*
     * We create some fake mappings and push them into the mapping repository
     */
    "Push the proper evaluation in to the comparison repository" in {
      // Create an oracle and 10 mapping to compare with
      println("Creating fake mappings ...")
      val oracle = MatcherMock.mapping
      val toCompare = for (i <- 1 to 10) yield MatcherMock.mapping

      // Push all these mapping into the mapping repository
      forall(oracle :: toCompare.toList){ mapping =>
        println("OK, pushing mapping '" + mapping.uid + "' ...")
        val conduit = new HttpConduit(httpClient, "localhost", 8080) {
          val pipeline = { simpleRequest[MappingData] ~> sendReceive ~> unmarshal[String] }
        }
        val futureUrl = conduit.pipeline(Post(MAPPING_REPOSITORY_URL, mapping))
        Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
          case s: String => ok
        }
      }

      // Trigger the comparison of the the oracle against the other mappings
      println("OK, Trigerring the comparison ... ")
      val request = new Request(oracle.uid, toCompare.toList.map { x => x.uid }, "Comparator integration test")
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest[Request] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(COMPARATOR_URL, request))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case url: String => {
          // We check whether there all the comparison is available
          println("The URL is " + url)
          println("OK, Fetching comparison from the repository ...")
          val conduit = new HttpConduit(httpClient, "localhost", 8080) {
            val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Evaluation]] }
          }
          val futureUrl = conduit.pipeline(Get(url))
          Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
            case e: List[Evaluation] => e.size must_== toCompare.size
          }

          // We try to fetch all comparisons
          forall(toCompare)({ mapping =>
            println("OK, fetching evaluation of mapping '" + mapping.uid + "' ...")
            val conduit = new HttpConduit(httpClient, "localhost", 8080) {
              val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Evaluation] }
            }
            val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY_URL + "/" + oracle.uid + "/" + mapping.uid))
            Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
              case c: Evaluation => ok
            }
          })

        }
      }

    }

  }

}