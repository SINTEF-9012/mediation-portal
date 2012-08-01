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
import org.specs2.mutable._


trait SampleLiteral extends SampleMof {
  
  override def theElement: Element =
    theLiteral 
    
    
}


/**
 * Test the behaviour of enumeration Literals
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestLiteral extends SpecificationWithJUnit with TestElement with SampleLiteral {
	isolated
  
  "A literal" should {
    
    
    "reject name update that would generate duplicate names in the container" in {
    	(literalB.name = "LiteralA") must throwA[DuplicateEnumerationLiteral]
    }
    
    "support container update" in {
      val anotherEnumeration = new Enumeration("AnotherEnumeration")
      literalA.container = Some(anotherEnumeration)
      literalA.container must beSome.which{x => x == anotherEnumeration}
      anotherEnumeration.literals must contain(literalA)
    }
    
    "reject container update that would generate duplicate names in the container" in {
      val newLiteral = new Literal(initialName="LiteralA")
      (newLiteral.container = Some(theEnumeration)) must throwA[DuplicateEnumerationLiteral]
    }
    
    "have qualified name properly formatted" in {
       literalA.qualifiedName must contain("/").when(literalA.container.isDefined)
    }
    
  }
  
}