/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.client
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
package net.modelbased.mediation.client

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

import net.modelbased.mediation.service.aggregator
import net.modelbased.mediation.service.aggregator.RequestJsonProtocol._

/**
 * Client API for the Aggregator service
 */
trait Aggregator extends Portal {

   self: ModelRepository => // Dependencies on ModelRepository

   val AGGREGATOR = "/aggregator"

   /**
    * Invoke the aggregator service and return the URL of the resulting model
    *
    * @param result the name of the result model
    *
    * @param parts a list of couple (m,p) where m is the model ID and p is the
    * name of the package in which this model must be wrapped
    *
    * @return the URL of the resulting model
    */
   def aggregate(result: String, parts: List[(String, String)]): String = {
      val request = new aggregator.Request(result, parts.map { case (m, p) => new aggregator.Part(m, p) })
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
         val pipeline = { simpleRequest[aggregator.Request] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(MODEL_REPOSITORY, request))
      Await.result(futureUrl, intToDurationInt(5) seconds)

   }

}