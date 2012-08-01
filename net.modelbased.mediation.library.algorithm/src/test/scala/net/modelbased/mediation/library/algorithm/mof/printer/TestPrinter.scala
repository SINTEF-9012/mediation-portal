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
package net.modelbased.mediation.library.algorithm.mof.printer

import org.specs2.mutable._

import net.modelbased.mediation.library.algorithm.mof._

/**
 * Specification of the behaviour expected for the pretty printer of mof
 * models
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestPrinter extends SpecificationWithJUnit {

   val printer = new MofPrinter()

   val fooPackage = new Package("foo")
   val fooClass = new Class("MyClass", false, fooPackage)
   val theSingleFeature = new Feature(Some(fooClass), "singleFeature", String, 1, Some(1))
   val theOptionalFeature = new Feature(Some(fooClass), "optionalFeature", String, 0, Some(1))
   val theCollectionFeature = new Feature(Some(fooClass), "collectionFeature", String, 0, None)
   
   val thePackage = new Package("test")
   val theClass = new Class("MyClass", false, thePackage)
   val featureA = new Feature(Some(theClass), "featureA", Integer, 1, Some(1))
   val featureB = new Feature(Some(theClass), "featureB", String, 0, Some(1)) 
   
   
   val theAbstractClass = new Class("AbstractClass", true, thePackage)
   val featureC = new Feature(Some(theAbstractClass), "featureC", Boolean, 0, Some(1))
  
   val theSubClass = new Class("MySubClass", false, thePackage)
   val featureD = new Feature(Some(theSubClass), "featureD", Real, 1, Some(1))
   theSubClass.addSuperClass(theAbstractClass)
   
   val theEnumeration = new Enumeration("MyEnum")
   val myLiteral = new Literal(theEnumeration, "Monday")
   val myOtherLiteral = new Literal(theEnumeration, "Tuesday")

   "A printer" should {
      
      "print packages properly" in {
         val input = new StringBuilder()
         val output = thePackage.accept(printer, input)
         val expected = "package test {\n\tclass MyClass {\n\t\tfeatureA: Integer\n\t\tfeatureB: String [0..1]\n\t}\n\t\n\tabstract class AbstractClass {\n\t\tfeatureC: Boolean [0..1]\n\t}\n\t\n\tclass MySubClass extends AbstractClass {\n\t\tfeatureD: Real\n\t}\n\t\n}"
         output.result must beEqualTo(expected)
      }

      "print regular classes properly" in {
         val input = new StringBuilder()
         val output = theClass.accept(printer, input)
         val expected = "class MyClass {\n\tfeatureA: Integer\n\tfeatureB: String [0..1]\n}"
         output.result must beEqualTo(expected)
      }
      
      "print abstract classes properly" in {
         val input = new StringBuilder()
         val output = theAbstractClass.accept(printer, input)
         val expected = "abstract class AbstractClass {\n\tfeatureC: Boolean [0..1]\n}"
         output.result must beEqualTo(expected)
      }
      
      "print super classes properly" in {
         val input = new StringBuilder()
         val output = theSubClass.accept(printer, input)
         val expected = "class MySubClass extends AbstractClass {\n\tfeatureD: Real\n}"
         output.result must beEqualTo(expected)
      }
      
      "print single feature properly" in {
         val input = new StringBuilder()
         val output = theSingleFeature.accept(printer, input)
         output.result must beEqualTo("singleFeature: String")
      }

      "print optional features properly" in {
         val input = new StringBuilder()
         val output = theOptionalFeature.accept(printer, input)
         output.result must beEqualTo("optionalFeature: String [0..1]")
      }

      "print collection features properly" in {
         val input = new StringBuilder()
         val output = theCollectionFeature.accept(printer, input)
         output.result must beEqualTo("collectionFeature: String [0..*]")
      }

      "print enumeration literals properly" in {
         val input = new StringBuilder()
         val output = myLiteral.accept(printer, input)
         output.result must beEqualTo("Monday")
      }

      "print enumeration properly" in {
         val input = new StringBuilder()
         val output = theEnumeration.accept(printer, input)
         val expected = "enum MyEnum { Monday, Tuesday }"
         output.result must beEqualTo(expected)
      }

      "print the Character type properly" in {
         val input = new StringBuilder()
         val output = Character.accept(printer, input)
         output.result must beEqualTo("Character")
      }

      "print the String type properly" in {
         val input = new StringBuilder()
         val output = String.accept(printer, input)
         output.result must beEqualTo("String")
      }

      "print the Integer type properly" in {
         val input = new StringBuilder()
         val output = Integer.accept(printer, input)
         output.result must beEqualTo("Integer")
      }

      "print the Real type properly" in {
         val input = new StringBuilder()
         val output = Real.accept(printer, input)
         output.result must beEqualTo("Real")
      }

      "print the Boolean type properly" in {
         val input = new StringBuilder()
         val output = Boolean.accept(printer, input)
         output.result must beEqualTo("Boolean")
      }

      "print the Byte type properly" in {
         val input = new StringBuilder()
         val output = Byte.accept(printer, input)
         output.result must beEqualTo("Byte")
      }
   }

}