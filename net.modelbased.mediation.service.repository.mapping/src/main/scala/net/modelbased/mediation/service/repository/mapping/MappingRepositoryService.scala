/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.mapping
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
package net.modelbased.mediation.service.repository.mapping

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement

/**
 * REFACTORING NEEDED
 */
trait MappingRepositoryService extends SensAppService {

   override lazy implicit val partnerName = "mapping-repository"

   val service = {
      path("mediation" / "repositories" / "mappings") {
         get {
            parameter("flatten" ? false) {
               flatten =>
                  context =>
                     val contents = _registry.retrieve(List())
                     if (flatten) {
                        context.complete(StatusCodes.OK, contents.map { m => toMappingInfo(m) })
                     }
                     else {
                        val uris = contents.map { e => URLHandler.build("/mediation/repositories/mappings/" + e.uid) }
                        context.complete(uris)
                     }
            }
         } ~
            post {
               content(as[MappingData]) { mapping =>
                  context =>
                     if (_registry exists ("uid", mapping.uid)) {
                        context fail (StatusCodes.Conflict, "A mapping identified as '" + mapping.uid + "' already exists!")
                     }
                     else {
                        _registry push mapping
                        context complete (StatusCodes.Created, URLHandler.build("/mediation/repositories/mappings/" + mapping.uid))
                     }
               }
            } ~ cors("GET", "POST")
      } ~
         path("mediation" / "repositories" / "mappings" / PathElement) { uid =>
            get { context =>
               ifExists(context, uid, { context complete ((_registry pull ("uid", uid)).get) })
            } ~
               delete { context =>
                  ifExists(context, uid, {
                     val mapping = _registry pull ("uid", uid)
                     _registry drop mapping.get
                     context complete "true"
                  })
               } ~ cors("GET", "DELETE")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "status") { uid =>
            get { context =>
               ifExists(context, uid, {
                  val mapping = (_registry pull ("uid", uid)).get
                  context complete mapping.status
               })
            } ~
               put {
                  content(as[String]) { status =>
                     context =>
                        ifExists(context, uid, {
                           val mapping: Mapping = ((_registry pull ("uid", uid)).get)
                           mapping.status = Status.withName(status)
                           _registry push mapping
                           context complete "done"
                        })
                  }
               } ~ cors("GET", "PUT")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "asXML") { uid =>
            get { context =>
               ifExists(context, uid, {
                  val mapping: Mapping = (_registry pull ("uid", uid)).get
                  context complete mapping.toXml.toString
               })
            } ~ cors("GET")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content") { uid =>
            get { context =>
               ifExists(context, uid, {
                  val mapping: Mapping = (_registry pull ("uid", uid)).get
                  context complete mapping.entries
               })
            } ~
               put {
                  content(as[List[Entry]]) { data =>
                     context =>
                        ifExists(context, uid, {
                           val mapping: Mapping = (_registry pull ("uid", uid)).get
                           val init = mapping.size
                           mapping addAll data
                           _registry push mapping
                           context complete ("%d added, %d removed".format(data.size, init))
                        })
                  }
               } ~
               delete { context =>
                  ifExists(context, uid, {
                     val mapping: Mapping = (_registry pull ("uid", uid)).get
                     val init = mapping.size
                     mapping.removeAll
                     _registry push mapping
                     context complete ("0 added, %d removed".format(init))
                  })
               } ~ cors("GET", "PUT", "DELETE")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content" / PathElement) { (uid, source) =>
            get { context =>
               ifExists(context, uid, {
                  val mapping = (_registry pull ("uid", uid)).get
                  val data = mapping.get(source)
                  context complete data
               })
            } ~
               delete { context =>
                  ifExists(context, uid, {
                     val mapping = (_registry pull ("uid", uid)).get
                     val init = mapping.size
                     mapping.removeAll(source)
                     _registry push mapping
                     context complete ("0 added, %d removed".format(init - mapping.size))
                  })
               } ~ cors("GET", "PUT", "DELETE")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content" / PathElement / PathElement) { (uid, source, target) =>
            get { context =>
               try {
                  val result = _registry.getEntry(uid, source, target).get
                  context.complete(StatusCodes.OK, result)                  
               
               } catch {
                  case e: IllegalArgumentException =>
                      case e: IllegalArgumentException =>
                      	   context.complete(StatusCodes.NotFound, e.getMessage())
               }
            } ~
               delete { context =>
                  ifExists(context, uid, {
                     val mapping = (_registry pull ("uid", uid)).get
                     val init = mapping.size
                     mapping.removeAll(source, target)
                     _registry push mapping
                     context.complete(StatusCodes.OK, "0 added, %d removed".format(init - mapping.size))
                  })
               } ~ cors("GET", "PUT", "DELETE")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content" / PathElement / PathElement / "approve") { (uid, source, target) =>
            put {
               context =>
                   try {
                      _registry.confirm(uid, source, target, Some(true))
                      context.complete(StatusCodes.OK, "Approval Successful!")

                   } catch {
                      case e: IllegalArgumentException =>
                      	   context.complete(StatusCodes.NotFound, e.getMessage())

                   }
            } ~ cors("PUT")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content" / PathElement / PathElement / "disapprove") { (uid, source, target) =>
            put {
               context =>
                   try {
                      _registry.confirm(uid, source, target, Some(false))
                      context.complete(StatusCodes.OK, "Disapproval Successful!")

                   } catch {
                      case e: IllegalArgumentException =>
                      	   context.complete(StatusCodes.NotFound, e.getMessage())

                   }
            } ~ cors("PUT")
         } ~
         path("mediation" / "repositories" / "mappings" / PathElement / "content" / PathElement / PathElement / "unknown") { (uid, source, target) =>
            put {
               context =>
                   try {
                      _registry.confirm(uid, source, target, None)
                      context.complete(StatusCodes.OK, "Reset Successful!")

                   } catch {
                      case e: IllegalArgumentException =>
                      	   context.complete(StatusCodes.NotFound, e.getMessage())

                   }
            } ~ cors("PUT")
         }
   }


   private[this] val _registry = new MappingRegistry()

   private def ifExists(context: RequestContext, id: String, lambda: => Unit) = {
      if (_registry exists ("uid", id))
         lambda
      else
         context fail (StatusCodes.NotFound, "Unknown mapping [" + id + "]")
   }

}