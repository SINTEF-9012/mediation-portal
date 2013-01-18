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
package net.modelbased.mediation.service.importer


import org.specs2.mutable._

import scala.xml._

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.client.importer.Importer 

import net.modelbased.mediation.service.repository.model.data.Model


trait ImporterIT extends SpecificationWithJUnit {

   val portal = new Portal("localhost", 8080) with ModelRepository with Importer

   
   trait Repository extends After {
  
      var theModel: Model = _
         
      override def after = {
          portal.deleteModel(theModel)
      }
   
   }

   "The importer" should {

      "provide the list of supported format" in {
    	  val formats = portal.supportedFormats
    	  formats must not beNull ;
    	  formats must not beEmpty
      }
      
      "support importing textual MOF models" in new Repository {
         val modelId = "test-importer-model"
         val content = "package importer { class Test { feature1: String [0..1] } }"
         val url = portal.importModel(modelId, "a simple textual model", Format.TEXT, content)
         theModel = portal.fetchModelAt(url)
         theModel must not beNull ;
         theModel.content must beEqualTo(content)         
      }
      
      "support importing XSD models" in new Repository {
         val modelId = "test-importer-model"
         val content = Utility.trim(XML.loadFile("src/test/resources/schemas/article.xsd")) 
         val url = portal.importModel(modelId, "a simple textual model", Format.XSD, content.toString())
         theModel = portal.fetchModelAt(url)
         theModel must not beNull ;
         theModel.content must beEqualTo(content)         
      }
      
     
   }


}