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
package net.modelbased.mediation.service.repository.comparison.data

import java.util.Date
import java.util.UUID



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
case class Evaluation(val oracle:String, val mapping: String, val tp: Int, val tn: Int, val fp: Int, val fn: Int) {

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
   * Extract all the statistics from this evaluations
   */
   def toStatistics: Statistics =
     new Statistics(oracle, mapping, precision, recall, accuracy, fMeasure)

  /**
   * @inheritdoc
   */
  override def toString: String =
    "%s / %s : %7.3f %7.3f %7.3f %7.3f".format(oracle, mapping, precision * 100, recall * 100, fMeasure * 100, accuracy * 100)

}


/**
 * Represent the statistics that can be derived from an evaluation
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
case class Statistics(val oracle: String, val mapping: String, precision: Double, recall: Double, accuracy: Double, fMeasure: Double) {

  /**
   * @inheritdoc
   */
  override def toString: String =
    "%s / %s : %7.3f %7.3f %7.3f %7.3f".format(oracle, mapping, precision * 100, recall * 100, fMeasure * 100, accuracy * 100)

}

