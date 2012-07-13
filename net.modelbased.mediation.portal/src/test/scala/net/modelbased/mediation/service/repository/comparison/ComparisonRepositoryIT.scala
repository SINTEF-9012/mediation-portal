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
import net.modelbased.mediation.service.repository.model._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._
import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.comparison._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

/**
 * Simple integration test for the model repository. We merely push one model and
 * we ensure that the model is then available at the given URL.
 *
 * @note Running these tests require a MongoDB instance running locally
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ComparisonRepositoryIT extends SpecificationWithJUnit with HttpSpraySupport {

  val httpClientName = "test-model-repository"

  val MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings"

  val COMPARISON_REPOSITORY_URL = "/mediation/repositories/comparisons"

  val modelName = "test-model-"

  val xsd = XML.load("src/test/resources/schemas/document.xsd")

  "The Comparison Repository" should {

    /**
     * Here we just send GET to the repository URL and ensure that we receive
     * a list of URL (potentially empty)
     */
    "Returns the list of stored comparison" in {
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY_URL))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case l: List[String] => ok
      }
    }

    /**
     * We create a new dummy comparison and we PUSH it to the repository URL. We
     * then ensure that a comparison is indeed available at the given URL
     */
    "Store a new comparison" in {
      val oracle = MatcherMock.mapping
      val toEvaluate = MatcherMock.mapping
      val evaluation = toEvaluate.evaluateAgainst(oracle)
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest[List[Evaluation]] ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Post(COMPARISON_REPOSITORY_URL, List(evaluation)))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case urls: List[String] => {
          urls.find(x => x.contains(oracle.uid)) match {
            case None => ko
            case Some(url) => {
              println("THE URL:" + url)
              if (url != "/sensapp" + COMPARISON_REPOSITORY_URL + "/" + oracle.uid) {
                ko
              } else {
                val conduit2 = new HttpConduit(httpClient, "localhost", 8080) {
                  val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Evaluation]] }
                }
                val futureModel = conduit2.pipeline(Get(url))
                Await.result(futureModel, intToDurationInt(5) seconds) must beLike {
                  case loe: List[Evaluation] =>
                    loe must contain(evaluation)
                }
              }
            }
          }
        }
      }
    }

  }

}