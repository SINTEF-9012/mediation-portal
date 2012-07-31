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
package net.modelbased.mediation.library.algorithm.mof

import org.specs2.specification._

trait SampleMof extends AfterExample {

   def theElement: Element = null

   var thePackage = new Package("testPackage")
   var theSubPackage = new Package("subpackage", thePackage)

   var theClass = new Class("MyClass")
   var theFeature = new Feature(Some(theClass), "myFeature", String)
   var featureA = new Feature(Some(theClass), "featureA", String)
   var featureB = new Feature(Some(theClass), "featureB", Boolean)

   var anotherClass = new Class("OtherClass")
   var anotherFeature = new Feature(Some(anotherClass), "anotherFeature", Boolean)

   var theEnumeration = new Enumeration("MyEnumeration")
   var theLiteral = new Literal(theEnumeration, "theLiteral")
   var literalA = new Literal(theEnumeration, "LiteralA")
   var literalB = new Literal(theEnumeration, "LiteralB")

   def after = {
      thePackage = new Package("testPackage")
      theSubPackage = new Package("subpackage", thePackage)
      
      theClass = new Class("MyClass")
      theFeature = new Feature(Some(theClass), "myFeature", String)
      featureA = new Feature(Some(theClass), "featureA", String)
      featureB = new Feature(Some(theClass), "featureB", Boolean)

      anotherClass = new Class("OtherClass")
      anotherFeature = new Feature(Some(anotherClass), "anotherFeature", Boolean)

      theEnumeration = new Enumeration("MyEnumeration")
      theLiteral = new Literal(theEnumeration, "theLiteral")
      literalA = new Literal(theEnumeration, "LiteralA")
      literalB = new Literal(theEnumeration, "LiteralB")
   }

}