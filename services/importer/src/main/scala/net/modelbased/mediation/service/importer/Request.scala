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

import cc.spray.json._

/**
 * Describe the possible format accepted by the importer service
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object Format extends Enumeration {

   case class FormatValue(val label: String, val description: String) extends Val(label) {

      def withDescription: String =
         "%s: %s".format(label, description)

   }

   val TEXT = FormatValue("TEXT", "SINTEF MoF textual notation")
   val XSD = FormatValue("XSD", "Standard XML schemas")
   val ECORE = FormatValue("ECORE", "Eclipse EMF MoF models")

   implicit def valueToFormat(v: Value): FormatValue = v.asInstanceOf[FormatValue]

}

/**
 * Represent a request to the importer service. Each request contain the name,
 * the description of the model and its contents in its original form.
 *
 * @author Franck Chauvel - SINTEF ICT
 */
sealed case class Request(val modelId: String, val description: String, val format: String, val content: String)

/**
 * The associated serialization JSON protocol
 */
object RequestJsonProtocol extends DefaultJsonProtocol {
   implicit val formatValueFormat = jsonFormat(Format.FormatValue, "label", "description") 
   implicit val requestFormat = jsonFormat(Request, "modelId", "description", "format", "content")
}