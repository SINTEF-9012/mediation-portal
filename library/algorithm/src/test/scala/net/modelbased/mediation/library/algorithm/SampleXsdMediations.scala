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
package net.modelbased.mediation.library.algorithm


import org.specs2.mutable.Specification
import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping


/**
 * Define a sample mediation algorithm between two XSD schemas. In this simple
 * case, we just convert the source and target XSD schemas into mof models. Then
 * we apply a basic syntactic matching between the two.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
trait SampleMediations extends Specification {

  
   val syntacticMediation = new SyntacticMatch
 
   
   val randomMediation = new RandomMatch
   
}