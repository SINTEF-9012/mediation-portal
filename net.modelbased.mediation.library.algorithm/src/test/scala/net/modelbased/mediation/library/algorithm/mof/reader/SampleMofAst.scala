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
package net.modelbased.mediation.library.algorithm.mof.reader

import org.specs2.mutable.Specification

/**
 * Define a sample AST that can be used to test various element visitors
 *
 * package test {
 *
 *    package data {
 *
 *    	enumeration WeekDay {
 *    		Saturday, Sunday
 *    	}
 *
 *    }
 *
 *    class Element {
 *        name: String
 *        next: Element # previous
 *        previous: test.Element # Element.next
 *    }
 *
 * }
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait SampleMofAst extends Specification {
   
   val global = MofAst.createGlobalScope

   val saturday = new LiteralNode(Some(global), "Saturday")
   val sunday = new LiteralNode(Some(global), "Sunday")
   val weekDays = new EnumerationNode(Some(global), "WeekDays", Seq(saturday, sunday))

   val data = new PackageNode(Some(global), "data", Seq(weekDays))

   val name = new FeatureNode(Some(global), "name", new Reference("String"), opposite = None)
   val next = new FeatureNode(Some(global), "next", new Reference("Element"), opposite = Some(Reference("previous")))
   val previous = new FeatureNode(Some(global), "previous", new Reference("test", "Element"), opposite = Some(Reference("Element", "next")))
   val day = new FeatureNode(Some(global), "day", new Reference("data", "WeekDays"), opposite = None)
   val element = new ClassNode(Some(global), "Element", false, Seq(name, next, previous, day), Seq.empty)

   
   val description = new FeatureNode(Some(global), "description", new Reference("String"), opposite = None)
   val specialElement = new ClassNode(Some(global), "SpecialElement", false, Seq(description), Seq(new Reference("Element")))
   
   val test = new PackageNode(Some(global), "test", Seq(data, element, specialElement))
 
}