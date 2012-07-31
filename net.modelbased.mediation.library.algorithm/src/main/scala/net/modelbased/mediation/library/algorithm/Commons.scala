/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.library.algorithm
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
package net.modelbased.mediation.library.algorithm


import net.modelbased.mediation.service.repository.mapping.data._ 
import net.modelbased.mediation.service.repository.model.data._



class Translate extends ModelProcessor { def apply(m: Model): Model = m }

class Prune extends ModelProcessor { def apply(m: Model): Model = m }

class Filter(val threshold: Double) extends MappingProcessor {
  def apply(m: Mapping): Mapping = { return null }
}


/**
 * Defines the common library of the internal DSL describing composite mediations
 * 
 * To publish a new function in this library, just create a val that is bound to
 * the implementation of algorithms
 * 
 * @author Sebastien Mosser - SINTEF ICT
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
object Commons {
  
  val translate = new Translate()
  
  val prune = new Prune()
  
  val filter = new Filter(0.)
  
  val xsdSyntacticMatch : Mediation = new SyntacticXsdMediation
  
  val semanticMatch : Mediation = null
  
  val xsdRandomMatch : Mediation = new RandomXsdMediation
  
  val aggregateByMax : MappingAggregator = null

}