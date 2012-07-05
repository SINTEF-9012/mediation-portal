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
package net.modelbased.mediation.service.repository.comparison.data

import java.util.Date
import java.util.UUID

/**
 * Store the result of a comparison between a set of mappings and a given oracle
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Comparison(
  val uid: String = UUID.randomUUID().toString,
  val oracle: String, 
  var contents: Map[String, Evaluation] = Map.empty,
  val note: String = "") {

  /**
   * @returns the number of evaluations contained in this comparison
   */
  def size: Int =
    this.contents.size

    
  /**
   * Look for the evaluation of a given mapping.
   * 
   * @param mapping the mapping whose evaluation is needed
   * 
   * @return the related evaluation if it exists, None otherwise
   */
  def get(mapping: String): Option[Evaluation] =
    this.contents.get(mapping)
    
    
  /**
   * Clear out the comparison: any evaluation stored will be lost
   */
  def clear =
    this.contents = Map.empty

  /**
   * Add a new mapping evaluation in this comparison
   *
   * @param e the mapping evaluation to add
   */
  def add(e: Evaluation) =
    this.contents += (e.mapping -> e)

  /**
   * Remove the evaluation of the given mapping. If there is not evaluation of
   * the given mapping, nothing is changed.
   *
   * @param mapping the mapping to remove
   *
   */
  def remove(mapping: String) =
    this.contents = this.contents - (mapping)

  /**
   * Remove the given evaluation. If it is not already in the comparison, nothing
   * is done.
   *
   * @param e the evaluation to remove
   */
  def remove(e: Evaluation) =
    this.contents = this.contents - (e.mapping)

  /**
   * Add a collection of enumerations.
   *
   * @param evaluations the list of evaluation to add
   */
  def addAll(evaluations: List[Evaluation]) =
    this.contents ++= evaluations.foldLeft(Map[String, Evaluation]()) { (acc, e) => acc + (e.mapping -> e) }

  /**
   * @inheritdoc
   */
  override def toString: String =
    "Comparison with mapping %s:\n%s".format(
      oracle,
      contents.map { e => " - " + e.toString }.mkString("\n"))

}

/**
 * Store the evaluation of one mapping against the oracle
 *
 * @param mapping the UDI of the subject of the evaluation
 *
 * @param precision the precision measure
 *
 * @param recall the recall measure
 *
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
case class Evaluation(val mapping: String, val precision: Double, val recall: Double) {

  /**
   * @returns the fMeasure, deduced from the precision and the recall
   */
  def fMeasure: Double =
    0.

  /**
   * @inheritdoc
   */
  override def toString: String =
    "%s - %6.4f %6.4f %6.4f".format(mapping, precision, recall, fMeasure)

}

