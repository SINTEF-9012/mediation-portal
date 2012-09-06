/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.comparison
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
package net.modelbased.mediation.service.repository.comparison

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }

import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement

import net.modelbased.mediation.service.repository.comparison._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

/**
 * REST protocol of the mapping comparison repository service
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait ComparisonRepositoryService extends SensAppService {

   val REPOSITORY_URL = "/mediation/repositories/comparisons"

   override lazy implicit val partnerName = "comparison-repository"

   private[this] val _registry = new EvaluationRegistry()

   private def ifExists(context: RequestContext, id: String, lambda: => Unit) = {
      if (_registry exists ("oracle", id))
         lambda
      else
         context fail (StatusCodes.NotFound, "Unknown comparison [" + id + "]")
   }

   val service = {
      path("mediation" / "repositories" / "comparisons") {
         get {
            parameter("flatten" ? false) {
               flatten =>
                  context =>
                     val contents = _registry.retrieve(List())
                     if (flatten) {
                        context.complete(StatusCodes.OK, contents)
                     }
                     else {
                        val urls = _registry.allOracles.map { o => URLHandler.build(REPOSITORY_URL + "/" + o) }
                        context.complete(StatusCodes.OK, urls)
                     }
            }
         } ~
            post {
               content(as[List[Evaluation]]) { evaluations =>
                  context =>
                     evaluations.foreach { e => _registry.add(e) }
                     val urls = _registry.allOracles.map { o => URLHandler.build(REPOSITORY_URL + "/" + o) }
                     context.complete(urls)
               }
            } ~ cors("GET", "POST")
      } ~
         path("mediation" / "repositories" / "comparisons" / PathElement) { oracle =>
            get { context =>
               val evaluations = _registry.findByOracle(oracle)
               context.complete(evaluations)
            } ~
               put {
                  content(as[List[Evaluation]]) { evaluations =>
                     context =>
                        val (discarded, toAdd) = evaluations.partition { e => e.oracle == oracle }
                        toAdd.foreach { e => _registry.add(e) }
                        val message = "%d added/updated (%d discard because of irrelevant oracle)".format(toAdd.size, discarded.size)
                        context.complete(message)
                  }
               } ~ delete {
                  context =>
                     val evaluations = _registry.findByOracle(oracle)
                     evaluations.foreach { evaluation => _registry.remove(evaluation) }
                     context.complete(StatusCodes.OK, "%d comparison deleted.".format(evaluations.size))
               } ~ cors("GET", "PUT")
         } ~
         path("mediation" / "repositories" / "comparisons" / PathElement / "stats") { oracle =>
            get {
               context =>
                  val result = _registry.findByOracle(oracle).map{ c => c.toStatistics }
                  context.complete(StatusCodes.OK, result);
            } ~ cors("GET")
         } ~
         path("mediation" / "repositories" / "comparisons" / PathElement / PathElement) { (oracle, mapping) =>
            get { context =>
               _registry.findByOracleAndMapping(oracle, mapping) match {
                  case None =>
                     val message = "No evaluation of '%s' against '%s'.".format(mapping, oracle)
                     context.complete(StatusCodes.NotFound, message)
                  case Some(e) =>
                     context.complete(e)
               }
            } ~ put {
               content(as[Evaluation]) { evaluation =>
                  context =>
                     _registry.findByOracleAndMapping(oracle, mapping) match {
                        case None =>
                           _registry.add(evaluation)
                           val message = "1 evaluation added."
                           context.complete(StatusCodes.OK, message)
                        case Some(e) =>
                           _registry.add(e)
                           val message = "1 evaluation replaced."
                           context.complete(StatusCodes.OK, message)
                     }

               }
            } ~ delete {
               context =>
                  _registry.findByOracleAndMapping(oracle, mapping) match {
                     case Some(evaluation) =>
                        _registry.remove(evaluation)
                        context.complete(StatusCodes.OK, "1 evaluation deleted!")
                     case None =>
                        context.complete(StatusCodes.BadRequest, "Unknown comparison (oracle=%s, mapping=%s)".format(oracle, mapping))
                  }
            } ~ cors("GET", "PUT")
         } ~
         path("mediation" / "repositories" / "comparisons" / PathElement / PathElement / "stats") { (oracle, mapping) =>
            get { context =>
               _registry.findByOracleAndMapping(oracle, mapping) match {
                  case None =>
                     val message = "No evaluation of '%s' against '%s'.".format(mapping, oracle)
                     context.complete(StatusCodes.NotFound, message)
                  case Some(e) =>
                     context.complete(e.toStatistics)
               }
            } ~ cors("GET", "PUT")
         }
   }

}