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
package net.modelbased.mediation.library.algorithm.mof.reader

import org.specs2.mutable.SpecificationWithJUnit

import net.modelbased.mediation.library.algorithm.mof._

class TestMofBuilder extends SpecificationWithJUnit with SampleMofAst {
   isolated

   val builder = new MofBuilder()
   
   
   "A MofBuilder" should {

      "build a literal from LiteralNode" in {
         val errors = saturday.accept(builder, Nil)
         errors must beEmpty
         saturday.modelElement must beSome.which {
            case l: Literal =>
               l.name must beEqualTo("Saturday")
            case _ =>
               ko
         }
      }

      "build an Enumeration from a EnumerationNode" in {
         val errors = weekDays.accept(builder, Nil)
         errors must beEmpty
         weekDays.modelElement must beSome.which {
            case e: Enumeration =>
               val countSaturday = e.literals.count { l => l.name == "Saturday" }
               countSaturday must beEqualTo(1)
               val countSunday = e.literals.count { l => l.name == "Sunday" }
               countSunday must beEqualTo(1)
               e.literals.size must beEqualTo(2)
            case _ => ko
         }
      }

      "build a feature out of a FeatureNode" in {
         val errors = name.accept(builder, Nil)
         errors must beEmpty
         name.modelElement must beSome.which {
            case f: Feature =>
               f.name must beEqualTo("name")
               f.container must beNone
               f.`type` must beNull			// It is supposed to be resolved later, during linking
               f.lower must beEqualTo(1)
               f.upper must beSome.which{ v => v == 1}
               f.isOrdered must beTrue
               f.isUnique must beFalse
            case _ =>
               ko
         }
      }
      
      "build a class out or a ClassNode" in {
         val errors = element.accept(builder, Nil)
         errors must beEmpty
         element.modelElement must beSome.which{
            case c: Class =>
               c.name must beEqualTo("Element")
               c.container must beNone
               c.isAbstract must beFalse
               c.superClasses must beEmpty
               c.subClasses must beEmpty
               c.features.size must beEqualTo(3)
               val countFeatureNamedName = c.features.count{ f => f.name == "name" }
               countFeatureNamedName must beEqualTo(1)
               val countFeatureNamedNext = c.features.count{ f => f.name == "next" }
               countFeatureNamedNext must beEqualTo(1)
               val countFeatureNamedPrevious = c.features.count{ f => f.name == "previous" }
               countFeatureNamedPrevious must beEqualTo(1)
            case _ => 
               ko
         }
      }
      
      "build a Package from a PackageNode" in {
         val errors = test.accept(builder, Nil)
         errors must beEmpty
         test.modelElement must beSome.which{ 
            case p: Package =>
               p.name must beEqualTo("test")
               p.elements.size must beEqualTo(3)
               val countElementNamedSpecialElement = p.elements.count{ e => e.name == "SpecialElement" }
               countElementNamedSpecialElement must beEqualTo(1)
               val countElementNamedElement = p.elements.count{ e => e.name == "Element" }
               countElementNamedElement must beEqualTo(1)
               val countElementNamedData = p.elements.count{ e => e.name == "data" }
               countElementNamedData must beEqualTo(1)
            case _ => ko
         }
      }

   }
}