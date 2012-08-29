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
package net.modelbased.mediation.service.repository.mapping

import org.specs2.mutable._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.mapping.MappingRepository

import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.mapping.data.Entry

/**
 * Simple Integration test for the Mapping Repository service.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MappingRepositoryIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with MappingRepository

   var mappingA: Mapping = _

   var mappingB: Mapping = _

   /**
    * Prepare and clean the repository
    */
   class Repository extends BeforeAfter {

      override def before = {
         mappingA = new Mapping(sourceId = "foo", targetId = "bar")
         mappingA.add(new Entry("source.foo", "target.bar", 0.34, "testing mapping repository"))
         portal.storeMapping(mappingA) 
         mappingB = new Mapping(sourceId = "foo2", targetId = "bar2")
         mappingB.add(new Entry("source.foo2", "target.bar2", 0.56, "testing mapping repository"))
         portal.storeMapping(mappingB)
      }

      override def after = {
         portal.deleteMapping(mappingA)
         portal.deleteMapping(mappingB)
      }

   }

   "The mapping repository " should {
      
      
       "support flattening information about mappings" in new Repository {
         val infos = portal.fetchAllMappingInfo()
         infos.size must beEqualTo(2)
      }
      
      "support adding and deleting mapping from the repository" in new Repository {
         val urls = portal.fetchAllMappingUrls() 
         urls.size must beEqualTo(2)
         
         // We add a new model in the repository
         val mappingC = new Mapping(sourceId = "foo3", targetId = "bar3")
         mappingC.add(new Entry("source.foo3", "target.bar3", 0.98, "testing mapping repository"))
         portal.storeMapping(mappingC)
        
         val urls2 = portal.fetchAllMappingUrls() 
         urls2.size must beEqualTo(3)
        
         val mappingCbis = portal.fetchMappingById(mappingC.uid)
         mappingCbis.size must beEqualTo(1) 
         
         // we delete the model from the repository
         portal.deleteMapping(mappingC)

         val urls3 = portal.fetchAllMappingUrls() 
         urls3.size must beEqualTo(2)
      }
      
      
      "support export of mapping as an XML document" in new Repository {
         val node = portal.exportMappingToXml(mappingA.uid)
         node must not beNull
      }

   }

}