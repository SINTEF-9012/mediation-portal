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
 * Exception thrown when two literals share the same name with a single enumeration
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class DuplicateEnumerationLiteral(val context: Enumeration, duplicatedLiteral: String) extends Exception 


/**
 * Exception thrown when two features share the same name within a single class
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class DuplicateClassFeature(val context: Class, duplicateFeatureName: String) extends Exception



/**
 * Exception throw when a package contain two element (types definitions, 
 * packages, etc.) sharing the same name
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class DuplicatePackageElement(val context: Package, duplicateElementName: String) extends Exception



/**
 * Exception thrown when a class has a superclass which is its subclass at the same type
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class CircularClassInheritance(val context: Class) extends Exception