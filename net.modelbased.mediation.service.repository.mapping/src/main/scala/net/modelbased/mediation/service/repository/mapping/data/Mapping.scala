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

package net.modelbased.mediation.service.repository.mapping.data

import java.util.UUID

/**
 * Describe the possible states in which a mapping object can be
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
object Status extends Enumeration {
  val READY = Value(1)
  val RUNNING = Value(2)
  val COMPLETE = Value(3)
  val ERROR = Value(4)
}

/**
 * A simple mapping object that store relationship between elements.
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
class Mapping(val uid: String = UUID.randomUUID().toString(), var status: Status.Value = Status.READY) { 

  private[this] var contents: Map[(String, String), Entry] = Map.empty

  
  /**
   * @return the number of entries of the mapping
   */
  def size: Int =
    this.contents.size
  
  
  /**
   * Filter the mapping on a given source element
   *
   * @param source the source whose entries needed to be retrieved
   *
   * @return all the mapping entries, whose source match the given value
   */
  def get(source: String): List[Entry] =
    this.contents.filter { case ((s, t), v) => s == source }.values.toList

  

  /**
   * Return the entry identified by its source and target element, or None if
   * there does not exist such an entry
   *
   * @param source the source element
   *
   * @param target the target element
   *
   * @return the related entry, if it exists (None otherwise)
   */
  def get(source: String, target: String): Option[Entry] =
    this.contents.get((source, target))

    
  /**
   * Update the entry identified by its source and target elements
   *  
   * @param entry the entry that must be added
   */
  def add(entry: Entry) =
    this.contents += ((entry.source, entry.target) -> entry)
    
    
  /**
   * Add a collection of entries
   *
   * @param entries the new entries add in the mapping
   */
  def addAll(entries: List[Entry]) = {
    this.contents = this.contents ++
      entries.foldLeft(Map.empty.asInstanceOf[Map[(String, String), Entry]]) {
        (acc, e) =>
          acc + ((e.source, e.target) -> e)
      }
  }
    
  /**
   * Remove an entry from the mapping
   * 
   * @param entry the entry to remove
   */
  def remove(entry: Entry) =
    this.contents -= ((entry.source, entry.target))
    
    
  /**
   * Remove a set of entries
   * 
   * @param the set of entry to remove
   */
  def removeAll(entries: Collection[Entry]) =
  	this.contents = entries.foldLeft(this.contents){
    	(acc, v) =>
    	  acc - ((v.source, v.target))
    }
    
  /**
   * Erase the all mapping. No entry will remain
   */
  def removeAll =
    this.contents = Map.empty

}

object Mapping {

  val EMPTY = new Mapping()

}

case class Entry(val source: String, val target: String, val degree: Double, val origin: String)


