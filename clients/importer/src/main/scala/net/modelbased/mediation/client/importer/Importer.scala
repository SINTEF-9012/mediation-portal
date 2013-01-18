/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: root
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
package net.modelbased.mediation.client.importer

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

import net.modelbased.mediation.client.repository.model.ModelRepository

import net.modelbased.mediation.service.importer._
import net.modelbased.mediation.service.importer.RequestJsonProtocol._

/**
 * Client API for the importer service
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
trait Importer extends Portal {

   val IMPORTER_URL = "/sensapp/importer"

   /**
    * Import a model in the repository, taking care of the needed conversion if
    * possible.
    * 
    * @param modelId the id that will be used to retrieve the model from the repository
    * 
    * @param description a short description of what this model is about
    * 
    * @param format the current format of the model
    * 
    * @param content the content of the model as a string
    */
   def importModel(modelId: String, description: String, format: Format.FormatValue, content: String) = {
      val request = new Request(modelId, description, format.label, content) 
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest[Request] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(IMPORTER_URL, request))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Retrieve all the supported format, which can be imported
    *
    * @return the list of supported formats
    */
   def supportedFormats: List[Format.FormatValue] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Format.FormatValue]] }
      }
      val futureUrl = conduit.pipeline(Get(IMPORTER_URL ++ "/formats", None))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

}