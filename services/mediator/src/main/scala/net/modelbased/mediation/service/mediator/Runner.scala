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
package net.modelbased.mediation.service.mediator

import akka.dispatch._

import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._

import net.modelbased.sensapp.library.system._
import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.client.repository.mapping.MappingRepository

/**
 * Implementation of the Mediator service as template method pattern
 *
 * @author Sebastien Mosser - SINTEF ICT
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(partners: PartnerHandler) extends HttpSpraySupport {

   val modelRepository = {
      val (host, port) = partners("model-repository").get
      new Portal(host, port) with ModelRepository
   }

   val mappingRepository = {
      val (host, port) = partners("mapping-repository").get
      new Portal(host, port) with MappingRepository
   }

   val httpClientName = "mediator"

   /**
    * Bind each mediation algorithm to a specific name. This is definitely quick
    * and dirty
    *
    * @todo refactor
    */
   private[this] val mediations: Map[String, Mediation] = Map(
      "random" -> new RandomMatch(),
      "syntactic" -> new SyntacticMatch())

   /**
    * @return the list of mediation algorithm supported by the mediator service
    */
   def algorithms: List[String] =
      mediations.keySet.toList

   /**
    * Process a mediation request: it fetches both the source and target models,
    * create an initial mapping, and run the given mediation algorithm
    */
   def process(request: Request): String = {
      println("Fetching source ...")
      val source = modelRepository.fetchModelById(request.source)
      val target = modelRepository.fetchModelById(request.target)
      mediations.get(request.algo) match {
         case Some(mediation) =>
            val mapping = mediation(new Mapping(sourceId = source.name, targetId = target.name), source, target)
            mappingRepository.storeMapping(mapping)
         case _ =>
            throw new UnknownAlgorithmException(request.algo, mediations.keySet.toList)
      }
   } 

}

/**
 * Exception thrown when the given algorithm does not exist in the mediations table
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
case class UnknownAlgorithmException(val algorithm: String, val candidates: List[String]) extends Exception("Unknown algorithm '%s' (existing algorithms are %s)".format(algorithm, candidates.mkString(", "))) {

}