/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.portal
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
package net.modelbased.mediation.service.aggreator

import org.specs2.mutable._

import net.modelbased.mediation.library.algorithm.mof.reader.MofReader

import net.modelbased.mediation.service.repository.model.data.Model

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.client.aggregator.Aggregator

class AggregatorIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with ModelRepository with Aggregator
   var modelA: Model = _
   var modelB: Model = _
   var result: Model = _

   "The aggregator" should {

      val modelAName = "modelA-aggregator"
      val modelBName = "modelB-aggregator"

      "populate the model repository" in new Repository {
         println("Triggering Aggregation")
         val url = portal.aggregate("aggregator-result", List(("model-A", "mA"), ("model-B", "mB")))
         result = portal.fetchModelAt(url)
         val reader = new MofReader
         reader.readPackage(result.content) must beLike {
            case Left(errors) => ko
            case Right(aPackage) => aPackage must beLike {
               case p: Package =>
                  p.elementNamed("mA") must beSome
                  p.elementNamed("mB") must beSome
               case _ => ko;
            }
         }
      }

   }

   class Repository extends BeforeAfter {

      override def before = {
         println("Pushing Model A")
         modelA = new Model("model-A", "a test model", "text/mof", "package pA { class A { featureA: String } }")
         portal.storeModel(modelA)

         println("Pushing Model B")
         modelB = new Model("model-B", "a test model", "text/mof", "package pB { class B { featureB: String } }")
         portal.storeModel(modelB)
      }

      override def after = {
         portal.deleteModel(modelA)
         portal.deleteModel(modelB)
         portal.deleteModel(result) 
      }
      
   }

}

