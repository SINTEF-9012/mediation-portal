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

import org.specs2.mutable._

import scala.xml.{ XML, Node, NodeSeq, Utility }
import scala.io.Source

import net.modelbased.mediation.service.repository.model.data.Model

/**
 * This is a simple test for the XSD cleaner feature. We basically assert that
 * the cleaner properly remove implicit types
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
class TestXsdCleaner extends SpecificationWithJUnit {

  val clean = new XsdCleaner()
  val home = "src/test/resources/cleaner/"

  "XSD cleaner" should {

    "remove the implicit types in sequence" in {
      val inputFile = home + "sequence-dirty.xsd"
      val oracleFile = home + "sequence-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
    }

    "remove the implicit types in choice" in {
      val inputFile = home + "choice-dirty.xsd"
      val oracleFile = home + "choice-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
    }

    "remove the implicit types in all" in {
      val inputFile = home + "all-dirty.xsd"
      val oracleFile = home + "all-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
    }
    
    "remove the implicit types extensions" in {
      val inputFile = home + "extension-dirty.xsd"
      val oracleFile = home + "extension-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
    }

    "remove nested implicit types" in {
      val inputFile = home + "nested-dirty.xsd"
      val oracleFile = home + "nested-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
    }
    
    "not change clean schemas" in {
      val inputFile = home + "nested-expected.xsd"
      val oracleFile = home + "nested-expected.xsd"
      val input = Utility.trim(XML.loadString(Source.fromFile(inputFile).mkString))
      val expected = Utility.trim(XML.loadString(Source.fromFile(oracleFile).mkString))
      val result = clean(new Model("input", input.toString))
      val actual = Utility.trim(XML.loadString(result.content))
      actual must beEqualToIgnoringSpace(expected)
      
    }

  }

}