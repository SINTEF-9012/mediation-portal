/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.client.comparator
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
package net.modelbased.mediation.client.comparator

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

import net.modelbased.mediation.service.comparator.Request
import net.modelbased.mediation.service.comparator.RequestJsonProtocol._

/**
 * Client API for the Comparator service
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
trait Comparator extends Portal {
 
   val COMPARATOR_URL = "/comparator"

   /**
    * Invoke the comparator service
    * 
    * @param oracleId the UID of the mapping that should be considered as the 
    * oracle
    * 
    * @param mappingIds the list mappings UID that should be compared against the
    * oracle
    * 
    * @param note a note to explain the meaning/context of this comparison
    * 
    * @return the URL of the resulting comparison as a string
    */
   def compare(oracleId: String, mappingIds: List[String], note: String): String = {
       val request = new Request(oracleId, mappingIds, note)
      val conduit = new HttpConduit(httpClient, host, port) {
        val pipeline = { simpleRequest[Request] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(COMPARATOR_URL, request))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }     
   
   
}