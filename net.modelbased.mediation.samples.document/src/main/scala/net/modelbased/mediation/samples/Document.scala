/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.samples.document
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
package net.modelbased.mediation.samples


import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.model.data.Model 


/**
 * This class illustrates how to describe a composite mediation using the internal
 * DSL provided with the SINTEF mediation framework
 *
 * In this example, the composite mediation that we build combines two
 * mediations algorithms, which are both provided by the framework. The first one
 * matches XSD types using a syntactic distance between their names, whereas the
 * second one matches types using a semantic measure. The two resulting mappings
 * are then simply merged using the most probable entries
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class SampleMediation extends Mediation {

  // We import the standard library of mediation algorithms 
  import net.modelbased.mediation.library.algorithm.Commons._

  /*
   * Here, we override the behaviour of the mediation, by running a syntactic
   * match on one side, a semantic match on the other side, and finally merging the
   * two results by selecting the most relevant entries. 
   */
  override def execute(in: Mapping, source: Model, target: Model) = {
    val m1 = syntacticMatch(in, source, target)
    val m2 = randomMatch(in, source, target)
    val result = aggregateByMax(Set(m1, m2))
  }

}
