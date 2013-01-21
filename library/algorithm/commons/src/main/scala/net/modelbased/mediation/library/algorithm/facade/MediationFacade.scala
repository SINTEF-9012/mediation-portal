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
package net.modelbased.mediation.library.algorithm.facade

import net.modelbased.mediation.library.algorithm.Mediation

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }
import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement
import cc.spray.json._


import net.modelbased.mediation.service.repository.mapping.data.Mapping
import net.modelbased.mediation.client.repository.model.ModelRepository
import net.modelbased.mediation.client.repository.mapping.MappingRepository
import net.modelbased.mediation.client.portal.Portal

import MediationRequestJsonProtocol._

/**
 * Define the common REST API that all mediation services must implements.
 *
 * It implements a bridge pattern to enable the reuse of the same interface with
 * various implementation
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 */
trait AbstractMediationService extends SensAppService {

  val modelRepository = {
    val (host, port) = partners("model-repository").get
    new Portal(host, port) with ModelRepository
  }

  val mappingRepository = {
    val (host, port) = partners("mapping-repository").get
    new Portal(host, port) with MappingRepository
  }

  /**
   * Abstract method defining the name of the this mediation algorithm
   */
  def name: String

  /**
   * Abstract method defining the actual algorithm to be run
   */
  def algorithm: Mediation

  // Define the name of caller
  override lazy val partnerName = name

  val runner = new Runner(algorithm, modelRepository, mappingRepository)

  /**
   * REST API of the mediation service.
   *
   * Accepts POST of mediation request at the proper name
   */
  val service = {
    path("algorithms" / name.replace(" ", "_")) {
      post {
        content(as[MediationRequest]) { request =>
          context =>
            try {
              val result = runner.process(request)
              context.complete(StatusCodes.OK, result)

            } catch {
              case e: Exception =>
                context.complete(StatusCodes.InternalServerError, e.getMessage())

            }
        }
      } ~ cors("POST")
    }
  }

}

/**
 * Encapsulate the mediation process, i.e., fetching the source and target
 * models, triggering the mediation algorithm, and storing the resulting
 * mapping in the repository
 *
 * @param name the name of the algorithm
 *
 * @param algorithm the implementation of the mediation algorithm
 *
 * @param models the models repository to use
 *
 * @param mappings the mapping repository to use
 */
final class Runner(val algorithm: Mediation, val models: ModelRepository, val mappings: MappingRepository) {

  def process(request: MediationRequest): String = {
    val source = models.fetchModelById(request.source)
    val target = models.fetchModelById(request.target)
    val mapping = algorithm(new Mapping(sourceId = source.name, targetId = target.name), source, target)
    mappings.storeMapping(mapping)
  }

}