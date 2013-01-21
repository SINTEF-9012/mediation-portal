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
package net.modelbased.mediation.library.util


/**
 * Implements a min edit distance (aka. Levenshtein distance)
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MinEditDistance extends StringDistance {

  
  override def apply(left: String, right: String): Double = {

    // for all i and j, d[i,j] will hold the Levenshtein distance between
    // the first i characters of s and the first j characters of t;
    // note that d has (m+1)x(n+1) values
    val data = Array.ofDim[Double](left.size+1, right.size+1)
    
    // source prefixes can be transformed into empty string by
    // dropping all characters 
    for (i <- 0 to left.size) { data(i)(0) = i }

    // target prefixes can be reached from empty source prefix
    // by inserting every characters
    for (j <- 0 to right.size) { data(0)(j) = j }

    for (j <- 1 to right.size) {
      for (i <- 1 to left.size) {
        if (left(i-1) == right(j-1)) {
          data(i)(j) = data(i - 1)(j - 1) // no operation required
        
        } else {
          data(i)(j) = 
            List(	data(i - 1)(j) + 1, 
            		data(i)(j - 1) + 1, 
            		data(i - 1)(j - 1) + 1
            	).min
        }
      }
    }

    //println(data.map{x => x.mkString("[", ", ", "]")}.mkString("\n"))
    
    return data(left.size)(right.size) / math.max(left.size, right.size).toDouble
  }

}