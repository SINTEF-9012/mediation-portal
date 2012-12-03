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


import scala.xml.{ XML, Node, NodeSeq, Utility }
import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.importer.Importer
import net.modelbased.mediation.client.aggregator.Aggregator
import net.modelbased.mediation.client.mediator.Mediator
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.repository.model.ModelRepository

import net.modelbased.mediation.service.importer.Format


object WaterAlarmService extends App {

   val host: String = "54.247.114.191" // SINTEF DEMO Server
   val port: Int = 8080

   // Instantiate the portal
   val portal = new Portal(host, port) with Importer with Aggregator with Mediator with MappingRepository with ModelRepository
   
     // Import the first source model
   print("1. Importing the source 1 XSD ... ")
   val source1Id = "ENVISION-DroughtIn-source1"
   val xsd1 = Utility.trim(XML.loadFile("src/main/resources/droughtIn-source1.xsd")).toString
   portal.importModel(source1Id,
      "ENVISION - Description of the source SOS service",
      Format.XSD,
      xsd1)
   println("\t\t[ OK ]")

   // Import the second source model
   print("2. Importing the source 2 XSD ... ")
   val source2Id = "ENVISION-DroughtIn-source2"
   val xsd2 = Utility.trim(XML.loadFile("src/main/resources/droughtIn-source2.xsd")).toString
   portal.importModel(source2Id,
      "ENVISION - Description of the source WFS service",
      Format.XSD,
      xsd2)
   println("\t\t[ OK ]")

   // Aggregate the two models
   print("3. Aggregating the two source XSD ... ")
   val aggregationId = "ENVISION-DroughtIn-aggregated-source"
   portal.aggregate(aggregationId, List((source1Id, "sos"), (source2Id, "wfs")))
   println("\t\t[ OK ]")

   // Import the target model
   // Import the second source model
   print("4. Importing the WPS target XSD ... ")
   val targetId = "ENVISION-DroughtIn-target"
   val xsd3 = Utility.trim(XML.loadFile("src/main/resources/droughtIn-target.xsd")).toString
   portal.importModel(targetId,
      "ENVISION - Description of the target WPS service",
      Format.XSD,
      xsd3)
   println("\t\t[ OK ]")

   // We trigger the mediation
   print("5. Mediating between the two XSD ... ")
   val url = portal.mediate(aggregationId, targetId, "syntactic")
   println("\t\t[ OK ]")

   // Showing the result
   print("6. Fetching the resulting mapping ...")
   val result = portal.fetchMappingAt(url)
   println("\t\t[ OK ]")

   println("The result is: \n" + result + "\n")
   

}