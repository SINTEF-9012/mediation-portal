/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.library.algorithm
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
package net.modelbased.mediation.library.algorithm.mof

import org.specs2.specification.Scope
import org.specs2.mutable.Specification


trait TestElement extends Specification {
  
  this: SampleMof =>
  
  
  "An element" should {
    
    "support name update" in {
      val newName = theElement.name + "Foo"
      theElement.name = newName
      theElement.name must beEqualTo(newName)
    }
    
    
    "reject null as name update" in  {
      theElement.name must not beNull
    }
    
    
    "have a non null qualified name" in {
      theElement.qualifiedName must not beNull
    }
    
  }
  
}