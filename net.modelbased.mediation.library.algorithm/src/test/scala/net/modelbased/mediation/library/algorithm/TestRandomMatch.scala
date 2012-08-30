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


import scala.io.Source
import org.specs2.mutable._


import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.library.algorithm.Commons._


class TestRandomMatch extends SpecificationWithJUnit with SampleMediations {

  val sourceContent = Source.fromFile("src/test/resources/models/article.txt").mkString
  val source = new Model(
        "source", 
        "sample data model describing scientific articles", 
        "text/mof", 
        sourceContent)
  
  val target = new Model(
        "target", 
        "sample data model describing documents", 
        "text/xsd", 
        Source.fromFile("src/test/resources/models/document.txt").mkString)	
 
 
  "A RandomMatch" should {
    
    "return some mapping" in {
      val m = randomMediation(new Mapping(sourceId=source.name, targetId=target.name), source, target)
      //println(m)
      m.size must_== (10 + 5)
    }
    
  } 
  
  
}