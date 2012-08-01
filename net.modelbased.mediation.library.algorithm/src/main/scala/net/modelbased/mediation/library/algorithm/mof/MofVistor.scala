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

/**
 * Abstract interface of the MofVistors
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
abstract trait MofVisitor[I, O] {

   def visitPackage(thePackage: Package, input: I): O

   def visitClass(theClass: Class, input: I): O

   def visitFeature(feature: Feature, input: I): O

   def visitEnumeration(enumeration: Enumeration, input: I): O

   def visitLiteral(literal: Literal, input: I): O

   def visitCharacter(character: Character.type, input: I): O

   def visitString(string: String.type, input: I): O

   def visitInteger(integer: Integer.type, input: I): O

   def visitReal(real: Real.type, input: I): O

   def visitBoolean(boolean: Boolean.type, input: I): O

   def visitByte(byte: Byte.type, input: I): O

   def visitAny(any: Any.type, input: I): O
}

/**
 * Abstract interface of the "visitable" objects
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
abstract trait Visitable {

   def accept[I, O](visitor: MofVisitor[I, O], input: I): O

}