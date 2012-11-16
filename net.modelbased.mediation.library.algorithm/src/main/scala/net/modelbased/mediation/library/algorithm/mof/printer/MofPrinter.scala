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

import net.modelbased.mediation.library.algorithm.mof._

/**
 * Pretty printer for Mof Models
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MofPrinter extends MofVisitor[StringBuilder, StringBuilder] {

   private[this] var _level: Int = 0

   private[this] def indent =
      _level += 1

   private[this] def outdent =
      _level -= 1

   private[this] def print(input: StringBuilder, text: String): StringBuilder = {
      input.append(text)
   }

   private[this] def newLine(input: StringBuilder) = {
      input.append("\n")
      input.append(List.range(0, _level).foldLeft("") { (acc, v) => acc + "\t" })
   }

   def visitPackage(thePackage: Package, input: StringBuilder): StringBuilder = {
      print(input, "package ")
      print(input, thePackage.name)
      print(input, " {")
      indent; newLine(input)
      thePackage.elements.reverse.tail.reverse.foreach { e =>
         e.accept(this, input)
         newLine(input)
         newLine(input)
      }
      thePackage.elements.last.accept(this, input)
      newLine(input)
      outdent; newLine(input)
      print(input, "}")
   }

   def visitClass(theClass: Class, input: StringBuilder): StringBuilder = {
      if (theClass.isAbstract) { print(input, "abstract ") }
      print(input, "class ")
      print(input, theClass.name)
      if (!theClass.superClasses.isEmpty) {
         print(input, " extends ")
         print(input, theClass.superClasses.map { c => c.name }.mkString(", "))
      }
      print(input, " {")
      indent; newLine(input)
      for (i <- 0 until theClass.features.size - 1) {
         theClass.features(i).accept(this, input)
         newLine(input)
      }
      theClass.features.last.accept(this, input)
      outdent; newLine(input)
      print(input, "}")
   }

   def visitFeature(feature: Feature, input: StringBuilder): StringBuilder = {
      print(input, feature.name)
      print(input, ": ")
      print(input, feature.`type`.name)
      val tmp = if (feature.lower == 0 && feature.upper.isEmpty) print(input, " [0..*]")
      else if (feature.lower == 1 && feature.upper.map { v => v == 1 }.getOrElse(false)) print(input, "")
      else print(input, " [%d..%d]".format(feature.lower, feature.upper.getOrElse(-1)))
      feature.opposite.map { x => print(input, " ~ %s".format(x.qualifiedName)) }.getOrElse(tmp)
   }

   def visitEnumeration(enumeration: Enumeration, input: StringBuilder): StringBuilder = {
      print(input, "enum ")
      print(input, enumeration.name)
      print(input, enumeration.literals.map { l => l.name }.mkString(" { ", ", ", " }"))
   }

   def visitDataType(dataType: DataType, input: StringBuilder): StringBuilder = {
      print(input, "data type ")
      print(input, dataType.name)
   }

   def visitLiteral(literal: Literal, input: StringBuilder): StringBuilder = {
      print(input, literal.name)
   }

   def visitCharacter(character: Character.type, input: StringBuilder): StringBuilder = {
      print(input, "Character")
   }

   def visitString(string: String.type, input: StringBuilder): StringBuilder = {
      print(input, "String")
   }

   def visitInteger(integer: Integer.type, input: StringBuilder): StringBuilder = {
      print(input, "Integer")
   }

   def visitReal(real: Real.type, input: StringBuilder): StringBuilder = {
      print(input, "Real")
   }

   def visitBoolean(boolean: Boolean.type, input: StringBuilder): StringBuilder = {
      print(input, "Boolean")
   }

   def visitByte(byte: Byte.type, input: StringBuilder): StringBuilder = {
      print(input, "Byte")
   }

   def visitAny(any: Any.type, input: StringBuilder): StringBuilder = {
      print(input, "Any")
   }

}