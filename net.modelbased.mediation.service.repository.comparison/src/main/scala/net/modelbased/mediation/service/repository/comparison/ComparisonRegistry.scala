/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.comparison
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
package net.modelbased.mediation.service.repository.comparison

import cc.spray.json._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.sensapp.library.datastore._



/**
 * Defines a data store devoted to mapping comparison in the mediation database
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class ComparisonRegistry extends DataStore[JsonComparison] {
  
  import JsonComparisonProtocol._
  
  override val databaseName = "mediation_portal" 
  override val collectionName = "repository.comparisons" 
  override val key = "uid"
    
  override def getIdentifier(e: JsonComparison) = e.uid
  
  override def deserialize(json: String): JsonComparison = { json.asJson.convertTo[JsonComparison] }
 
  override def serialize(e: JsonComparison): String = { e.toJson.toString }
    
}