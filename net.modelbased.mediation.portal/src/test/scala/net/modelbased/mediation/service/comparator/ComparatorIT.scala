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
package net.modelbased.mediation.service.comparator

import org.specs2.mutable._

import net.modelbased.mediation.service.repository.mapping.data.Mapping

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.repository.comparison.ComparisonRepository
import net.modelbased.mediation.client.comparator.Comparator

/**
 * Simple integration test for the Comparator service. We ensure that given a set
 * of models, the comparator service performs properly the needed comparisons and
 * effectively stores the result in the comparison repository.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ComparatorIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with MappingRepository with ComparisonRepository with Comparator

   var mappings: List[Mapping] = _
   var oracle: Mapping = _

   "The comparator service" should {

      "push the proper evaluation in to the comparison repository" in new Repository {
         // Trigger the comparison of the the oracle against the other mappings
         val listUrl = portal.compare(oracle.uid, mappings.toList.map { x => x.uid }, "Comparator integration test")
         val comparisonUrls = portal.fetchUrlsOfComparisonsWithOracle(oracle.uid)
         forall(comparisonUrls) { url =>
         	val comparison = portal.fetchComparisonAt(url)
         	comparison must not beNull
         }
      }

   }

   /**
    * Prepare the mapping repository: add an oracle aand 10 mappings related to this oracle.
    */
   class Repository extends BeforeAfter {

      /**
       * Push an oracle and a set of mappings in the mapping repository
       */
      override def before = {
         oracle = MatcherMock.mapping
         mappings = (for (i <- 1 to 10) yield MatcherMock.mapping).toList
         (oracle :: mappings).foreach {
            mapping => portal.storeMapping(mapping)
         }
      }

      /**
       * Delete the mappings that were pushed in the repository
       */
      override def after = {
         portal.deleteMapping(oracle)
         mappings.foreach { mapping => portal.deleteMapping(mapping) }
         portal.deleteComparisonsWithOracle(oracle.uid)
      }

   }

}