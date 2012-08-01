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

import org.specs2.mutable._

/**
 * Quick test of the min-edit distance provided by the library
 *
 * @auhtor Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestMinEditDistance extends SpecificationWithJUnit {

  val edit = new MinEditDistance()

  "The MinEdit distance" should {

    "be 1. when two string have nothing in common" in {
      val d = edit("abcdef", "uvwxyz")
      d must_== 1.
    }

    "be 0. when the two strings are similar" in {
      val d = edit("abcdef", "abcdef")
      d must_== 0.
    }

    "be in the interval ]0, 1[ when strings share only a few characters" in {
      val d = edit("abcdef", "uvwxef")
      d must be_>(0.) and be_<(1.)
    }

    "be commutative" in {
      val s1 = "abcdef"
      val s2 = "uvwzef"
      edit(s1, s2) must_== edit(s2, s1)
    }

  }

}