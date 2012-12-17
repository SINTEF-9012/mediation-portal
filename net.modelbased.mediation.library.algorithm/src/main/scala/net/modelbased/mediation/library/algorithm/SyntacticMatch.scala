/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.library.algorithm
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
package net.modelbased.mediation.library.algorithm

import scala.xml.{ Utility, XML, Node }
import net.modelbased.mediation.service.repository.model.data.Model
import net.modelbased.mediation.service.repository.mapping.data.{ Mapping, Entry }
import net.modelbased.mediation.library.util.StringToolBox._
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader
import net.modelbased.mediation.library.algorithm.mof.Collector
import net.modelbased.mediation.library.util.MinEditDistance
import net.modelbased.mediation.library.algorithm.mof._

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

   private[this] val algorithmName = "Syntactic Match"

   private[this] val reader = new MofReader
   private[this] val distance = new MinEditDistance()

   /**
    * @inheritdoc
    */
   override def execute(context: Mapping, source: Model, target: Model) = {

      val sp = reader.readPackage(source.content) match {
         case Right(p: Package) => p
         case Left(errors)      => throw new IllegalArgumentException("Ill-formed source model! (%s)".format(source.name));
      }

      val tp = reader.readPackage(target.content) match {
         case Right(p: Package) => p
         case Left(errors)      => throw new IllegalArgumentException("Ill-formed target model (%s)!".format(target.name));
      }

      val capacity = Mof.countTypesAndFeatures(sp) * Mof.countTypesAndFeatures(tp)
      out = new Mapping(sourceId = source.name, targetId = target.name, capacity = capacity)

      // Manage mappings between types
      for (
         st <- sp.accept(new Collector(x => x.isInstanceOf[Type]), Nil);
         tt <- tp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
      ) {
         val similarity = 1. - distance(st.qualifiedName, tt.qualifiedName)
         out.add(new Entry(st.qualifiedName, tt.qualifiedName, similarity, algorithmName))
      }

      // Manage mappings between features
      for (
         sf <- sp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil);
         tf <- tp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
      ) {
         val similarity = 1. - distance(sf.qualifiedName, tf.qualifiedName)
         out.add(new Entry(sf.qualifiedName, tf.qualifiedName, similarity, algorithmName))
      }

      //      val allSourceTypes = sp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
      //      for (st <- allSourceTypes) {
      //         val n = st.name
      //         val allTargetTypes = tp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
      //         val min = allTargetTypes.reduceLeft { (l, r) => if (distance(n, l.name) < distance(n, r.name)) l else r }
      //         out.add(new Entry(st.qualifiedName, min.qualifiedName, 1. - distance(st.name, min.name), "syntactic matching"))
      //      }
      //
      //      // Handle features
      //      val allSourceFeatures = sp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
      //      for (sf <- allSourceFeatures) {
      //         val n = sf.name
      //         val allTargetFeatures = tp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
      //         val min = allTargetFeatures.reduceLeft { (l, r) => if (distance(n, l.name) < distance(n, r.name)) l else r }
      //         out.add(new Entry(sf.qualifiedName, min.qualifiedName, 1. - distance(sf.name, min.name), "syntactic matching"))
      //      }

   }

}
