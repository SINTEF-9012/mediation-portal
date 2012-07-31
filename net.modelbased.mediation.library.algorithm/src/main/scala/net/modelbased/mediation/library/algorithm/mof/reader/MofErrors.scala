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
package net.modelbased.mediation.library.algorithm.mof.reader

abstract class MofError(val context: Node, val message: String) {

   override def toString: String =
      message
}

class UnknownFeatureType(override val context: Node,
                         val typeName: String)
      extends MofError(context,
         "Unknown feature's type '%s'".format(typeName))

class InternalError(override val context: Node,
                    override val message: String)
      extends MofError(context, message)

class UnknownSuperClass(override val context: Node,
                        val superClassName: String)
      extends MofError(context,
         "Unknown super class '%s'".format(superClassName))

class CircularInheritance(override val context: Node,
                        val superClassName: String)
      extends MofError(context,
         "Circular inheritance hierarchy of class '%s'".format(superClassName))

class IllegalInheritance(override val context: Node,
                         val illegalTypeName: String)
      extends MofError(context,
         "Illegal class inheritance from a '%s'".format(illegalTypeName))

class ParsingError(val errorMessage: String, val line: Int, val column: Int) extends MofError(null, errorMessage + " at line %d, column %d".format(line, column))

class DuplicateLiteral(override val context: Node,
                       val literal: String)
      extends MofError(context,
         "Duplicate literal '%s'".format(literal))

class DuplicateFeature(override val context: Node,
                       val featureName: String)
      extends MofError(context,
         "duplicate feature named '%s'".format(featureName))


class DuplicateElement(override val context: Node,
                       val elementName: String)
      extends MofError(context,
         "duplicate package element named '%s'".format(elementName))