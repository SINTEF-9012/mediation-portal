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
package net.modelbased.mediation.library.util

import org.specs2.mutable._


/**
 * Quick check of the main properties of the cosine distance between list of
 * strings.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestCosineDistance extends SpecificationWithJUnit {

  val distance = new CosineDistance()
  val l1 = List("elephant", "hippopotame", "koala")
  val l2 = List("cloporte", "limace", "escargot")
  val l3 = List("elephant", "coloporte", "limace")
  
  
  "The cosine distance " should {
    
	  "be equal to 1 when the two list are totally different" in {
		  val d = distance(l1, l2)
		  (1-d) must be_< (1e-9)
	  }
	  
	  "be equal to 1 when the two list are exactly the same" in {
		  val d = distance(l1, l1)
		  d must be_< (1e-9)
	  }
	  
	  "be equal to 0 when the two list are the same, modulo the order" in {
	    val d = distance(l1, l1.reverse)
	    d must be_< (1e-9)
	  }
	  
	  "be in ]0, 1[ when the two lists share some elements" in {
	    val d = distance(l1, l3)
	    d must be_>(0.) and be_<(1.)
	  }
	  
	  "be a commutative operator" in {
	    val d1 = distance(l1, l3)
	    val d2 = distance(l3, l1)
	    math.abs(d1 - d2) must be_<(1e-9)	    
	  }
    
  }
  
}