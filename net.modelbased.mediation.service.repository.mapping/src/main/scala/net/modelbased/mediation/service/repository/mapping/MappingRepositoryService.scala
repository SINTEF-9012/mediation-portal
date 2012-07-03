/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.mapping
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
package net.modelbased.mediation.service.repository.mapping

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._
import net.modelbased.mediation.service.repository.mapping.data.Mapping._
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
      get { context =>
        val uris = (_registry retrieve (List())) map { e => URLHandler.build("/mediation/repositories/mappings/" + e.uid) }
        context.complete(uris)
      } ~
        post { context => 
          val m = new Mapping()
          _registry push m
          context complete URLHandler.build("/mediation/repositories/mappings/" + m.uid)
        } ~ cors("GET", "POST")
    } ~
      path("mediation" / "repositories" / "mappings" / PathElement) { uid => 
        get { context =>
          ifExists(context, uid, { context complete ((_registry pull("uid", uid)).get) }) 
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
          content(as[String]) { status => context =>
            ifExists(context, uid, {
              val mapping: Mapping = ((_registry pull ("uid", uid)).get)
              mapping.status = Status.withName(status)
              _registry push mapping
              context complete "done"
            })
          }
        } ~ cors("GET", "PUT")
      } ~
      path("mediation" / "repositories" / "mappings" / PathElement / "content") { uid =>
        get { context =>
          ifExists(context, uid, {
            val mapping: Mapping = (_registry pull ("uid", uid)).get
            context complete mapping.entries
          })
        } ~
        put {
          content(as[List[Entry]]) { data => context =>
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
        } ~  cors("GET", "PUT", "DELETE")
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
          ifExists(context, uid, {
            val mapping: Mapping = (_registry pull ("uid", uid)).get            
            context complete mapping.get(source, target)
          })
        } ~
        delete { context => 
          ifExists(context, uid, {
            val mapping = (_registry pull ("uid", uid)).get
            val init = mapping.size
            mapping.removeAll(source, target)
            _registry push mapping
            context complete ("0 added, %d removed".format(init - mapping.size))
          })
        } ~ cors("GET", "PUT", "DELETE")
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