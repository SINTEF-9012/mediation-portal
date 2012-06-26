/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.mapping
 *
 * SensApp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SensApp is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with SensApp. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package net.modelbased.mediation.service.repository.mapping


import cc.spray.json._
import net.modelbased.sensapp.library.datastore._
import net.modelbased.mediation.library.mapping._

class MappingRegistry extends DataStore[Mapping]  {

  import MappingJsonProtocol._
  
  override val databaseName = "mediation_portal"
  override val collectionName = "repository.mappings" 
  override val key = "uid"
    
  override def getIdentifier(e: Mapping) = e.uid
  
  override def deserialize(json: String): Mapping = { json.asJson.convertTo[Mapping] }
 
  override def serialize(e: Mapping): String = { e.toJson.toString }
    
}