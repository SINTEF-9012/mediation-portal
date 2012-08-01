/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.samples.envision
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
package net.modelbased.mediation.samples.envision

import scala.io.Source

import org.specs2.mutable._

import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.model.data.Model

/**
 * Simplistic specification of the expected behavior of the ENVISION mediation
 * environment
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestEnvision extends SpecificationWithJUnit {

  val envision: Envision = new Envision()
  val source1: Model = new Model("SOS", Source.fromFile("src/test/resources/source-SOS-SINTEF.xsd").mkString)
  val source2: Model = new Model("WFS", Source.fromFile("src/test/resources/source-WFS-SINTEF.xsd").mkString)
  val target: Model = new Model("WPS", Source.fromFile("src/test/resources/target-WPS-SINTEF.xsd").mkString)

  "The ENVISION framework" should {

    "mediate between more than two XSD files" in {
      val mapping = envision(List(source1, source2), target)
      println("Resulting Mapping:")
      println(mapping.toString)
      mapping must beLike {
        case m: Mapping => ok
        case _ => ko
      }
    }

  }

}