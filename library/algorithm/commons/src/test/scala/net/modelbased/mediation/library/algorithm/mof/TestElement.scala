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

import org.specs2.specification.Scope
import org.specs2.mutable.Specification

/**
 * Specification of the expected behaviour of MoF models elements
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait TestElement extends Specification {

   this: SampleMof =>

   "An element" should {

      "support name update" in {
         val newName = theElement.name + "Foo"
         theElement.name = newName
         theElement.name must beEqualTo(newName)
      }

      "have not predefined annotations" in {
         theElement.annotations must beEmpty
      }

      "support adding and deleting annotations" in {
         val key = "test"
         theElement.annotation(key) must beNone
         val value = "this is an annotation"
         theElement.addAnnotation(key, value)
         theElement.annotations must contain((key, value))
         theElement.annotation(key) must beSome.which{ v => v == value}
         theElement.deleteAnnotation(key)
         theElement.annotation(key) must beNone
         theElement.annotations must beEmpty
      }
      

      "reject null as name update" in {
         theElement.name must not beNull
      }

      "have a non null qualified name" in {
         theElement.qualifiedName must not beNull
      }

   }

}