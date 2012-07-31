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

import net.modelbased.mediation.service.repository.comparison.data.Evaluation 

/**
 * Describe the possible states in which a mapping object can be
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
object Status extends Enumeration {
  val READY = Value("READY")
  val RUNNING = Value("RUNNING")
  val COMPLETE = Value("COMPLETE")
  val ERROR = Value("ERROR")
}

/**
 * A simple mapping object that stores relationship between elements.
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
class Mapping(val uid: String = UUID.randomUUID().toString(), var capacity: Int = 10, var status: Status.Value = Status.READY) {

  private[this] var contents: Map[(String, String), Entry] = Map.empty

 
  /**
   * Compare the mapping with a given oracle. This returns a comparison object
   * containing both relative metrics
   * 
   * @param oracle the mapping against which the evaluation shall be done
   * 
   * @return an evaluation of the mapping
   */
  def evaluateAgainst(oracle: Mapping): Evaluation = { 
    val falsePositive = this.entries.count { e => !oracle.contains(e) }
    val truePositive = oracle.entries.count { e => this.contains(e) }
    val falseNegative = oracle.entries.count { e => !this.contains(e) }
    val trueNegative = oracle.capacity - (falsePositive + truePositive + falseNegative)
    return new Evaluation(oracle.uid, this.uid, truePositive, trueNegative, falsePositive, falseNegative)
  }
  
  
  /**
   * @return all the entry contained in the mapping
   */
  def entries: List[Entry] =
    contents.values.toList

    
  /**
   * @check whether the mapping contains a given entry 
   */
  def contains(e: Entry): Boolean =
    contents.contains((e.source, e.target))
    
  /**
   * @return the number of entries of the mapping
   */
  def size: Int =
    this.contents.size
    
    
  /**
   * @return the number of entries that match a given source
   * 
   * @param source the source whose number of entries is needed
   */
  def size(source: String):Int =
    this.contents.count{ case (k, v) => v.source == source}

  
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
   * Remove all the entry with a a given source
   * 
   * @param the source whose entry must be removed
   */
  def removeAll(source: String) = 
    this.contents = this.contents.filter{ case (k,v) => v.source != source } 

  
  /**
   * Remove all the entry with a a given source
   * 
   * @param the source whose entry must be removed
   */
  def removeAll(source: String, target: String) = 
    this.contents = this.contents.filterKeys{ k => k == (source, target) } 
  
  
  /**
   * Remove a set of entries
   *
   * @param the set of entry to remove
   */
  def removeAll(entries: Collection[Entry]) =
    this.contents = entries.foldLeft(this.contents) {
      (acc, v) =>
        acc - ((v.source, v.target))
    }

  
  /**
   * Erase the all mapping. No entry will remain
   */
  def removeAll =
    this.contents = Map.empty
    
    
  /**
   * @inheritdoc
   */
  override def toString: String = {
    def summary(s: String): String = 
    	s.splitAt(22) match {
    		case (l, "") => l
    		case (l, r) => l + "..."
        }
      
    this.contents.values.foldLeft(""){
	  (acc, e) => acc + " - %25s --> %25s (at %+4.2f by %25s)\n".format(summary(e.source), summary(e.target), e.degree, summary(e.origin)) 
    }
  }

}

object Conversions {

    /**
     * Convert a mappingData into a mapping object
     */
    implicit def toMapping(md: MappingData): Mapping =
      md.entries.foldLeft(new Mapping(md.uid, md.capacity, Status.withName(md.status))){ (acc, v) => acc.add(v) ; acc }
    
    /**
     * Convert a mapping object into a mappingData
     */
    implicit def fromMapping(m: Mapping): MappingData =
      new MappingData(m.uid, m.capacity, m.status.toString(), m.entries)

}

sealed case class Entry(val source: String, val target: String, val degree: Double, val origin: String)


