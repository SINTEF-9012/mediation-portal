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
package net.modelbased.mediation.service.comparator

import net.modelbased.sensapp.library.system._

import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._
import cc.spray.json.DefaultJsonProtocol._

import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.comparison.ComparisonRepository
import net.modelbased.mediation.client.repository.mapping.MappingRepository

/**
 * This singleton implements the comparator service. It accepts a mapping oracle,
 * and a list of mappings to evaluate against the oracle, fetch all mappings,
 * performs the needed comparisons and store them at a specific URL in the
 * comparison repository.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Runner(val partners: PartnerHandler) extends HttpSpraySupport {

   private[this] val MAPPING_REPOSITORY_URL = "/mediation/repositories/mappings"
   private[this] val COMPARISON_REPOSITORY_URL = "/mediation/repositories/comparisons"

   val httpClientName = "comparator"

   val mappingRepository = {
      val (host, port) = partners("mapping-repository").get
      new Portal(host, port) with MappingRepository
   }

   val comparisonRepository = {
      val (host, port) = partners("comparison-repository").get
      new Portal(host, port) with ComparisonRepository
   }

   
   /**
    * Fetch all the mappings, and trigger all the needed comparisons
    */
   def process(request: Request): String = {
      var result: List[String] = Nil
      val oracle = mappingRepository.fetchMappingById(request.oracle) 

      val evaluations = request.toCompare.foldLeft(List[Evaluation]()) {
         (acc, v) =>
            val mapping = mappingRepository.fetchMappingById(v)
            mapping.evaluateAgainst(oracle) :: acc
      }
      
      comparisonRepository.storeComparisons(evaluations)

      return COMPARISON_REPOSITORY_URL + "/" + request.oracle
   }


}