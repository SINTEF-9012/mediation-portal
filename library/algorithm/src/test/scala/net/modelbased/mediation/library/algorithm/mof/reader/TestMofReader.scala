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
package net.modelbased.mediation.library.algorithm.mof.reader

import org.specs2.mutable.SpecificationWithJUnit

import net.modelbased.mediation.library.algorithm.mof._

/**
 * Specification of the expected behaviour of the MofReader object. As MofReader
 * is a facade to the various steps of the parsing and static analysis process,
 * this is more like integration testing.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestMofReader extends SpecificationWithJUnit {
   isolated

   val reader = new MofReader

   "A MofReader" should {

      "read enumeration literals" in {
         val text = "Saturday"
         val result = reader.readLiteral(text)
         result must beRight.like { case x: Literal => x.name must beEqualTo("Saturday") }
      }

      "build enumeration from well-formed enumeration text" in {
         val text = "enumeration WeekDays { Monday, Tuesday, Sunday }"
         val result = reader.readEnumeration(text)
         result must beRight.like {
            case x: Enumeration =>
               x.name must beEqualTo("WeekDays")
               x.literals.size must beEqualTo(3)
         }
      }
      
      "build data type from a well-formed declaration" in {
         val text = "data type DateTime"
         val result = reader.readDataType(text)
         result must beRight.like {
            case dt: DataType =>
               dt.name must beEqualTo("DateTime")
         }
      }

      "report about duplicate enumeration literal" in {
         val text = "enumeration WeekDays { Monday, Tuesday, Tuesday }"
         val result = reader.readEnumeration(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e =>
                  e match {
                     case dl: DuplicateLiteral =>
                        dl.literal == "Tuesday"
                  }
               }
               test must beTrue
            case _ => ko
         }
      }

      "build a feature from a well-formed feature" in {
         val text = "theFeature: String"
         val result = reader.readFeature(text)
         result must beRight.like {
            case f: Feature =>
               f.name must beEqualTo("theFeature")
         }
      }

      "report about unknown feature types" in {
         val text = "foo: Bar [0..1]"
         val result = reader.readFeature(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[UnknownFeatureType] }
               test must beTrue
            case _ => ko
         }
      }

      "build a class from a well-formed class" in {
         val text = "class Foo { foo: String [0..1] }"
         val result = reader.readClass(text)
         result must beRight.like {
            case c: Class =>
               c.name must beEqualTo("Foo")
               c.features.size must beEqualTo(1)
               c.featureNamed("foo") must beSome
            case _ => ko
         }
      }
      
      "build class where features have opposites" in {
         val text = "class Foo { foo: String [0..1] ~ bar bar: String ~ foo }"
         val result = reader.readClass(text)
         result must beRight.like {
            case c: Class =>
               c.name must beEqualTo("Foo")
               c.features.size must beEqualTo(2)
               c.featureNamed("foo") must beSome.like{
                  case foo: Feature =>
                     c.featureNamed("bar") must beSome.like{
                        case bar: Feature =>
                        	foo.opposite must beSome.which{ x => x == bar }
                     }
               }
            case _ => ko
         }
      }

      "report about undefined super classes" in {
         val text = "class Foo extends Bar { foo: String [0..1] }"
         val result = reader.readClass(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[UnknownSuperClass] }
               test must beTrue
            case _ => ko
         }
      }

      "report about duplicate feature names with a class" in {
         val text = "class Foo { foo: String [0..1] foo: Boolean }"
         val result = reader.readClass(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[DuplicateFeature] }
               test must beTrue
            case _ => ko
         }
      }

      "build a package from a well-formed package description" in {
         val text = "package Foo { enumeration Bar { bar1, bar2 } class Quz { quz: Boolean [0..*] } }"
         val result = reader.readPackage(text)
         result must beRight.like {
            case p: Package =>
               p.name must beEqualTo("Foo")
               p.elements.size must beEqualTo(2)
               p.elementNamed("Bar") must beSome
               p.elementNamed("Quz") must beSome
         }
      }

      "report about enumeration whose name conflict with an existing class a single package" in {
         val text = "package test { class Foo { bar1: String [1..*] } enumeration Foo { bar3, bar4 } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[DuplicateElement] }
               test must beTrue
            case _ => ko
         }
      }

      "report about duplicate enumeration within a single package" in {
         val text = "package test { enumeration Foo { bar1, bar2 } enumeration Foo { bar3, bar4 } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[DuplicateElement] }
               test must beTrue
            case _ => ko
         }
      }

      "report about illegal inheritance (e.g., from an enumeration)" in {
         val text = "package test { enumeration Bar { bar1, bar2 } class Foo extends Bar { foo: String [0..1] foo: Boolean } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[IllegalInheritance] }
               test must beTrue
            case _ => ko
         }
      }

      "report about cycles in the inheritance hierachy" in {
         val text = "package test { class Top extends Bottom { top: String } class Middle extends Top { middle: String } class Bottom extends Middle { bottom: String } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[CircularInheritance] }
               test must beTrue
            case _ => ko
         }
      }
      
      "report about illegal feature opposite" in {
         val text = "package test { class ClassA { partner: ClassB ~ ClassB } class ClassB { associate: ClassA ~ ClassA.partner } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[IllegalFeatureOpposite] }
               test must beTrue
            case _ => ko
         }  
      }
      
      "report about unknown feature opposite" in {
         val text = "package test { class ClassA { partner: ClassB ~ ClassB.asso } class ClassB { associate: ClassA ~ ClassA.partner } }"
         val result = reader.readPackage(text)
         result must beLeft.like {
            case errors: List[MofError] =>
               val test = errors.exists { e => e.isInstanceOf[UnknownFeatureOpposite] }
               test must beTrue
            case _ => ko
         }  
      }

      "support reuse" in {
         val text1 = "package tns {  class Article {   title: String  abstract: tns.Paragraph  parts: tns.Part } class Part {   label: String } class Paragraph extends tns.Part {   body: String } class Section extends tns.Part {   title: String } class Author {   firstname: String  lastname: String  email: String } class Schema {  article: tns.Article } }"
         val result1 = reader.readPackage(text1)
         result1 must beRight
         val text2 = "package tns {  class Document {   title: String  subtitle: String  version: String  content: tns.Element  authors: tns.Author } class Element {   id: String  title: String  text: String  child: tns.Element } class Author {   name: String  family_name: String } class Schema {  document: tns.Document } }"
         val result2 = reader.readPackage(text2)
         result2 must beRight
      }

   }

}