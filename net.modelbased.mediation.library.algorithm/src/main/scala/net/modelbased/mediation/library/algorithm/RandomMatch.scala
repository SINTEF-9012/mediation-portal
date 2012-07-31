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

import net.modelbased.mediation.library.algorithm.mof._
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader
import scala.util.Random
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.library.algorithm.xsd.XsdToMof

/**
 * Implement a random match algorithm, which given two Mof models, outputs a random
 * mapping between the types and elements declared in the two schemas.
 *
 * Random mappings are needed when effectiveness of matching algorithms
 * must be checked against the null hypothesis.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class RandomMatch extends Mediation {

   private[this] val reader = new MofReader

   /**
    * @inheritdoc
    *
    * We basically extract all the source and target types that are defined as
    * complex types in models received as input. Then we shuffle the target types
    * and associate them to the source type. The degree of match is generated
    * randomly.
    *
    */
   override def execute(in: Mapping, source: Model, target: Model): Unit = {
      out = new Mapping()

      val mofSource = reader.readPackage(source.content)
      mofSource match {
         case Right(sp: Package) =>
            val mofTarget = reader.readPackage(target.content)
            mofTarget match {
               case Right(tp: Package) =>
                  val randomizer = new Random()

                  // Manage the types
                  val allSourceTypes = sp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
                  val allTargetTypes = tp.accept(new Collector(x => x.isInstanceOf[Type]), Nil)
                  allSourceTypes.foldLeft((randomizer.shuffle(allTargetTypes.toList), out)) {
                     case ((Nil, m), v) =>
                        val l = (randomizer.shuffle(allTargetTypes.toList))
                        m.add(new Entry(v.qualifiedName, l.head.qualifiedName, randomizer.nextDouble(), "random matching"))
                        (l.tail, m)
                     case ((head :: tail, m), v) =>
                        m.add(new Entry(v.qualifiedName, head.qualifiedName, randomizer.nextDouble(), "random matching"))
                        (tail, m)
                  }

                  // Manage Elements
                  val allSourceFeatures = sp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
                  val allTargetFeatures = tp.accept(new Collector(x => x.isInstanceOf[Feature]), Nil)
                  val (m2, _) = allSourceFeatures.foldLeft((randomizer.shuffle(allTargetFeatures.toList), out)) {
                     case ((Nil, m), v) =>
                        val l = (randomizer.shuffle(allTargetFeatures.toList))
                        m.add(new Entry(v.qualifiedName, l.head.qualifiedName, randomizer.nextDouble(), "random matching"))
                        (l.tail, m)
                     case ((head :: tail, m), v) =>
                        m.add(new Entry(v.qualifiedName, head.qualifiedName, randomizer.nextDouble(), "random matching"))
                        (tail, m)
                  }

            }
      }
   }

}



/**
 * A simple random mediation between XSD files
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class RandomXsdMediation extends Mediation {

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