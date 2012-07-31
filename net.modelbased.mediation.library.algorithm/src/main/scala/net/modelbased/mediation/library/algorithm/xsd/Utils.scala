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
package net.modelbased.mediation.library.algorithm.xsd

import scala.xml.Node

/**
 * Gather various helper functions that ease the manipulation of XML node in 
 * the context of mediation.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
object Utils {
  
   /**
   * Helper function that searches for an attribute labelled "name" in a given
   * XML node. If the attribute exist, it returns its label, otherwise it returns
   * "anonymous" as a default value. If there exist several attributes labelled
   * "name", it returns the value of the first one.
   * 
   * @param node the XML node whose "name" attribute must be retrieved
   * 
   * @return 	the value of the first attribute labelled "name", or the value 
   * 			"anonymous" if there is no such attribute on the given node 
   */
  def getName(node: Node): String =
    node.attribute("name") match {
    	case None => "anonymous"
    	case Some(x) => x.head.text
  	}

}