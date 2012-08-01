/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.portal
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
package net.modelbased.mediation.service.repository.mapping

import scala.util.Random

import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._

/**
 * Simpler mocking object replacing the matching algorithm between models.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object MatcherMock {

  private[this] val source = List("foo", "bar", "baz", "quz")
  private[this] val target = source.foldLeft(List[String]()) {
    (acc, v) =>
      (v :+ v.last) :: acc
  }

  /**
   * Define a random mapping by mapping source element some of the target elements.
   */
  def mapping: Mapping = {
    val generator = new Random()
    (source.zip(generator.shuffle(target))).foldLeft(new Mapping()) {
      (acc, v) =>
        acc.add(new Entry(v._1, v._2, generator.nextDouble(), "mapping-mock"))
        acc
    }
  }

}