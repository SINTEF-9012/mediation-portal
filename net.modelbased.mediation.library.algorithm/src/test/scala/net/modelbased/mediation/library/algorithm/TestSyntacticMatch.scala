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


import scala.xml._
import scala.io.Source
import org.specs2.mutable._


import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.library.algorithm.Commons._


class TestSyntacticMatch extends SpecificationWithJUnit {

  val source = new Model("source", Source.fromFile("src/test/resources/schemas/article.xsd").mkString)
  val target = new Model("target", Source.fromFile("src/test/resources/schemas/document.xsd").mkString)	
 
 
  "A SyntacticMatch" should {
    
    "return some mapping" in {
      val m = syntacticMatch(new Mapping(), source, target)
      println(m)
      m.size must_== 5
    }
    
  } 
  
  
}