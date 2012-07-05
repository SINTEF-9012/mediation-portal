/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.comparator
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
package net.modelbased.mediation.service.comparator


import net.modelbased.mediation.service.repository.mapping.data.Mapping


/**
 * This singleton implements the comparator service. It accepts a mapping oracle,
 * and a list of mappings to evaluate against the oracle, performs the needed 
 * comparisons and store them at a specific URL in the comparison repository.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
object Runner {
  
  /**
   * Fetch all the mappings, and trigger all the needed comparisons 
   */
  def compare(request: Request) = 
    null

    
  /**
   * Fetch a given mapping, form its URI.
   */
  def fetch(uri: String): Mapping = 
    null

    
  /** 
   * Compare one mapping against the oracle and returns the associated report
   * 
   * @param oracle the mapping that will be considered as correct during the evaluation
   * 
   * @param subject the mapping that must be compared to the oracle
   * 
   * @return a report describing how close is the subject from the oracle
   */
  def evaluate(oracle: Mapping, subject: Mapping): Object =
    null
    
    
}