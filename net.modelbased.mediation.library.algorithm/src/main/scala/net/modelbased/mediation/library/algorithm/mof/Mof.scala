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
 * Provide some facilities to manipulate MoF models
 */
object Mof {

   /**
    * @return the number of feature contained in a package (includes all the 
    * features of the package and its sub packages)
    * 
    * @param p the package whose features needed to be counted
    */
   def countFeatures(p: Package): Int =
      p.accept(new Collector(e => e.isInstanceOf[Feature]), Nil).size
   
   /**
    * @return the number of types contained in a package and in its sub packages
    * 
    * @param p the package whose classes need to be counted
    */
   def countTypes(p: Package): Int =
      p.accept(new Collector(e => e.isInstanceOf[Type]), Nil).size
   
   
   /**
    * @return the number of types declared in a package and in its sub packages
    * 
    * @param p the package whose types and features need to be counted
    */
   def countTypesAndFeatures(p: Package): Int = 
      p.accept(new Collector(e => e.isInstanceOf[Type] || e.isInstanceOf[Feature]), Nil).size
   
}