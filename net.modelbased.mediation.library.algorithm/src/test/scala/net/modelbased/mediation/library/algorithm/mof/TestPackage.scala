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

import org.specs2.mutable._

trait SamplePackageable extends SampleMof {

   def thePackageable: Packageable

   override def theElement: Element =
      thePackageable

}

/**
 * Abstract specification of the behaviour expected for any packageable element,
 * especially types
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait TestPackageable extends TestElement {
   this: SamplePackageable =>

   "A packageable" should {

      "have non null container" in {
         thePackageable.container must not beNull
      }
      
      "have a non null qualified named" in {
         thePackageable.qualifiedName must contain("/").when(thePackageable.container.isDefined)
      }

      "reject update container updates that creates duplicated package elements" in {
         val name = thePackageable.name
         val aPackage = new Package("foo-package")
         val aSubPackage = new Package(name, aPackage)
         (thePackageable.container = Some(aPackage)) must throwA[DuplicatePackageElement]
      }
      
      "reject container udpate that creates circular dependencies in the containment tree" in {
    	  val p1 = new Package("p1")
    	  val p2 = new Package("p2", p1)
    	  (p1.container = Some(p2)) must throwA[IllegalArgumentException]
      }
   }
}

trait SamplePackage extends SamplePackageable {

   override def thePackageable = thePackage

}

/**
 * Define the behaviour expected for packages
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestPackage extends SpecificationWithJUnit with TestPackageable with SamplePackage {
   isolated

   "A package" should {

      "support fetching content by name" in {
         val unknownElement = thePackage.elementNamed("this-element-does-not-exist")
         unknownElement must beNone
         val knownElement = thePackage.elementNamed("subpackage")
         knownElement must beSome.which(x => x == theSubPackage)
      }

      "support proper addition of new element" in {
         val newPackage = new Package("newPackage")
         newPackage.container must beNone
         thePackage.addElement(newPackage)
         thePackage.elementNamed("newPackage") must beSome.which { p => p == newPackage }
         newPackage.container must beSome.which { x => x == thePackage }
      }

      "reject addition of elements whose name already used" in {
         thePackage.elementNamed("subpackage") must beSome
         val newSubPackage = new Package("subpackage")
         newSubPackage.container must beNone
         (thePackage.addElement(newSubPackage)) must throwA[DuplicatePackageElement]
      }

      "reject addition of an element that creates circular containment" in {
         val newSubPackage = new Package("newSubPackage", thePackage)
         val newSubSubPackage = new Package("newSubPackage", newSubPackage)
         (newSubSubPackage.addElement(thePackage)) must throwA[IllegalArgumentException]
      }

      "support deletion of element" in {
         thePackage.elementNamed("subpackage") match {
            case None => ko
            case Some(element) =>
               thePackage.deleteElement(element.name)
               element.container must beNone
               thePackage.elements must not contain (element)
               thePackage.elementNamed("subpackage") must beNone
         }
      }

      "support containment detection over several level of depth" in {
         val p1 = new Package("P1")
         val p2 = new Package("P11", p1)
         val p3 = new Package("P111", p2)
         p1.contains(p3) must beTrue
      }

      "be root if it has no container" in {
         val isRoot = thePackage.isRoot
         isRoot must beEqualTo(thePackage.container.isEmpty)
      }

   }

}