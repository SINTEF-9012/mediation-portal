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

package net.modelbased.mediation.library.algorithm.syntactic

import scala.io.Source
import org.specs2.mutable._

import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping

/**
 * Sample execution of a mediation between two XSD files
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestSyntacticMatch extends SpecificationWithJUnit {

  val sourceStream = classOf[Mediation].getResourceAsStream("/models/article.txt")
  val sourceContent = Source.fromInputStream(sourceStream).mkString
  val source = new Model(
        "source", 
        "sample data model describing scientific articles", 
        "text/mof", 
        sourceContent)
  
  val targetStream = classOf[Mediation].getResourceAsStream("/models/document.txt")
  val targetContent = Source.fromInputStream(targetStream).mkString
  val target = new Model(
        "target", 
        "sample data model describing documents", 
        "text/xsd", 
        targetContent)	
  
  "A Syntactic mediation" should {

    "return a mapping for all features and all types" in {
      val mediation: Mediation = new SyntacticMatch()
      val m = mediation(new Mapping(sourceId=source.name, targetId=target.name), source, target)
      //println(m)
      m.size must_== (125) // Size of the product between document and article
    }

  }

}