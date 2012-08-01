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
package net.modelbased.mediation.library.algorithm.mof.reader

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Define the behaviour expected for the MofParser
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestMofParser extends SpecificationWithJUnit {
   isolated

   val parser = new MofParser()

   "A MoFParser" should {

      "parse enum literals properly" in {
         val text = "Saturday"
         parser.parse(parser.enumerationLiteral, text) match {
            case parser.Success(lt: LiteralNode, _) =>
               lt.name must beEqualTo("Saturday")
            case _ => ko
         }
      }

      "parse enumeration properly" in {
         val text = "enumeration WeekDays { Monday, Tuesday, Wednesday, Sunday }"
         parser.parse(parser.enumeration, text) match {
            case parser.Success(e: EnumerationNode, _) =>
               e.name must beEqualTo("WeekDays")
               e.literals.size must beEqualTo(4)
               val countMonday = e.literals.count { l => l.name == "Monday" }
               countMonday must beEqualTo(1)
               val countTuesday = e.literals.count { l => l.name == "Tuesday" }
               countTuesday must beEqualTo(1)
               val countWednesday = e.literals.count { l => l.name == "Wednesday" }
               countWednesday must beEqualTo(1)
               val countSundays = e.literals.count { l => l.name == "Sunday" }
               countSundays must beEqualTo(1)
            case _ => ko
         }
      }

      "parse simple features with implicit multiplicity" in {
         val text = "name: String"
         parser.parse(parser.feature, text) match {
            case parser.Success(f: FeatureNode, _) =>
               f.name must beEqualTo("name")
               f.`type` must beEqualTo(new Reference("String"))
               f.lower must beEqualTo(1)
               f.upper must beSome.which { v => v == 1 }
               f.isOrdered must beTrue
               f.isUnique must beFalse
            case _ => ko
         }
      }

      "parse optional feature" in {
         val text = "name: String [0..1]"
         parser.parse(parser.feature, text) match {
            case parser.Success(f: FeatureNode, _) =>
               f.name must beEqualTo("name")
               f.`type` must beEqualTo(new Reference("String"))
               f.lower must beEqualTo(0)
               f.upper must beSome.which { v => v == 1 }
               f.isOrdered must beTrue
               f.isUnique must beFalse
            case _ => ko
         }
      }

      "parse features that are collections" in {
         val text = "name: String [0..*]"
         parser.parse(parser.feature, text) match {
            case parser.Success(f: FeatureNode, _) =>
               f.name must beEqualTo("name")
               f.`type` must beEqualTo(new Reference("String"))
               f.lower must beEqualTo(0)
               f.upper must beNone
               f.isOrdered must beTrue
               f.isUnique must beFalse
            case _ => ko
         }
      }

      "parse basic classes properly" in {
         val text = "class MyClass { p1: String p2: Boolean p3: Integer[0..1] }"
         parser.parse(parser.`class`, text) match {
            case parser.Success(c, _) =>
               c.name must beEqualTo("MyClass")
               c.isAbstract must beFalse
               c.superClasses must beEmpty
               c.features.size must beEqualTo(3)
         }
      }
      
      "parse empty classes properly" in {
         val text = "class MyClass "
         parser.parse(parser.`class`, text) match {
            case parser.Success(c, _) =>
               c.name must beEqualTo("MyClass")
               c.isAbstract must beFalse
               c.superClasses must beEmpty
               c.features must beEmpty
         }
      }

      "parse abstract classes properly" in {
         val text = "abstract class MyClass { p1: String p2: Boolean p3: Integer[0..1] }"
         parser.parse(parser.`class`, text) match {
            case parser.Success(c, _) =>
               c.name must beEqualTo("MyClass")
               c.isAbstract must beTrue
               c.superClasses must beEmpty
               c.features.size must beEqualTo(3)
         }
      }

      "parse classes which specifies super classes" in {
         val text = "class MyClass extends This, That { p1: String p2: Boolean p3: Integer[0..1] }"
         parser.parse(parser.`class`, text) match {
            case parser.Success(c, _) =>
               c.name must beEqualTo("MyClass")
               c.isAbstract must beFalse
               c.superClasses must beEqualTo(Seq(new Reference("This"), new Reference("That")))
               c.features.size must beEqualTo(3)
         }
      }

      "parse packages properly" in {
         val text = "package test { class TestClass { feature: String [0..*] } enumeration States { On, Off } }"
         parser.parse(parser.`package`, text) match {
            case parser.Success(p, _) =>
               p.name must beEqualTo("test")
               p.elements.size must beEqualTo(2)
               val testClassExists =
                  p.elements.exists { e =>
                     e match {
                        case c: ClassNode => c.name == "TestClass"
                        case _            => false
                     }
                  }
               testClassExists must beTrue
               val statesEnumerationExists =
                  p.elements.exists { e =>
                     e match {
                        case e: EnumerationNode => e.name == "States"
                        case _                  => false
                     }
                  }
               statesEnumerationExists must beTrue
         }
      }

      "discard comment in a shell-script fashion" in {
         val text = """
            # This is a comment
            package test {
               
               class SuperClass
            
               class TestClass extends SuperClass {
                  feature1: String [0..1]
               }
            }
            """
           parser.parse(parser.`package`, text) match {
            case parser.Success(p, _) =>
               p.name must beEqualTo("test")
               p.elements.size must beEqualTo(2)
               val testClassExists =
                  p.elements.exists { e =>
                     e match {
                        case c: ClassNode => c.name == "TestClass"
                        case _            => false
                     }
                  }
               testClassExists must beTrue
               val superClassExists =
                  p.elements.exists { e =>
                     e match {
                        case c: ClassNode => c.name == "SuperClass"
                        case _                  => false
                     }
                  }
               superClassExists must beTrue
         } 
         
      }

      "parse explicit multiplicities (e.g., [3, 8])" in {
         val text = "[3..8]"
         parser.parse(parser.multiplicity, text) match {
            case parser.Success((l, u), _) =>
               l must beEqualTo(3)
               u must beSome.which { v => v == 8 }
            case _ => ko
         }
      }

      "parse unbound multiplicities (e.g., [0..*])" in {
         val text = "[3..*]"
         parser.parse(parser.multiplicity, text) match {
            case parser.Success((l, u), _) =>
               l must beEqualTo(3)
               u must beNone
            case _ => ko
         }
      }

      "parse complex references" in {
         val text = "test.data.Map"
         parser.parse(parser.reference, text) match {
            case parser.Success(r, _) =>
               r must beEqualTo(new Reference("test", "data", "Map"))
            case _ => ko
         }
      }

      "parse integer literals" in {
         val text = "10"
         parser.parse(parser.integerLiteral, text) match {
            case parser.Success(v, _) =>
               v must beEqualTo(10)
            case _ => ko
         }
      }

   }

}