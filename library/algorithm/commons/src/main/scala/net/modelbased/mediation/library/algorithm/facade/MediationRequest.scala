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
package net.modelbased.mediation.library.algorithm.facade

import cc.spray.json._

/**
 * Represent a mediation request that all mediation services must supports.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
case class MediationRequest(val source: String, val target: String)



/**
 * Ensure the proper JSON serialization and deserialization
 */
object MediationRequestJsonProtocol extends DefaultJsonProtocol {
  
  implicit val requestFormat = jsonFormat(MediationRequest, "source", "target")

}