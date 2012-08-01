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
import scala.runtime.RichChar


/**
 * Split a given identifier into a list of words. This very useful to process
 * identifier formatted according to the CaML case or to remove underscore for
 * instance
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait SplitIdentifier extends Function[String, List[String]]



/**
 * A simple implementation tailored for handling Java identifiers
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class SplitJavaIdentifier extends SplitIdentifier {

   override def apply(id: String): List[String] = {
          var result = mutable.ListBuffer[String]()
    
    //printf("-----------------\n")
    
    val buffer = new StringBuilder()
    for(c <- id) {
      //printf("DBG: Buffer = %s\n", buffer.mkString("[", ",", "]"))
      val rc = new RichChar(c)
      if (!rc.isLetterOrDigit) {
        if (!buffer.isEmpty) {
          result += buffer.toString
          buffer.clear
        }
        
      } else {
        if (buffer.isEmpty) {
          buffer.append(rc)
  
        } else {
          if (buffer.last.isLower && rc.isLower) { // ...xy
            buffer.append(rc);

          } else if (buffer.last.isLower && rc.isUpper) { // ...xY
            result += buffer.toString
            buffer.clear
            buffer.append(rc)
            
          } else if (buffer.last.isLower && rc.isDigit) {
            result += buffer.toString
            buffer.clear
            buffer.append(rc)
                
          } else if (buffer.last.isUpper && rc.isLower) { // ...Xy
            if (buffer.size > 1) {
              val nextToLast = buffer.length-2 
              if( buffer(nextToLast).isUpper) { //...WXy
                result += buffer.substring(0, buffer.length-1)
                buffer.delete(0, buffer.length-1)
                buffer.append(rc)

              } else {
                buffer.append(rc)
             
              }
            } else {
              buffer.append(rc)
             
            }

          } else if (buffer.last.isUpper && rc.isUpper) { // ...XY
            buffer.append(rc)

          } else if (buffer.last.isUpper && rc.isDigit) {
            result += buffer.toString
            buffer.clear
            buffer.append(rc)
            
          } else if (buffer.last.isDigit && rc.isLetter) {
            result += buffer.toString
            buffer.clear
            buffer.append(rc)

          } else if (buffer.last.isDigit && rc.isDigit) {
            buffer.append(rc)
          
          } else {
            
          }

        }

      }      

    }
    
    if (!buffer.isEmpty) result += buffer.toString
    
    return result.toList.map{ x => x.toLowerCase }
   }

}