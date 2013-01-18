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
package net.modelbased.mediation.service.repository.comparison

import cc.spray.json._
import net.modelbased.mediation.service.repository.comparison.data.Evaluation
import net.modelbased.sensapp.library.datastore._

/**
 * Defines a data store devoted to mapping comparison in the mediation database
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class EvaluationRegistry extends DataStore[Evaluation] {

  import data.JsonEvaluationProtocol._

  override val databaseName = "mediation_portal"
  override val collectionName = "repository.comparisons"
  override val key = "oracle"

  override def getIdentifier(e: Evaluation) = (e.oracle, e.mapping)
  
  override def deserialize(json: String): Evaluation = { json.asJson.convertTo[Evaluation] } 

  override def serialize(e: Evaluation): String = { e.toJson.toString }  
 
  /**
   * Insert a new evaluation in the repository. This function ensure the
   * replacement of any evaluation which would already be in the registry with similar oracle
   * and mapping
   *
   * @param e the evaluation to add
   */
  def add(e: Evaluation) = {
    val filter = List(("oracle", e.oracle), ("mapping", e.mapping))
    retrieve(filter) match {
      case Nil =>
        push(e)
      case x :: Nil =>
        drop(e);
        push(e)
      case l: List[Evaluation] => // this case should never occurs
        l.foreach { e => drop(e) }
        push(e)
    }
  }

  /**
   * Delete a given evaluation in the repository, and all the other evaluation
   * which a similar oracle and mapping
   *
   * @param e the evaluation to remove
   */
  def remove(e: Evaluation) = {
    val filter = List(("oracle", e.oracle), ("mapping", e.mapping))
    retrieve(filter).foreach { e => drop(e) }
  }

  /**
   * Retrieve all the evaluation having a given oracle
   */
  def findByOracle(oracle: String) = {
    val filter = List(("oracle", oracle)) 
    retrieve(filter)
  }

  /**
   * Retrieve the evaluation (normally unique) with a given oracle and mapping. In
   * case that several are available in the registry, it returns only the first one.
   * 
   * @param oracle the oracle of the evaluation to retrieve
   * @param mapping the mapping of the evaluation to retrieve
   * 
   * @return the evaluation matching the given oracle and mapping, or none otherwise 
   */
  def findByOracleAndMapping(oracle: String, mapping: String): Option[Evaluation] = {
    val filter = List(("oracle", oracle), ("mapping", mapping))
    retrieve(filter) match {
      case Nil => None
      case x :: Nil => Some(x)
      case x :: l => Some(x)
    }
  }
  
  
  /**
   * @return all the oracles available in the registry
   */
  def allOracles(): List[String] = {
    val filter = List()
    retrieve(filter).map{ x => x.oracle }.distinct
  }

}