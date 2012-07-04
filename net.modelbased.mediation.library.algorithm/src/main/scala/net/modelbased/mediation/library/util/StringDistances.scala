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
package net.modelbased.mediation.library.util



import scala.collection.mutable



trait StringDistance extends Function2[String, String, Double]

trait ListDistance extends Function2[List[String], List[String], Double]

/**
 * Defines a common library of functions for relative comparison of String, i.e.,
 * comparison that returns a value between 0 and 1 (0 meaning that the string are
 * different and 1 meaning they are the same).
 * 
 * To add new function to this library, just add a new "val" that instantiate the
 * function-object that carries your algorithm
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object StringMatching {

  val minEdit = new MinEditDistance()

  val threeGram = new NGramDistance(3)

  def nGram(n: Int) = new NGramDistance(n)

  val cosine = new CosineDistance()
}

/**
 * Calculate a normalised distance between two list of words. The calculation is
 * based on a distance in a vector space, where each word represent one single 
 * dimension.
 * 
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class CosineDistance extends ListDistance {

  /**
   * This function calculates several things in one loop, for performance
   * reasons and returns a tuple containing several results
   *  - It calculates the norm of the first vector (so called n1)
   *  - It calculates the norm of the second vector (so called n2)
   *  - It calculates the scalar product of the two vectors (so called p)
   *  It returns a tuple made of (n1, n2, p)
   */
  private[this] def f(v1: Map[String, Int], v2: Map[String, Int]): (Double, Double, Double) = {
    var n1 = 0.
    var n2 = 0.
    var p = 0.
    v1.keySet.foreach { k =>
      n1 = n1 + (v1(k) * v1(k))
      n2 = n2 + (v2(k) * v2(k))
      p = p + (v1(k) * v2(k))
    }
    (math.sqrt(n1), math.sqrt(n2), p)
  }

  /**
   * This function calculates the angle between two vectors. As the vectors of
   * interest cannot have any negative members, we do not calculate a
   * normalized angle (a value ranging over [0,1] reflecting the angle between
   * [0, pi/2]) but we directly return the cosine of the angle (which is a
   * value ranging over [0, 1]
   *
   * @param v1 the first vector of interest
   * 
   * @param v2 the second vector of interest
   */
  private[this] def angle(v1: Map[String, Int], v2: Map[String, Int]): Double = {
    val t = f(v1, v2)
    if (t._1 != 0 && t._2 != 0) {
      //acos(scalarProduct(v1, v2) / (n1 * n2))
      t._3.toDouble / (t._1 * t._2)
    } else {
      1
    }
  }

  /**
   * Implementation of the cosine distance
   */
  override def apply(left: List[String], right: List[String]): Double = {
    val v1 = mutable.Map[String, Int]()
    val v2 = mutable.Map[String, Int]()

    left.foreach { s =>
      if (v1.contains(s)) {
        v1(s) = v1(s) + 1
      } else {
        v1(s) = 1
      }
      if (!v2.contains(s)) {
        v2(s) = 0
      }
    }
    right.foreach { s =>
      if (v2.contains(s)) {
        v2(s) = v2(s) + 1
      } else {
        v2(s) = 1
      }
      if (!v1.contains(s)) {
        v1(s) = 0
      }
    }

    1 - angle(v1.toMap, v2.toMap)
  }

}



/**
 * Implement a N-Gram distance
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class NGramDistance(n: Int) extends StringDistance {

  
  /**
   * Calculate all possible n-grams existing in a given string
   * 
   * @param n the size of the n-grams to produces
   * 
   * @param source the string from which n-grams must be derived
   * 
   * @return the set of n-gram that can be derived from the source string
   */
  private[this] def nGram(n: Int, source: String): Set[String] = {
    var result = Set[String]()
    var index = 0
    while( index < source.size ) {
      if (index - n >= 0 ) result = result + source.substring(index-n, index)
      if (index + n <= source.size) result = result + source.substring(index, index+n)
      index = index + 1
    }
    //printf("%d-grams(%s) = %s\n", n, source, result.mkString("[", ",", "]"))
    return result
  }
  
  /**
   * The implementation of the n-gram distance
   */
  override def apply(left: String, right: String): Double = {
   new CosineDistance()(nGram(n, left).toList, nGram(n, right).toList)
  }

}