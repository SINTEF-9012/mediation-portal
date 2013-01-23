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
package net.modelbased.mediation.portal

import akka.actor.{Props, ActorSystem}
import cc.spray._

import net.modelbased.mediation.service.repository.model.ModelRepositoryService
import net.modelbased.mediation.service.repository.mapping.MappingRepositoryService
import net.modelbased.mediation.service.repository.comparison.ComparisonRepositoryService
import net.modelbased.mediation.services.repositories.algorithm.AlgorithmRepositoryService

import net.modelbased.mediation.service.mediator.MediatorService
import net.modelbased.mediation.service.comparator.ComparatorService
import net.modelbased.mediation.service.aggregator.AggregatorService
import net.modelbased.mediation.service.importer.ImporterService

import net.modelbased.mediation.library.algorithm.random.RandomMediationService
import net.modelbased.mediation.library.algorithm.syntactic.SyntacticMediationService

import net.modelbased.sensapp.library.system._



class Boot(override val system: ActorSystem) extends System { 
  
  trait iod { 
    lazy val partners = new Monolith { implicit val actorSystem = system }
    implicit def actorSystem = system 
  }
  
  def services: List[Service] = List(
      new ModelRepositoryService() with iod { },
      new MappingRepositoryService() with iod { },
      new ComparisonRepositoryService() with iod { },
      new AlgorithmRepositoryService() with iod { },
      new MediatorService() with iod { },
      new ComparatorService() with iod { },
      new AggregatorService() with iod { },
      new ImporterService() with iod{ },
      new RandomMediationService with iod{ },
      new SyntacticMediationService with iod { }
  )
  
} 
