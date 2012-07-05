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

import org.specs2.mutable._

/**
 * Quick validation of the evaluation function, especially the precision and
 * recall function
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestEvaluation extends SpecificationWithJUnit {
  val tp = 45
  val tn = 45
  val fp = 5
  val fn = 10

  val eval = new Evaluation("x", "y", tp, tn, fp, fn)

  "evaluation objects" should {

    "calculate precision properly" in {
      val p = eval.precision
      p must_== (tp / (tp + fp).toDouble)
    }

    "calculate recall properly" in {
      val r = eval.recall
      r must_== (tp / (tp + fn).toDouble)
    }

    "calculate f-measure properly" in {
      val f = eval.fMeasure
      f must_== (2 * (eval.precision * eval.recall) / (eval.precision + eval.recall))
    }

    "calculate accuracy properly" in {
      val a = eval.accuracy
      a must_== ((tp + tn) / (tp + tn + fp + fn).toDouble)
    }

  }

}