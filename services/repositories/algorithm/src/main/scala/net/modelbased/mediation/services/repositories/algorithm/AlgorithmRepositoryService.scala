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
package net.modelbased.mediation.services.repositories.algorithm


import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler } 
import net.modelbased.mediation.services.repositories.algorithm.AlgorithmJsonProtocol._
import net.modelbased.mediation.library.data.Algorithm 
import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement

trait AlgorithmRepositoryService extends SensAppService {
  
   private[this] val _registry = new AlgorithmRegistry()

   override implicit lazy val partnerName = "algorithm-repository"

   val service = {
      path("mediation" / "repositories" / "algorithms") {
         get {
            parameter("flatten" ? false) {
               flatten =>
                  context =>
                     val contents = _registry.retrieve(List())
                     if (flatten) {
                        context.complete(StatusCodes.OK, contents.map{ a => a}) 
                     } else {
                        val uris = contents.map { e => URLHandler.build("/mediation/repositories/algorithms/" + e.id) }
                        context complete uris
                     }
            }
         } ~
            post {
               content(as[Algorithm]) { algo =>
                  context =>
                     if (_registry.exists("id", algo.id)) {
                        context.fail(StatusCodes.Conflict, "An algorithm identified as [" + algo.id + "] already exists!")
                     }
                     else {
                        _registry.push(algo)
                        context.complete(StatusCodes.Created, URLHandler.build("/mediation/repositories/algorithms/" + algo.id))
                     }
               }
            } ~ cors("GET", "POST")
      } ~
         path("mediation" / "repositories" / "algorithms" / PathElement) { name =>
            get { context =>
               ifExists(context, name, { context.complete(_registry.pull("id", name).get) })
            } ~
               delete { context =>
                  ifExists(context, name, {
                     val algo = _registry.pull("id", name)
                     _registry.drop(algo.get)
                     context.complete("true")
                  })
               } ~
               put {
                  content(as[Algorithm]) { algo =>
                     context =>
                        ifExists(context, algo.id, {
                           _registry.push(algo)
                           context.complete(algo)
                        })
                  }
               } ~ cors("GET", "DELETE", "PUT")
         } 
   }


   private def ifExists(context: RequestContext, id: String, lambda: => Unit) = {
      if (_registry.exists("id", id))
         lambda
      else
         context fail (StatusCodes.NotFound, "Unknown algorithm [" + id + "]")
   }
}