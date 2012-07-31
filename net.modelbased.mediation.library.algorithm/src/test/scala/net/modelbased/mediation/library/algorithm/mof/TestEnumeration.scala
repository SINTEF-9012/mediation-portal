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

import org.specs2.specification.Scope
import org.specs2.mutable._

trait SampleEnumeration extends SamplePackageable {

   override def thePackageable: Packageable =
      theEnumeration

}

class TestEnumeration extends SpecificationWithJUnit with TestPackageable with SampleEnumeration {
   isolated

   "An enumeration" should {

      "support addition of new literals" in {
         val newLiteral = new Literal(theEnumeration, "newLiteral")
         theEnumeration.literals must contain(newLiteral)
         newLiteral.container must beSome.which { x => x == theEnumeration }
      }

      "support deletion of literals" in {
         theEnumeration.literalNamed("LiteralA") match {
            case None => ko
            case Some(literal) =>
               theEnumeration.deleteLiteral("LiteralA")
               theEnumeration.literals must not contain (literal)
               literal.container must beNone
         }
      }

      "reject duplicated literals" in {
         (new Literal(theEnumeration, "LiteralA")) must throwA[DuplicateEnumerationLiteral]
      }
          

   }

}