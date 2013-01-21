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
package net.modelbased.mediation.library.algorithm.syntactic



import net.modelbased.mediation.library.algorithm.facade.AbstractMediationService



/**
 * Implementation of the syntactic mediation service.
 * 
 * This just binds the SyntacticMatch algorithm to the MediationService 
 * interface. The complete REST API is inherited from AbstractMediationService.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * @author 0.0.1
 */
trait SyntacticMediationService extends AbstractMediationService {
  
  
  // We set up the name of the mediation service
  override val name = "syntactic"
  
  // We define here the algorithm to actually use
  override val algorithm = new SyntacticMatch()
  
  
}