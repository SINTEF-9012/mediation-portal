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
package net.modelbased.mediation.service.repository.model

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

import net.modelbased.mediation.service.mediator._
import net.modelbased.mediation.service.mediator.RequestJsonProtocol._

import net.modelbased.mediation.service.repository.model._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._

import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._

import java.text.SimpleDateFormat

class MediatorIT extends SpecificationWithJUnit with HttpSpraySupport {

  val httpClientName = "test-mapping-repository"

  object Urls {

    val MODEL_REPOSITORY = "/mediation/repositories/models"

    val MAPPING_REPOSITORY = "/mediation/repositories/mappings"

    val MEDIATOR = "/mediator"
  }

  val modelName = "test-mediator"

  val sourceXsd = XML.load("src/test/resources/schemas/document.xsd")

  val targetXsd = XML.load("src/test/resources/schemas/article.xsd")

  "The Mediator" should {

    "populate the mapping repository" in {
      // Push the source model
      println("Pushing the source model")
      val formatter = new SimpleDateFormat("yyMMddHHmmssSS")
      val source = new Model(modelName + formatter.format(new java.util.Date()), sourceXsd.toString())
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
        val pipeline = { simpleRequest[Model] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(Urls.MODEL_REPOSITORY, source))
      Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
        case url: String => {
          // 
          // We push the target model
          println("OK, URL = " + url)
          println("Pushing the target model")
          val target = new Model(modelName + formatter.format(new java.util.Date()), targetXsd.toString())
          val conduit = new HttpConduit(httpClient, "localhost", 8080) {
            val pipeline = { simpleRequest[Model] ~> sendReceive ~> unmarshal[String] }
          }
          val futureUrl = conduit.pipeline(Post(Urls.MODEL_REPOSITORY, target))
          Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
            case url: String => {
              //
              // We trigger the mediation
              println("OK, URL = " + url)
              println("Triggering mediation")
              val mediationRequest = new Request(source.name, target.name, "xsd-syntactic")
              val conduit = new HttpConduit(httpClient, "localhost", 8080) {
                val pipeline = { simpleRequest[Request] ~> sendReceive ~> unmarshal[String] }
              }
              val futureUrl = conduit.pipeline(Post(Urls.MEDIATOR, mediationRequest))
              Await.result(futureUrl, intToDurationInt(5) seconds) must beLike {
                case url: String => {
                  // 
                  // We retrieve the mapping
                  println("OK, URL = " + url)
                  println("Fetching resulting mapping")
                  val conduit = new HttpConduit(httpClient, "localhost", 8080) {
                    val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[MappingData] }
                  }
                  val futureMapping = conduit.pipeline(Get(url))
                  Await.result(futureMapping, intToDurationInt(5) seconds) must beLike {
                    case md: MappingData => ok
                  }
                }
              }
            }
          }
        }
      }
    }
  }

}