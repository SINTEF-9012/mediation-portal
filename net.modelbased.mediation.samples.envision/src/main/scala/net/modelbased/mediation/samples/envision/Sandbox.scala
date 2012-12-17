/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.samples.envision
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
package net.modelbased.mediation.samples.envision

import scala.io.Source

import xml.{Utility, XML}

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.importer.Importer
import net.modelbased.mediation.client.aggregator.Aggregator
import net.modelbased.mediation.client.mediator.Mediator
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.repository.model.ModelRepository

import net.modelbased.mediation.service.importer.Format

object Sandbox extends App {

   val host: String = "54.247.114.191" // SINTEF DEMO Server
   val port: Int = 8080
   
//   val host: String = "localhost" // Locahost
//   val port: Int = 8080

   // Instantiate the portal 
   val portal = new Portal(host, port) with Importer with Aggregator with Mediator with MappingRepository with ModelRepository

   val source1Id = "oryx_source_task_6c85e34e-7494-49aa-99f6-a26bc41caa50"
   val source2Id = "oryx_source_task_a3658cca-49f9-40ce-dcf7-e47fcefb9d39"

      
    
//   // Import the first source model
//   println("1. Importing the source 1 ... ")
//   val source1Id = "source_import_test_1"
//   val source1 = Source.fromFile("src/main/resources/source1.mod").mkString  
//   portal.importModel(source1Id,
//      "Roy's bug in importing (input 1)",
//      Format.TEXT,
//      source1)
//   println("Complete.\n")
//      
//   // Import the first source model
//   println("1. Importing the source 2 ... ")
//   val source2Id = "source_import_test_2"
//   val source2 = Source.fromFile("src/main/resources/source2.mod").mkString  
//   portal.importModel(source2Id,
//      "Roy's bug in importing (input 2)",
//      Format.TEXT,
//      source2)
//   println("Complete.\n")
   
//    // Import the first source model
//   println("3. Importing the target ... ")
//   val targetId = "target_import_test"
//   val target = Utility.trim(XML.loadFile("src/main/resources/droughtIn-target.xsd")).toString
//   portal.importModel(targetId,
//      "Roy's bug in importing (target)",
//      Format.XSD,
//      target)
//   println("Complete.\n")
   
   
   println("Fetching the source model #2 ...")
   val source2bis = portal.fetchModelById(source2Id)
   println("Source model #1: " + source2bis + "\n")
   println("Complete.\n")

   println("Fetching the source model #1 ...")
   val source1bis = portal.fetchModelById(source1Id)
   println("Source model #1: " + source1bis + "\n")
   println("Complete.\n")

   println("Aggregation of the two models ... ")
   val aggregationId = "test-aggregation-Dec13"
   portal.aggregate(aggregationId, List((source1Id, "sos"), (source2Id, "wfs")))
   println("Complete.\n")

   println("Fetching results ... ")
   val result = portal.fetchModelById(aggregationId)
   println("Complete.\n")

   println("The result is: \n" + result + "\n")

}