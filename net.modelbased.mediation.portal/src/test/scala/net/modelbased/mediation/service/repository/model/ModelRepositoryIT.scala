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

import net.modelbased.mediation.service.repository.model.data.Model

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository

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
class ModelRepositoryIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with ModelRepository

   var modelA: Model = _
   var modelB: Model = _
   
   
   /**
    * Prepare and clean the model repository. We basically push two models that
    * are then removed from the repository once the test is run. 
    */
   class Repository extends BeforeAfter {
      
      override def before = {
         modelA = new Model("test-modelA", "a simple test model", "text/mof", "package testA { class A { featureA: String } }")
         portal.storeModel(modelA)
         modelB = new Model("test-modelB", "another simple test model", "text/mof", "package testB { class B { featureA: String } }")
         portal.storeModel(modelB)
      }
      
      override def after = {
         portal.deleteModel(modelA)
         portal.deleteModel(modelB)
      }
      
   }

   
   "The Model Repository" should {

      
      "support flattening information about models" in new Repository {
         val infos = portal.fetchAllModelInfo()
         infos.size must beEqualTo(2)
      }
 
      
      "support adding and deleting models from the repository" in new Repository {
         val urls = portal.fetchAllModelUrls() 
         urls.size must beEqualTo(2)
         
         // We add a new model in the repository
         val modelC = new Model("test-modelC", "a thrid simple test model", "text/mof", "package testC { class C { featureC: String } }")
         portal.storeModel(modelC)
        
         val urls2 = portal.fetchAllModelUrls() 
         urls2.size must beEqualTo(3)
        
         val modelCbis = portal.fetchModelById("test-modelC")
         modelCbis must beEqualTo(modelC) 
         
         // we delete the model from the repository
         portal.deleteModel(modelC)

         val urls3 = portal.fetchAllModelUrls() 
         urls3.size must beEqualTo(2)
         
      }

   }

}