/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.model
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
package net.modelbased.mediation.service.repository.model.data

import cc.spray.json._
import net.modelbased.sensapp.library.datastore._


/**
 * Model artifacts to be stored in the mediation portal
 */
case class Model(val name: String, var content: String)

/**
 * Spray support for JSON serialization
 */
object ModelJsonProtocol extends DefaultJsonProtocol {
  implicit val modelFormat = jsonFormat(Model, "name", "content")
}


/**
 * persistence manager for models
 */
class ModelRegistry extends DataStore[Model]  {

  import ModelJsonProtocol._
  
  override val databaseName = "mediation_portal"
  override val collectionName = "repository.models" 
  override val key = "name"
    
  override def getIdentifier(e: Model) = e.name
  
  override def deserialize(json: String): Model= { json.asJson.convertTo[Model] }
 
  override def serialize(e: Model): String = { e.toJson.toString }
    
}
