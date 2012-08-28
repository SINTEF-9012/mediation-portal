/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.client.mediator
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
package net.modelbased.mediation.client.mediator

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

import net.modelbased.mediation.client.portal.Portal;

import net.modelbased.mediation.service.mediator.Request
import net.modelbased.mediation.service.mediator.RequestJsonProtocol._

/**
 * Client API for the Mediator service
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait Mediator extends Portal {

   val MEDIATOR_URL = "/mediator"

   /**
    * Fetch the name of all mediation algorithm available in the mediator service
    *
    * @return the list of algorithm names
    */
   def fetchAlgorithmNames: List[String] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Get(MEDIATOR_URL ++ "/algorithms", None))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   
   /**
    * Invoke the mediator service
    *
    * @param sourceId the ID of the source model
    *
    * @param targetId the ID of the target model
    *
    * @param algorithm the name of the algorithm to use for the mediation
    *
    * @return the URL of the resulting mapping as a string
    */
   def mediate(sourceId: String, targetId: String, algorithm: String): String = {
      val mediationRequest = new Request(sourceId, targetId, algorithm)
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest[Request] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(MEDIATOR_URL, mediationRequest))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

}