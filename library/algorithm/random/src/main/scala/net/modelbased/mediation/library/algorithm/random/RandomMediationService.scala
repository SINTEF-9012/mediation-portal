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
package net.modelbased.mediation.library.algorithm.random


import net.modelbased.mediation.library.algorithm.facade.AbstractMediationService


/**
 * Implementation of the random mediation service.
 * 
 * We simply bind the RandomMatcher to the mediation service interface.
 * 
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
trait RandomMediationService extends AbstractMediationService {

  // We set up the name of the mediation service
  override val name = "Random"
  
  // We define here the algorithm to use
  override val algorithm = new RandomMatch()
  
}