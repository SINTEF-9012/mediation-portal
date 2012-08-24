/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.samples.envision
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
package net.modelbased.mediation.samples.envision

import scala.xml.{ XML, Node, NodeSeq }

import net.modelbased.mediation.library.algorithm._
import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.service.repository.model.data.Model

/**
 * An helper that that generate a aggreagtion of a list of models.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ModelAggregation extends Function[List[Model], Model] {

  override def apply(models: List[Model]): Model = {
    val schema = <xs:schema> {
      models.foldLeft(NodeSeq.Empty) {
        (acc, v) =>
          val xsd = XML.loadString(v.content) 
          val nodes = xsd.child
          acc ++ nodes
      }
    } </xs:schema>
    new Model("aggregation", "Aggregated model from " + models.map{ x => x.name }.mkString(", "), "text/xsd", schema.toString())
  }
 
}	

/**
 * Define an ENVISION-specific mediation
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class EnvisionMediation extends Mediation {

  import net.modelbased.mediation.library.algorithm.Commons._

  
  override def execute(in: Mapping, source: Model, target: Model) = {
    out = xsdSyntacticMatch(in, source, target)
  }

}

/**
 * Define a the Envision wrapper that provides the features needed in ENVISION
 */
class Envision extends Function2[List[Model], Model, Mapping] {

  val aggregation = new ModelAggregation
  val eMediation = new EnvisionMediation

  override def apply(sources: List[Model], target: Model): Mapping = {
    println(target.content)
    val source = aggregation(sources)
    printf(source.content)
    eMediation(new Mapping(sourceId=source.name, targetId=target.name), source, target)
  }

}