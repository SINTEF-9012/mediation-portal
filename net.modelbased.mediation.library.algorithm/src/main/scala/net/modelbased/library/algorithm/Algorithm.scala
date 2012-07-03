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
package net.modelbased.library.algorithm

import net.modelbased.mediation.service.repository.mapping.data._ 
import net.modelbased.mediation.service.repository.model.data._


trait Computation {
  protected[this] var _out: Mapping = Mapping.Empty 
  def out = _out
  def out_=(m: Mapping) { this._out = m } 
}


trait NotifiedComputation extends Computation {
  def remote: Mapping
  _out = remote
}

trait ModelProcessor extends Function1[Model, Model] with Computation


trait MappingProcessor extends Function1[Mapping, Mapping] with Computation


trait MappingAggregator extends Function1[Set[Mapping], Mapping] with Computation


trait ModelAggregator extends Function1[Set[Model], Model] with Computation


trait Mediation extends Function3[Mapping, Model, Model, Mapping] with Computation {
  
  final def apply(in: Mapping, source: Model, target: Model): Mapping = {
    execute(in, source, target)
    out
  }
  
  protected def execute(in: Mapping, source: Model, target: Model): Unit

}
