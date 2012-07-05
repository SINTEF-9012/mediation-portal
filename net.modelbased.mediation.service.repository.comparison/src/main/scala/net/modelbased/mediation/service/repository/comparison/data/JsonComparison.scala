/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.comparison
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
package net.modelbased.mediation.service.repository.comparison.data


import cc.spray.json.DefaultJsonProtocol


object Conversions {
  
  /**
   * Convert a JsonComparison into a Comparison
   * 
   * @param jsc the JsonComparison to convert
   * 
   * @return a equivalent Comparison object
   */
  implicit def toComparison(jsc: JsonComparison) = {
    val contents = jsc.contents.foldLeft(Map[String, Evaluation]()){(acc, e) => acc + (e.mapping -> e)}
    new Comparison(jsc.uid, jsc.oracle, contents, jsc.note)
  }
  
  /**
   * Convert a Comparison object into a JsonComparison
   * 
   * @param c the comparison object to convert
   * 
   * @return the equivalent JsonComparison objects
   */
  implicit def fromComparison(c: Comparison) =
    new JsonComparison(c.uid, c.oracle, c.contents.values.toList, c.note) 
}


/**
 * A simple class to serialise Comparison object in JSON 
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
sealed case class JsonComparison (val uid: String, val oracle: String, val contents: List[Evaluation], val note: String)


object JsonComparisonProtocol extends DefaultJsonProtocol {
  implicit val entryFormat = jsonFormat(Evaluation, "mapping", "precision", "recall")
  implicit val mappingFormat = jsonFormat(JsonComparison, "uid", "oracle", "contents", "note")
}
  