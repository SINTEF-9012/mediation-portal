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
package net.modelbased.mediation.service.repository.model

import scala.xml.XML

import org.specs2.mutable._

import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.mediator.Mediator



import java.text.SimpleDateFormat

class MediatorIT extends SpecificationWithJUnit {

  val portal = new Portal("localhost", 8080) 
  	with ModelRepository 
  	with MappingRepository 
  	with Mediator

  val modelName = "test-mediator"

     
  var source: Model = _

  var target: Model = _
  
  var mapping: Mapping = _
  
  val sourceMof = scala.io.Source.fromFile("src/test/resources/mof/article.txt").mkString

  val targetMof = scala.io.Source.fromFile("src/test/resources/mof/document.txt").mkString

  
  /**
   * Prepare the repository for the mediation: we push two models between which
   * the test will try to mediate. These two models are removed from the model
   * repository when the test is complete
   */
  class Repository extends BeforeAfter {
     
     override def before = {
        source = new Model("test-document", "A sample data model describing documents", "text/mof", sourceMof)
        portal.storeModel(source)
        target = new Model("test-article", "A sample data model describing article", "text/mof", targetMof)
        portal.storeModel(target)
     }
     
     
     override def after = {
        portal.deleteModel(source)
        portal.deleteModel(target)
        portal.deleteMapping(mapping)
     }
     
  }
  
  
  "The Mediator" should {
     
     "return the list of available algorithms" in {
    	 val algorithms = portal.fetchAlgorithmNames
    	 algorithms must not beNull ;
    	 algorithms must not beEmpty
     }
     

    "populate the mapping repository" in new Repository {
    	val url = portal.mediate(source.name, target.name, "syntactic")
        mapping = portal.fetchMappingAt(url)   
        mapping must not beNull ;
        mapping.entries must not beEmpty
    }
  }

}