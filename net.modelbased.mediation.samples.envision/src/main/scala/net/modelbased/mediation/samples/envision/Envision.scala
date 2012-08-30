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
import net.modelbased.mediation.service.importer.Format

/**
 * ENVISION illustrate how to write access (importer, aggregator and mediator)
 * so as to trigger the mediation
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object EnvisionSample extends App {

   // Instantiate the portal
   val portal = new Portal("localhost", 8080) with Importer with Aggregator with Mediator

   // Import the first source model
   val source1Id = "ENVISION-SOS-source"
   val xsd1 = Utility.trim(XML.loadFile("src/main/resources/source-SOS-SINTEF.xsd")).toString
   portal.importModel(source1Id,
      "ENVISION - Description of the source SOS service",
      Format.XSD,
      xsd1)

   // Import the second source model
   val source2Id = "ENVISION-WFS-source"
   val xsd2 = Utility.trim(XML.loadFile("src/main/resources/source-WFS-SINTEF.xsd")).toString
   portal.importModel(source2Id,
      "ENVISION - Description of the source WFS service",
      Format.XSD,
      xsd2)
      
   // Aggregate the two models
   val aggregationId = "ENVISION-aggregated-source"
   portal.aggregate(aggregationId, List((source1Id, "sos"), (source2Id, "wfs")))
         
   
   // Import the target model
   // Import the second source model
   val targetId = "ENVISION-WPS-target"
   val xsd3 = Utility.trim(XML.loadFile("src/main/resources/target-WPS-SINTEF.xsd")).toString
   portal.importModel(targetId,
      "ENVISION - Description of the target WPS service",
      Format.XSD,
      xsd3)
    
   // We trigger the mediation
   portal.mediate(aggregationId, targetId, "syntactic")
    
   
}
