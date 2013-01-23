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

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repositories.algorithm.AlgorithmRepository 
import net.modelbased.mediation.client.algorithm.{ Algorithm => AlgorithmInvoker }

/**
 * Implementation of the Mediator service as template method pattern
 *
 * @author Sebastien Mosser - SINTEF ICT
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(partners: PartnerHandler) extends HttpSpraySupport {

   val portal = {
      val (host, port) = partners("model-repository").get
      new Portal(host, port) with AlgorithmRepository with AlgorithmInvoker
   }

   val httpClientName = "mediator"


   /**
    * Process a mediation request: it fetches both the source and target models,
    * create an initial mapping, and run the given mediation algorithm
    */
   def process(request: Request): String = {
      val algo = portal.fetchAlgorithmById(request.algo)
      portal.invoke(algo, request.source, request.target)
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