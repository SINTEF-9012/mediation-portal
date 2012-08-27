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

import net.modelbased.mediation.service.repository.model._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._


/**
 * Client API for the model repository
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 *
 */
trait ModelRepository extends Portal {

   val MODEL_REPOSITORY = "/mediation/repositories/models"

   
   /**
    * Store a new model in the model repository
    *
    * @param model the model that has to be stored in the repository
    *
    * @return the URL of the model in the repository
    */
   def storeModel(model: Model): String = {
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
         val pipeline = { simpleRequest[Model] ~> sendReceive ~> unmarshal[String] }
      }
      val futureUrl = conduit.pipeline(Post(MODEL_REPOSITORY, model))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Fetch the model stored at a given URL
    *
    * @param url the URL where the needed model is stored
    *
    * @return the resulting Model object
    */
   def fetchModelAt(url: String): Model = {
      val conduit = new HttpConduit(httpClient, "localhost", 8080) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Model] }
      }
      val futureUrl = conduit.pipeline(Post(url, None))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

}