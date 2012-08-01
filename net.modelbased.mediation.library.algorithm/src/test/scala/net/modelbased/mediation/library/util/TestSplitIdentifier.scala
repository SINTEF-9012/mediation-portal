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
package net.modelbased.mediation.library.util

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Expected behaviour of the Split identifier
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestSplitIdentifier extends SpecificationWithJUnit {

   val split = new SplitJavaIdentifier()

   "Splitting an identifier" should {

      "remove whitespaces" in {
         val identifier = "foo bar   quz"
         val result = split(identifier)
         result must beEqualTo(List("foo", "bar", "quz"))
      }

      "remove underscores" in {
         val identifier = "foo_bar  __ quz"
         val result = split(identifier)
         result must beEqualTo(List("foo", "bar", "quz"))
      }

      "remove dollar symbols" in {
         val identifier = "foo_bar $quz"
         val result = split(identifier)
         result must beEqualTo(List("foo", "bar", "quz"))
      }

      "process CaML case properly" in {
         val identifier = "fooBarQuz"
         val result = split(identifier)
         result must beEqualTo(List("foo", "bar", "quz"))
         val identifier2 = "fooBarQUZ"
         val result2 = split(identifier2)
         result2 must beEqualTo(List("foo", "bar", "quz"))
         val identifier3 = "fooBarQUZ"
         val result3 = split(identifier3)
         result3 must beEqualTo(List("foo", "bar", "quz"))
      }

   }

}


