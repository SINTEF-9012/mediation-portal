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
package net.modelbased.mediation.services.repositories.algorithm

import cc.spray.json._
import net.modelbased.sensapp.library.datastore._
import net.modelbased.mediation.library.data.Algorithm 


/**
 * Spray support for JSON serialization of the Algorithm descriptions
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
object AlgorithmJsonProtocol extends DefaultJsonProtocol {
  implicit val algorithmFormat = jsonFormat(Algorithm, "id", "description", "url")
}



/**
 * Define the Algorithm registry
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
class AlgorithmRegistry extends DataStore[Algorithm] {
  
  import AlgorithmJsonProtocol._

  override val databaseName = "mediation_portal"
  override val collectionName = "repository.algorithms"
  override val key = "id"

  override def getIdentifier(e: Algorithm) = e.id

  override def deserialize(json: String): Algorithm = { json.asJson.convertTo[Algorithm] } 

  override def serialize(e: Algorithm): String = { e.toJson.toString } 
}