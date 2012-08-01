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

import scala.xml.{ Utility, XML, Node }
import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.{ Mapping, Entry }
import net.modelbased.mediation.library.util.StringToolBox._
import net.modelbased.mediation.library.algorithm.xsd.Utils
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader
import net.modelbased.mediation.library.algorithm.mof.Collector 
import net.modelbased.mediation.library.util.MinEditDistance
import net.modelbased.mediation.library.algorithm.mof._
import net.modelbased.mediation.library.algorithm.xsd.XsdToMof

/**
 * Match two MoF models based on the similarity between the name
 * of the types they contain. The similarity is calculated as the opposite of
 * a Levenshtein distance (1 - distance).
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class SyntacticMatch extends Mediation {

   private[this] val reader = new MofReader
   private[this] val distance = new MinEditDistance()

   /**
    * @inheritdoc
    */
   override def execute(context: Mapping, source: Model, target: Model) = {
      out = new Mapping()

      val mofSource = reader.readPackage(source.content)
      mofSource match {
         case Right(sp: Package) =>
            val mofTarget = reader.readPackage(target.content)
            mofTarget match {
               case Right(tp: Package) =>
                  // Handle types
                  val allSourceTypes = sp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
                  for (st <- allSourceTypes) {
                     val n = st.name
                     val allTargetTypes = tp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
                     val min = allTargetTypes.reduceLeft { (l, r) => if (distance(n, l.name) < distance(n, r.name)) l else r }
                     out.add(new Entry(st.qualifiedName, min.qualifiedName, distance(st.name, min.name), "syntactic matching"))
                  }

                  // Handle features
                  val allSourceFeatures = sp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
                  for (sf <- allSourceFeatures) {
                     val n = sf.name
                     val allTargetFeatures = tp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
                     val min = allTargetFeatures.reduceLeft { (l, r) => if (distance(n, l.name) < distance(n, r.name)) l else r }
                     out.add(new Entry(sf.qualifiedName, min.qualifiedName, distance(sf.name, min.name), "syntactic matching"))
                  }

            }
      }

   }

}


/**
 * A basic syntactic matching between XSD files, based on the above Mof model
 * syntactic matching
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class SyntacticXsdMediation extends Mediation {

   val toMof = new XsdToMof()
   val syntacticMatch = new SyntacticMatch()

   override def execute(in: Mapping, source: Model, target: Model): Unit = {
      val sourceAsMof = toMof(source)
      //println(sourceAsMof.content)
      val targetAsMof = toMof(target)
      //println(targetAsMof.content)
      out = syntacticMatch(new Mapping(), sourceAsMof, targetAsMof)
   }

}