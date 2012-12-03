/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.aggregator
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
package net.modelbased.mediation.service.aggregator

import akka.dispatch._

import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._

import net.modelbased.sensapp.library.system._
import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.library.algorithm.mof._
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader
import net.modelbased.mediation.library.algorithm.mof.printer.MofPrinter
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository

/**
 * Implementation of the aggregator service. It basically fetches all models in
 * the model repository and then generate the resulting model, and eventually
 * pushes it back in the model repository;
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
class Runner(partners: PartnerHandler) extends HttpSpraySupport {
   
   val httpClientName = "aggregator"
   
   val (host, port) = partners("model-repository").get
   val portal = new Portal(host, port) with ModelRepository    
      
   private[this] val MODEL_REPOSITORY_URL = "/mediation/repositories/models"

   /**
    * Process the request. It create a MofPackage, and for each parts specified
    * in the request, it fetch the related model, parse its content, append it to
    * the newly create package as a separated sub package. Then it create a new
    * model whose content s the pretty print of the model
    *
    *  @param request the request to process
    *
    *  @return result the URL of the aggregated model
    */
   def process(request: Request): String = {
      // Create a new Package
      var root = new Package("aggregation")

      request.parts.foreach {
         part =>
            // Retrieve the model
            val model = portal.fetchModelById(part.modelId) 

            // Parse le modele
            val reader = new MofReader();
            val result = reader.readPackage(model.content)
            result match {
               case Right(thePackage) =>
                  var localPackage = new Package(part.packageName)
                  localPackage.addElement(thePackage)
                  root.addElement(localPackage)
               case Left(errors) =>
                  errors.foreach{ e => println(e) }
                  throw new IllegalArgumentException("The model is ill-formed!") // FIXME refactor this exception 
            }
      }

      // Pretty print the result
      val printer = new MofPrinter()
      val content = root.accept(printer, new StringBuilder()).result()

      // Build the resulting model
      var model = new Model(name = request.resultId,
         description = makeDescription(request),
         kind = "text/mof",
         content = content)

      // Store the model
      portal.storeModel(model)
   }

   /**
    * Generate a description for the aggregation of several files
    *
    * @param request the request describing the aggregation
    *
    * @return a string describing the aggregation
    */
   private[this] def makeDescription(request: Request): String =
      "Aggregation from " + request.parts.map { p => p.modelId }.mkString(", ")



}