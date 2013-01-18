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
package net.modelbased.mediation.library.algorithm.mof

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Specification of the expected behaviour of the MoFCollector object
 */
class TestMofCollector extends SpecificationWithJUnit {
   isolated

   val thePackage = new Package("test")
   val theSubPackage = new Package("testagain", thePackage)
   val enum1 = new Enumeration("WeekDays", theSubPackage)
   val lit1 = new Literal(enum1, "Monday")
   val lit2 = new Literal(enum1, "Tuesday")
   val lit3 = new Literal(enum1, "Wednesday")
   
   val theSubPackage2 = new Package("testmore", thePackage)
   val classA = new Class("FooClass", false, theSubPackage2)
   val featureA = new Feature(Some(classA), "featureA", String)
   val featureB = new Feature(Some(classA), "featureB", String)
   val featureC = new Feature(Some(classA), "featureC", String)
   
   val classB = new Class("BarClass", false, thePackage)
   val featureD = new Feature(Some(classB), "featureD", String)
   val featureE = new Feature(Some(classB), "featureE", String)
   
   
   
   "A feature collector" should {
      val featureCollector = new Collector(x => x.isInstanceOf[Feature])

      "collect all the features of a package" in {
         val allFeatures = thePackage.accept(featureCollector, Nil)
         allFeatures.size must beEqualTo(5)
         allFeatures must contain(featureA)
         allFeatures must contain(featureB)
         allFeatures must contain(featureC)
         allFeatures must contain(featureD)
         allFeatures must contain(featureE)
      }

   }
   
   
   "A type collector" should {
      val typeCollector = new Collector(x => x.isInstanceOf[Type])
      
      "collect all the types declared in a package" in {
         val allTypes = thePackage.accept(typeCollector, Nil)
         allTypes.size must beEqualTo(3)
         allTypes must contain(enum1)
         allTypes must contain(classA)
         allTypes must contain(classB)
      }
   }

}