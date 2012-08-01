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
package net.modelbased.mediation.library.algorithm

import scala.xml._
import scala.io.Source
import org.specs2.mutable._

import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.library.algorithm.Commons._

/**
 * Sample execution of a mediation between two XSD files
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestSyntacticMatch extends SpecificationWithJUnit with SampleXsdMediations {

  val xsd = Utility.trim(XML.loadFile("src/test/resources/schemas/article.xsd"))
  val source = new Model("source", xsd.toString)
  val target = new Model("target", Utility.trim(XML.loadFile("src/test/resources/schemas/document.xsd")).toString)

  
  "A Syntactic mediation" should {

    "return a mapping for all features and all types" in {
      val m = syntacticMediation(new Mapping(), source, target)
      //println(m)
      val expected = (xsd \\ "complexType").size + (xsd \\ "element").size + 1 // +1 for the schema itself (considered as en element)
      m.size must_== expected
    }

  }

}