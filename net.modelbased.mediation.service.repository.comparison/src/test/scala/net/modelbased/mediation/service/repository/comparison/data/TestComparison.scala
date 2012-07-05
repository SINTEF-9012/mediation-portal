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


import org.specs2.mutable._


/**
 * A quick specification of the comparison behaviour
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestComparison extends SpecificationWithJUnit {

  "Any comparison" should {
    val c = new Comparison(oracle = "mappingX")
    val e1 = new Evaluation("mappingY", 0.2, 0.8) 
    val e2 = new Evaluation("mappingZ", 0.2, 0.8) 
      
    "be empty at first" in {
      c.size must_== 0
    }
    
    "support adding evaluation" in {
      c.add(e1)
      c.size must_== 1
      c.get("mappingY") must_== Some(e1)
    }
    
    "support removing old evaluations" in {
      c.add(e1)
      c.add(e2)
      c.size must_== 2
      c.get("mappingY") must_== Some(e1)
      c.get("mappingZ") must_== Some(e2)
      c.remove("mappingY")
      c.size must_== 1
      c.get("mappingZ") must_== Some(e2)
      c.get("mappingY") must_== None
    }
    
    "support removing all its evaluations" in {
      c.add(e1)
      c.add(e2)
      c.size must_== 2
      c.clear
      c.size must_== 0
      c.get("mappingZ") must_== None
      c.get("mappingY") must_== None
    } 
    
  }
}