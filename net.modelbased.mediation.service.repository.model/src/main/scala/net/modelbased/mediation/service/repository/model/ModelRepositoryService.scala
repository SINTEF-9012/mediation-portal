/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.model
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
package net.modelbased.mediation.service.repository.model

import net.modelbased.sensapp.library.system.{Service => SensAppService, URLHandler} 
import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.service.repository.model.data.ModelJsonProtocol._
import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement


trait ModelRepositoryService extends SensAppService {
  
  override lazy val name = "model-repository"

      val service = { 
    path("mediation" / "repositories" / "models") {
      get { context =>
        val uris = (_registry retrieve(List())) map { buildUrl(context, _) }
        context complete uris
      } ~
      post {
        content(as[Model]) { model => context =>
          if (_registry exists ("name", model.name)){
            context fail (StatusCodes.Conflict, "A Model identified as ["+ model.name +"] already exists!")
          } else {
            _registry push model
            context complete (StatusCodes.Created, buildUrl(context, model))
          }
        }
      } ~ cors("GET", "POST")
    } ~
    path("mediation" / "repositories" / "models" / PathElement ) { name =>
      get { context =>
        ifExists(context, name , { context complete (_registry pull ("name", name)).get})
      } ~
      delete { context =>
        ifExists(context, name, {
          val model = _registry pull ("name", name)
          _registry drop model.get
          context complete "true"
        })
      } ~
      put {
        content(as[String]) { data => context => 
          ifExists(context, name, {
            val model = (_registry pull ("name", name)).get
            model.content = data
            _registry push model
            context complete model
          })
        }
      } ~ cors("GET", "DELETE", "PUT")
    }
  }
  
  private[this] val _registry = new ModelRegistry()
  
  private def buildUrl(ctx: RequestContext, e: Model) = { URLHandler.build(ctx, ctx.request.path  + "/"+ e.name)  }
  
  private def ifExists(context: RequestContext, id: String, lambda: => Unit) = {
    if (_registry exists ("name", id))
      lambda
    else
      context fail(StatusCodes.NotFound, "Unknown model [" + id + "]") 
  } 
}