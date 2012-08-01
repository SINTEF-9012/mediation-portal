/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.portal
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
package net.modelbased.mediation.service.repository.mapping

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
import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._

import java.text.SimpleDateFormat

/**
 * Simple Integration test for the Mapping Repository service.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MappingRepositoryIT extends SpecificationWithJUnit with HttpSpraySupport {

    
  val httpClientName = "test-mapping-repository"

  val MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings"

  "The mapping repository " should {

    /**
     * Here we test that the mapping repository returns the list of existing
     * mapping, by sending a GET a the repository URL, and checking that we retrieve
     * a list (potentially empty)
     */
    "returns a list of existing mappings" in {
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Get(MAPPING_REPOSITORY_URL))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case l: List[String] => ok
      }
    }

    /**
     * Here we POST a new mapping at repository URL. We ensure that the URL we get
     * as an answer points effectively towards the mapping that we pushed
     */
    "store properly new mappings" in {
      val formatter = new SimpleDateFormat("yyMMddHHmmss")
      val mapping = new Mapping()
      mapping.add(new Entry("source.foo", "target.bar", 0.34, httpClientName))
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest[MappingData] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(MAPPING_REPOSITORY_URL, mapping))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case url: String => {
          println("THE URL:" + url)
          val conduit2 = new HttpConduit(httpClient, "localhost", 8080) {
            val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[MappingData] }
          }
          val futureModel = conduit2.pipeline(Get(url))
          Await.result(futureModel, intToDurationInt(5) seconds) must beLike {
            case m: MappingData =>
              m must_== fromMapping(mapping)
          }
        }
      }
    }

  }

}