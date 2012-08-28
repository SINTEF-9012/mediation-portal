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
import org.specs2.mutable._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.comparison.ComparisonRepository

import net.modelbased.mediation.service.repository.comparison.data.Evaluation
import net.modelbased.mediation.service.repository.comparison.data.Statistics

import net.modelbased.mediation.service.comparator.MatcherMock

/**
 * Simple integration test for the model repository. We merely push one model and
 * we ensure that the model is then available at the given URL.
 *
 * @note Running these tests require a MongoDB instance running locally
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ComparisonRepositoryIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with ComparisonRepository

   var comparisonA: Evaluation = _
   var comparisonB: Evaluation = _

   /**
    * Prepare and clean the comparison repository
    */
   class Repository extends BeforeAfter {

      override def before = {
         comparisonA = MatcherMock.mapping.evaluateAgainst(MatcherMock.mapping)
         comparisonB = MatcherMock.mapping.evaluateAgainst(MatcherMock.mapping)
         portal.storeComparisons(List(comparisonA, comparisonB))
      }

      override def after = {
         portal.deleteComparison(comparisonA)
         portal.deleteComparison(comparisonB)
      }

   }

   "The Comparison Repository" should {

      "support the extraction of the comparison at once" in {
         val infos = portal.fetchAllComparisons()
         infos.size must beEqualTo(2)
      }

      "support the addition and deletion of comparisons" in {
         val urls = portal.fetchAllOracleUrls()
         urls.size must beEqualTo(2)

         // We add a new model in the repository
         val comparisonC = MatcherMock.mapping.evaluateAgainst(MatcherMock.mapping)
         portal.storeComparisons(List(comparisonC))

         val urls2 = portal.fetchAllOracleUrls()
         urls2.size must beEqualTo(3)

         val comparisonCbis = portal.fetchComparisonById(comparisonC.oracle, comparisonC.mapping)
         comparisonCbis must beEqualTo(comparisonC)

         // we delete the model from the repository
         portal.deleteComparison(comparisonC)

         val urls3 = portal.fetchAllOracleUrls()
         urls3.size must beEqualTo(2)
      }

      "support the calculation of statistics" in {
         val stats = portal.fetchStatisticsById(comparisonA.oracle, comparisonA.mapping)
         stats must not beNull
      }

   }


}