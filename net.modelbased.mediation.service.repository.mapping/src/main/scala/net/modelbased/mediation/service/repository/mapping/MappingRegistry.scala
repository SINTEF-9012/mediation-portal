/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.mapping
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
package net.modelbased.mediation.service.repository.mapping


import cc.spray.json._
import net.modelbased.sensapp.library.datastore._
import net.modelbased.mediation.service.repository.mapping.data._

class MappingRegistry extends DataStore[MappingData]  {

  import MappingJsonProtocol._
  
  override val databaseName = "mediation_portal"
  override val collectionName = "repository.mappings" 
  override val key = "uid"
    
  override def getIdentifier(e: MappingData) = e.uid
  
  override def deserialize(json: String): MappingData = { json.asJson.convertTo[MappingData] }
 
  override def serialize(e: MappingData): String = { e.toJson.toString }
    
}