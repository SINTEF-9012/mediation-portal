/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.repository.comparison
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
package net.modelbased.mediation.service.repository.comparison

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }

import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement

import net.modelbased.mediation.service.repository.comparison._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.Conversions._
import net.modelbased.mediation.service.repository.comparison.data.JsonComparisonProtocol._

/**
 * REST protocol of the mapping comparison repository service
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
trait ComparisonRepositoryService extends SensAppService {

  override lazy implicit val partnerName = "comparison-repository"

  private[this] val _registry = new ComparisonRegistry()

  private def ifExists(context: RequestContext, id: String, lambda: => Unit) = {
    if (_registry exists ("uid", id))
      lambda
    else
      context fail (StatusCodes.NotFound, "Unknown comparison [" + id + "]")
  }

  val service = {
    path("mediation" / "repositories" / "comparisons") {
      get { context =>
        val uris = (_registry retrieve (List())) map { e => URLHandler.build("/mediation/repositories/comparisons/" + e.uid) }
        context.complete(uris)
      } ~
        post {
          content(as[String]) { s =>
            context =>
              val comparison = new Comparison(oracle = s)
              _registry push comparison
              context complete URLHandler.build("/mediation/repositories/comparisons/" + comparison.uid)
          }
        } ~ cors("GET", "POST")
    } ~
      path("mediation" / "repositories" / "comparisons" / PathElement / "oracle") { uid =>
        get { context =>
          ifExists(context, uid, {
            val comparison = (_registry pull ("uid", uid)).get
            context complete comparison.oracle
          })
        }
      } ~ cors("GET")
  } ~
    path("mediation" / "repositories" / "comparisons" / PathElement / "contents") { uid =>
      get { context =>
        ifExists(context, uid, {
          val comparison = (_registry pull ("uid", uid)).get
          context complete comparison.contents
        })
      } ~
        put {
          content(as[List[Evaluation]]) { data =>
            context =>
              ifExists(context, uid, {
                val comparison: Comparison = (_registry pull ("uid", uid)).get
                val init = data.map { x => x.mapping }.count { x => comparison.contents.contains(x) }
                comparison addAll data
                _registry push comparison
                context complete ("%d added, %d removed".format(data.size - init, comparison.size - init))
              })
          }
        }
    } ~ cors("GET", "PUT")

}