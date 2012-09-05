/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.samples.invoicing
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
package net.modelbased.mediation.samples

import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.model.data.Model

import scala.xml.{ XML, Node, NodeSeq, Utility }

import net.modelbased.mediation.client.portal.Portal
import net.modelbased.mediation.client.importer.Importer
import net.modelbased.mediation.client.aggregator.Aggregator
import net.modelbased.mediation.client.mediator.Mediator
import net.modelbased.mediation.service.importer.Format

/**
 * This example illustrates how to use the mediation engine, to bridge between two
 * 'a priori' different data model, using a third one, by chaining two mediations.
 *
 * We first look for equivalent element between two invoicing data models named
 * informatix and canonical. In a second step, we search for equivalent elements
 * between the canonical and the logo data model.
 *
 * Combined together, the resulting information permit to map the informatix data
 * model directly to the the logo data model.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object InvoicingSample extends App {

   // Instantiate the portal
   val portal = new Portal("localhost", 8080) with Importer with Aggregator with Mediator

   // Import the invoicing data model of the LOGO company
   val logoId = "sample-invoicing-LOGO"
   val xsd1 = Utility.trim(XML.loadFile("src/main/resources/schemas/logo.xsd")).toString
   val url1 = portal.importModel(logoId,
      "A sample XSD model describing the invoicing data of the 'LOGO' company",
      Format.XSD,
      xsd1)
   println("LOGO model available at: " + url1)

   // Import the invoicing data model of the Canonical standard
   val canonicalId = "sample-invoicing-CANONICAL"
   val xsd2 = Utility.trim(XML.loadFile("src/main/resources/schemas/canonical.xsd")).toString
   val url2 = portal.importModel(canonicalId,
      "A sample XSD model describing the canonical invoicing data",
      Format.XSD,
      xsd2)
   println("Canonical model available at: " + url2)

   // We trigger the mediation between logo and canonical
   val url3 = portal.mediate(logoId, canonicalId, "syntactic")
   println("Mapping from LOGO to canonical avaialble at: " + url3)

   // Import the invoicing data model of the Canonical standard
   val informatixId = "sample-invoicing-Informatix"
   val xsd3 = Utility.trim(XML.loadFile("src/main/resources/schemas/informatix.xsd")).toString
   val url4 = portal.importModel(informatixId,
      "A sample XSD model describing the invoincing data of the 'informatix' data",
      Format.XSD,
      xsd3)
   println("Informatix model available at: " + url4)

   // We trigger the mediation between logo and canonical
   val url5 = portal.mediate(canonicalId, informatixId, "syntactic")
   println("Mapping from Canonical to Informatix avaialble at: " + url5)

}