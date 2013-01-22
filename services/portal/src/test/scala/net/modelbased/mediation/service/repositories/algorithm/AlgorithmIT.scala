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
package net.modelbased.mediation.service.repositories.algorithm


import org.specs2.mutable._

import net.modelbased.mediation.library.data.Algorithm 

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repositories.algorithm.AlgorithmRepository

/**
 * Simple integration test for the algorithm repository. We merely push models 
 * and ensure that these models are then available from the resulting URL.
 *
 * @note Running these tests require a MongoDB instance running locally
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class AlgorithmRepositoryIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with AlgorithmRepository 

   var algoA: Algorithm = _
   var algoB: Algorithm = _
   
   
   /**
    * Prepare and clean the algorithm repository. We basically push two 
    * algorithms that are later removed from the repository once the test is 
    * run. 
    */
   class Repository extends BeforeAfter {
      
      override def before = {
         algoA = new Algorithm("syntactic-match", "Simple match based on the similarity between model elements' names", "www.mediation.org", 8080)
         portal.addAlgorithm(algoA)
         algoB = new Algorithm("random-match", "Simple random match (usefull for comparison with the null hypothesis)", "www.mediation.org", 8080)
         portal.addAlgorithm(algoB)
      }
      
      override def after = {
         portal.deleteAlgorithm(algoA)
         portal.deleteAlgorithm(algoB) 
      }
      
   }

   
   "The Algorithm Repository" should {
      
      "support adding and deleting models from the repository" in new Repository {
         val algos = portal.fetchAllAlgorithms 
         algos.size must beEqualTo(2)
         
         // We add a new algorithm in the repository
         val algoC = new Algorithm("remics-match", "Complex macth based on fuzzy-clustering of elements' names", "www.mediation.org", 8080)
 
         portal.addAlgorithm(algoC)
        
         val algos2 = portal.fetchAllAlgorithms 
         algos2.size must beEqualTo(3)
        
         val algoCbis = portal.fetchAlgorithmById("remics-match")
         algoCbis must beEqualTo(algoC) 
         
         // we delete the model from the repository
         portal.deleteAlgorithm(algoC)

         val algos3 = portal.fetchAllAlgorithms 
         algos3.size must beEqualTo(2)
         
      }

   }

}