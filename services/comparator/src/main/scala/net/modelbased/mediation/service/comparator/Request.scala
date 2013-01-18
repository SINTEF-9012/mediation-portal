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
package net.modelbased.mediation.service.comparator


import cc.spray.json._

/**
 * Represent a request to the comparator service.
 * 
 * @param oracle the URI of the mapping that must be used as oracle
 * 
 * @param toCompare the URIs of the mappings that  must be compared with the oracle
 * 
 * @param note a short description explaining the context of the comparison
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
case class Request(val oracle: String, val toCompare: List[String], val note: String)




/**
 * JSON Serialisation
 */
object RequestJsonProtocol extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat(Request, "oracle", "toCompare", "note")
}