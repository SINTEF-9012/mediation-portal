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

/**
 * Provide facilities for collecting MoF models element that matches a given criterion
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Collector(val filter: Element => Boolean) extends MofVisitor[List[Element], List[Element]] {

   def visitPackage(thePackage: Package, input: List[Element]): List[Element] = {
      val local = thePackage.elements.foldLeft(input) { (acc, e) => e.accept(this, acc) }.toList
      if (filter(thePackage))
         thePackage :: local
      else
         local
   }

   def visitClass(theClass: Class, input: List[Element]): List[Element] = {
      val local = theClass.features.foldLeft(input) { (acc, f) => f.accept(this, acc) }.toList
      if (filter(theClass))
         theClass :: local
      else
         local
   }

   def visitFeature(feature: Feature, input: List[Element]): List[Element] = {
      if (filter(feature)) feature :: input
      else input
   }

   def visitDataType(dataType: DataType, input: List[Element]): List[Element] = {
      if (filter(dataType)) {
         dataType :: input
      }
      else {
         input
      }
   }

   def visitEnumeration(enumeration: Enumeration, input: List[Element]): List[Element] = {
      val local = enumeration.literals.foldLeft(input) { (acc, l) => l.accept(this, acc) }.toList
      if (filter(enumeration)) {
         enumeration :: local
      }
      else {
         local
      }
   }

   def visitLiteral(literal: Literal, input: List[Element]): List[Element] = {
      if (filter(literal)) literal :: input else input
   }

   def visitCharacter(character: Character.type, input: List[Element]): List[Element] = {
      if (filter(character)) character :: input else input
   }

   def visitString(string: String.type, input: List[Element]): List[Element] = {
      if (filter(string)) string :: input else input
   }

   def visitInteger(integer: Integer.type, input: List[Element]): List[Element] = {
      if (filter(integer)) integer :: input else input
   }

   def visitReal(real: Real.type, input: List[Element]): List[Element] = {
      if (filter(real)) real :: input else input
   }

   def visitBoolean(boolean: Boolean.type, input: List[Element]): List[Element] = {
      if (filter(boolean)) boolean :: input else input
   }

   def visitByte(byte: Byte.type, input: List[Element]): List[Element] = {
      if (filter(byte)) byte :: input else input
   }

   def visitAny(any: Any.type, input: List[Element]): List[Element] = {
      if (filter(any)) any :: input else input
   }
}