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
package net.modelbased.mediation.library.algorithm


import scala.util.Random
import scala.xml.{XML, Node}

import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.model.data._



/**
 * Implement a random match algorithm, which given two XSD schemas, outputs a random
 * mapping between the types declared in the two schemas.
 * 
 * Random mappings are needed when effectiveness of matching algorithms
 * must be checked against the null hypothesis.
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class RandomMatch extends Mediation {


  /**
   * @inheritdoc
   * 
   * We basically extract all the source and target types that are defined as
   * complex types in models received as input. Then we shuffle the target types
   * and associate them to the source type. The degree of match is generated 
   * randomly.
   * 
   * @todo take care of complexTypes declared inside elements ...
   */
  override def execute(in: Mapping, source: Model, target:Model): Unit = {
    val randomizer = new Random()
    val sourceXSD = XML.loadString(source.content)
    val targetXSD = XML.loadString(target.content)
    val sourceTypes = sourceXSD \\ "complexType" //\ "@name"
    val targetTypes = targetXSD \\ "complexType" //\ "@name"
    val (_, m) = sourceTypes.foldLeft((randomizer.shuffle(targetTypes.toList), new Mapping())){ 
      case ((Nil, m), v) =>
        val l = (randomizer.shuffle(targetTypes.toList))
        m.add(new Entry(extractName(v), extractName(l.head), randomizer.nextDouble(), "randomMatch"))
        (l.tail, m)
      case ((head::tail, m), v) =>
          val sName = extractName(v)
          val tName = extractName(head)
          m.add(new Entry(sName, tName, randomizer.nextDouble(), "randomMatch"))
          (tail, m)
    }
    out = m  // We place the result into the out place holder
  }
  

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
  private[this] def extractName(node: Node): String =
    node.attribute("name") match {
    	case None => "anonymous"
    	case Some(x) => x.head.text
  	}
 

}