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
import net.modelbased.mediation.service.repository.mapping.data.Conversions._

class MappingRegistry extends DataStore[MappingData] {

   import MappingJsonProtocol._

   override val databaseName = "mediation_portal"
   override val collectionName = "repository.mappings"
   override val key = "uid"

   override def getIdentifier(e: MappingData) = e.uid

   override def deserialize(json: String): MappingData = { json.asJson.convertTo[MappingData] }

   override def serialize(e: MappingData): String = { e.toJson.toString }

   /**
    * Retrieve a mapping by ID (if it exists)
    *
    * @param uid the ID of the needed meeting
    *
    * @return the mapping or None if no mapping matches the given uid
    */
   def getMapping(uid: String): Option[Mapping] = {
      pull("uid", uid).map { x => x }
   }

   /**
    * Retrieve an entry from a specific mapping¨
    *
    * @param uid the unique ID of the needed mapping
    *
    * @param source the source element of the needed entry
    *
    * @param target the target element of the needed entry
    */
   def getEntry(uid: String, source: String, target: String): Option[Entry] = {
      getMapping(uid) match {
         case None =>
            throw new IllegalArgumentException("unknown mapping ID '%s'!".format(uid))
         case Some(m) =>
            m.get(source, target)
      }
   }

   /**
    * Modify a given entry in a given mapping. The modification is pass as a function
    * that will be applied on the selected entry if it exists.
    *
    * @param uid the unique ID of the mapping of interest
    *
    * @param source the source element identifying the entry
    *
    * @param target the target element identifying the entry
    *
    * @param update a function that will modify the selected entry
    */
   def updateEntry(uid: String, source: String, target: String, update: Entry => Entry) = {
      val mapping = getMapping(uid)
      mapping match {
         case None =>
            throw new IllegalArgumentException("unknown mapping ID '%s'!".format(uid))
         case Some(m) =>
            m.get(source, target) match {
               case None =>
                  throw new IllegalArgumentException("unknown entry (%s ; %s) for mapping '%s'!".format(source, target, uid))
               case Some(e) =>
                  m.add(update(e))
                  push(m)
            }
      }
   }

   /**
    * Fetch a given entry, and set is "isValidated" property to the given value.
    *
    * @param uid the ID of the mapping of interest
    *
    * @param source the source of the entry
    *
    * @param target the target of the entry
    *
    * @param value the value to set for the isValidated property
    */
   def confirm(uid: String, source: String, target: String, value: Option[Boolean]) = {
      val update = {
         (e: Entry) => e.isValidated = value; e
      }
      updateEntry(uid, source, target, update)
   }

}
