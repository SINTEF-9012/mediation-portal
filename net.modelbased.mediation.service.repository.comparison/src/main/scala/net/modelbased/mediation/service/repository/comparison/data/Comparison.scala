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
  initial: List[Evaluation],
  val note: String = "") {
  require(!initial.isEmpty && !initial.exists{ e1 => initial.exists{ e2 => e1.oracle != e2.oracle }})

  val oracle = initial.head.oracle
  
  private[this] var internal: Map[(String, String), Evaluation] = initial.foldLeft(Map[(String, String), Evaluation]()){(acc, e) => acc + ((e.oracle, e.mapping) -> e)}
  
  /**
   * @returns the number of evaluations contained in this comparison
   */
  def size: Int =
  this.internal.size
  
  
  /**
   * @return the list of all the entries available for the record
   */
  def contents: List[Evaluation] =
    this.internal.values.toList

  /**
   * Look for the evaluation of a given mapping.
   *
   * @param mapping the mapping whose evaluation is needed
   *
   * @return the related evaluation if it exists, None otherwise
   */
  def get(mapping: String): Option[Evaluation] =
    this.internal.get(oracle, mapping)

  /**
   * Clear out the comparison: any evaluation stored will be lost
   */
  def clear =
    this.internal = Map.empty

  /**
   * Add a new mapping evaluation in this comparison
   *
   * @param e the mapping evaluation to add
   */
  def add(e: Evaluation) = {
	require(e.oracle == oracle)
    this.internal += ((oracle, e.mapping) -> e)
  }
  
  /**
   * Remove the evaluation of the given mapping. If there is not evaluation of
   * the given mapping, nothing is changed.
   *
   * @param mapping the mapping to remove
   *
   */
  def remove(mapping: String) =
    this.internal = this.internal - ((oracle, mapping))

  /**
   * Remove the given evaluation. If it is not already in the comparison, nothing
   * is done.
   *
   * @param e the evaluation to remove
   */
  def remove(e: Evaluation) =
    this.internal = this.internal - ((e.oracle, e.mapping))

  /**
   * Add a collection of enumerations.
   *
   * @param evaluations the list of evaluation to add
   */
  def addAll(evaluations: List[Evaluation]) = {
    require(evaluations.forall(e => e.oracle == oracle))
    this.internal ++= evaluations.foldLeft(Map[(String, String), Evaluation]()) { (acc, e) => acc + ((e.oracle, e.mapping) -> e) }
  }
  
  /**
   * @inheritdoc
   */
  override def toString: String =
    "Comparison with mapping %s:\n%s".format(
      oracle,
      internal.map { e => " - " + e.toString }.mkString("\n"))

}

/**
 * Store the evaluation of one mapping against the oracle
 *
 * @param mapping the UDI of the subject of the evaluation
 *
 * @param tp the number of true positive match
 * 
 * @param tn the number of true negative match
 * 
 * @param fp the number of false positive match
 * 
 * @param fn the number of false negative match
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
sealed case class Evaluation(val oracle:String, val mapping: String, val tp: Int, val tn: Int, val fp: Int, val fn: Int) {

  /**
   * @return the precision as a value in [0, 1]
   */
  def precision: Double = 
    tp / (tp + fp).toDouble

  /**
   * @return the recall as a value in [0, 1]
   */
  def recall: Double = 
    tp / (tp + fn).toDouble

  /**
   * @return the accuracy as a value in [0, 1]
   */
  def accuracy: Double = 
    (tp + tn) / (tp + tn + fp + fn).toDouble

  /**
   * @return the fMeasure as a value in [0, 2]
   */
  def fMeasure: Double =
    2 * (precision * recall) / (precision + recall)

  /**
   * @inheritdoc
   */
  override def toString: String =
    "%s / %s : %7.3f %7.3f %7.3f %7.3f".format(oracle, mapping, precision * 100, recall * 100, fMeasure * 100, accuracy * 100)

}

