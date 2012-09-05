/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.importer
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
package net.modelbased.mediation.service.importer

import akka.dispatch._

import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._

import net.modelbased.sensapp.library.system._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository

import net.modelbased.mediation.service.repository.model.data.Model

import net.modelbased.mediation.service.converter.Converter
import net.modelbased.mediation.service.converter.NoConverter
import net.modelbased.mediation.service.converter.xsd.XsdConverter

/**
 * Implementation of the importer service
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(partners: PartnerHandler) extends HttpSpraySupport {

   val httpClientName = "importer"

   private[this] val convertions = Map[Format.FormatValue, Converter](
      Format.XSD -> new XsdConverter(),
      Format.ECORE -> new NoConverter(),
      Format.TEXT -> new NoConverter());

   val modelRepository = {
      val (host, port) = partners("model-repository").get
      new Portal(host, port) with ModelRepository
   }

   /**
    * Process a request. This method bascially selects the proper internal method
    * that with relevant for the specified format defined in the request.
    *
    * @param request the request to process
    */
   def process(request: Request): String = {
      convertions.get(Format.withName(request.format)) match {
         case Some(convertion) =>
            val content = convertion(request.content)
            val model = new Model(request.modelId, "text/mof", request.description, content)
            modelRepository.storeModel(model)
         case None =>
            "Unsupported format: %s".format(request.format) 
      }
   }
   
}