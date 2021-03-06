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
 * Define the expected behaviour of the Linker.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestMofLinker extends SpecificationWithJUnit with SampleMofAst {
   isolated

   val builder = new MofBuilder()
   val linker = new MofLinker()

   "The MoF Linker" should {

      "properly link a feature to its type" in {
         test.accept(builder, Nil)
         val errors = next.accept(linker, Nil)
         errors must beEmpty
         next.modelElement must beSome.like {
            case f: Feature =>
               element.modelElement.map { me => f.`type` must beEqualTo(me) }.getOrElse(ko)
            case _ => ko
         }
      }

      "properly link a feature with a Enumeration type" in {
         test.accept(builder, Nil)
         val errors = day.accept(linker, Nil)
         errors must beEmpty
         weekDays.modelElement must beSome.like {
            case e: Enumeration =>
               day.modelElement.map { me => me.`type` must beEqualTo(e) }.getOrElse(ko)
            case _ => ko
         }
      }

      "properly link a feature with a data type" in {
         test.accept(builder, Nil)
         val errors = creation.accept(linker, Nil)
         errors must beEmpty
         dateTime.modelElement must beSome.like {
            case dt: DataType =>
               creation.modelElement.map { me => me.`type` must beEqualTo(dt) }.getOrElse(ko)
            case _ => ko
         }
      }

      "properly link a feature to its opposite" in {
         test.accept(builder, Nil)
         val errors = next.accept(linker, Nil)
         errors must beEmpty
         next.modelElement must beSome.like {
            case f: Feature =>
               previous.modelElement must beSome.like {
                  case fp: Feature =>
                     f.opposite must beSome.which { x => x == fp }
               }
            case _ => ko
         }
      }

      "properly link a class to its super class" in {
         test.accept(builder, Nil)
         val errors = specialElement.accept(linker, Nil)
         errors must beEmpty
         specialElement.modelElement must beSome.like {
            case c: Class =>
               element.modelElement.map { me => c.superClasses must contain(me) }.getOrElse(ko)
            case _ => ko
         }
      }
   }

}