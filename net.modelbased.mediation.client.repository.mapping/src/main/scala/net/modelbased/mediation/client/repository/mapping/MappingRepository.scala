/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.client.repository.mapping
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
package net.modelbased.mediation.client.repository.mapping






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


import net.modelbased.mediation.client.portal.Portal

import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._


/**
 * Client API for the mapping repository
 *
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 *
 */
trait MappingRepository extends Portal {

   
   val MAPPING_REPOSITORY = "/mediation/repositories/mappings"
 
      
  /**
   * Publish a mapping in the repository
   *
   * @param mapping the mapping that must be published in the repository
   *
   * @return a string representing the status of the publication
   */
  def storeMapping(mapping: Mapping): String = {
    val conduit = new HttpConduit(httpClient, host, port) {
      val pipeline = simpleRequest[MappingData] ~> sendReceive ~> unmarshal[String]
    }
    val result = conduit.pipeline(Post(MAPPING_REPOSITORY, mapping)) 
    Await.result(result, 5 seconds)
  }
   
   
   
   
}