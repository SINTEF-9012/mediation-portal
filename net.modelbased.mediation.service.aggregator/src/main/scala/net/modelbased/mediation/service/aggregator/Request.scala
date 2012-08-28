/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.aggregator
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
package net.modelbased.mediation.service.aggregator

import cc.spray.json._

/**
 * Represent a request to the aggreagator service. Each request contains the
 * name to give to the resulting model, as wel as a list of couple (model, packageName)
 * reflecting in the packages where models must be placed.
 *
 * @author Franck Chauvel - SINTEF ICT
 */
sealed case class Request(val resultId: String, val parts: List[Part])

sealed case class Part(val modelId: String, val packageName: String)

/**
 * The associated serialization JSON protocol
 */
object RequestJsonProtocol extends DefaultJsonProtocol {
   implicit val PartFormat = jsonFormat(Part, "modelId", "packageNam")
   implicit val requestFormat = jsonFormat(Request, "resultId", "parts")
}