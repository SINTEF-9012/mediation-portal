/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.mapping
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

package net.modelbased.mediation.service.repository.mapping.data

import org.specs2.mutable._


/**
 * A basic specification for the mapping objects
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestMapping extends SpecificationWithJUnit {
	val t1, t2, t3 = new Mapping()
	val e1 = new Entry("x", "y", 0.25, "test")
	val e2 = new Entry("x", "v", 0.75, "test")
	t2.addAll(List(e1, e2))
    t3.addAll(List(e1, e2))
  
    
   "Conversions" should {
	  
	  "not alter mappings " in {
		  val md1 = Conversions.fromMapping(t2)
		  val m = Conversions.toMapping(md1)
		  m.uid must_== t2.uid
		  m.capacity must_== t2.capacity
		  m.status must_== t2.status
		  m.entries must_== t2.entries
		  
		  val md2 = Conversions.fromMapping(m)
		  md1 must_== md2
	  }
	  
	}
  
  "The empty mapping string" should {
	  
	  "not contains any entry" in {
		  t1.size must_== 0
	  }

	  "be ready" in {
		  t1.status must_== Status.READY
	  }
  }
  
  
  "Adding a new entry" should {
    
      "increase the size" in {
		  t2.size must_== 2
	  }
      
	  "make the entry available for search" in {
		  t2.get("x") must_== List(e1, e2)
		  t2.get("x", "y") must_== Some(e1)  
	  }  
  }
  
  
  "Deleting a single entry" should {
    
	  "Reduce the size of the mapping" in {
		  t2.remove(e2)
		  t2.size must_== 1
	  } 
	  
	  "Make some entries unavailable" in {
		  t2.get("x", "v") must_== None
		  t2.get("x") must_== List(e1)
	  }
  
  }
  
  "Deleting all entries" should {
	  
	  "clear the mapping" in {
		  t2.removeAll
		  t2.size must_== 0
	  }
	  
	  "makes all entries unavailable" in {
		  t2.get("x") must_== List.empty
		  t2.get("x", "y") must_== None
		  t2.get("x", "v") must_== None
	  }
	  
  }
    
  
}