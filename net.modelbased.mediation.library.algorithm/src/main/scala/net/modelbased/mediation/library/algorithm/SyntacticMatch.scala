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


import scala.xml.{ XML, Node }

import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.{Mapping, Entry}

import net.modelbased.mediation.library.util.StringMatching._


/**
 * Match two XML schemas (XSD files) based on the similarity between the name
 * of the types they contain. The similarity is calculated as the opposite of
 * a Levenshtein distance (1 - distance).
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class SyntacticMatch extends Mediation {

  /**
   * @inheritdoc
   */
  override def execute(context: Mapping, source: Model, target: Model) = {
    val sourceXSD = XML.loadString(source.content)
    val targetXSD = XML.loadString(target.content)
    val sourceTypes = (sourceXSD \ "complexType" ). map {x => ( x \ "@name") .text }
    val targetTypes = (targetXSD \ "complexType" ). map {x => ( x \ "@name") .text }
    
    out = new Mapping()
    for (st <- sourceTypes) {
      val (s, t, d) = targetTypes.foldLeft(("", "", 1.)) {
        case ((x, y, d), v) =>
          val distance = minEdit(st, v)
          //println("med(%s,%s)=%.2f".format(st, v, distance))
          if (distance <= d) {
            (st, v, distance)
          } else {
            (x, y, d)
          }
      }
      val entry = new Entry(s, t, 1.-d, "syntax")
      out.add(entry)
    }

  }

}