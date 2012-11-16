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

package net.modelbased.mediation.samples.dome

import scala.io.Source
import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.importer.Importer
import net.modelbased.mediation.client.aggregator.Aggregator
import net.modelbased.mediation.client.mediator.Mediator
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.service.importer.Format
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader 
import net.modelbased.mediation.library.algorithm.mof._
/**
 * REMICS Case study - DOME
 *
 * The DOME case study is about a mapping from the internal data representation
 * of the DOME company and the OTA standard, which is a large data schema.
 *
 * In this first version of the DOME case, we load data model that are described
 * as
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object EnvisionSample extends App {


   val host: String = "54.247.114.191" // SINTEF DEMO Server
   val port: Int = 8080

   // Description of the source
   val sourceModelLocation = "src/main/resources/source.mod"
   val sourceId = "REMICS-DOME-Internal"
   val sourceDescription = "REMICS - The DOME internal representation"
   val sourceContent = Source.fromFile(sourceModelLocation).mkString

   // Description of the target
   val targetModelLocation = "src/main/resources/target.mod"
   val targetId = "REMICS-DOME-OTA"
   val targetDescription = "REMICS - The OTA standard data model"
   val targetContent = Source.fromFile(targetModelLocation).mkString
   
   // Instantiate the portal
   val portal = new Portal(host, port) with Importer with Aggregator with Mediator with MappingRepository with ModelRepository

   // Import the first source model
   print("1. Importing the DOME internal source model ... ")
   portal.importModel(
      sourceId,
      sourceDescription,
      Format.TEXT,
      sourceContent)
   println("\t\t[ OK ]")

   // Import the target model
   print("2. Importing the Standard OTA model ... ")
   portal.importModel(
      targetId,
      targetDescription,
      Format.TEXT,
      targetContent)
   println("\t\t[ OK ]")

   // We trigger the mediation
   print("5. Mediating between the two XSD ... ")
   val url = portal.mediate(sourceId, targetId, "syntactic")
   println("\t\t[ OK ]")

   // Showing the result
   print("6. Fetching the resulting mapping ...")
   val result = portal.fetchMappingAt(url)
   println("\t\t[ OK ]")

   println("The result is: \n" + result + "\n")

}
