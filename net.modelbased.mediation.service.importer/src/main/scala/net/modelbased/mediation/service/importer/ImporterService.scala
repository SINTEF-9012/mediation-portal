/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.importer
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
package net.modelbased.mediation.service.importer

import net.modelbased.sensapp.library.system.{ Service => SensAppService, URLHandler }
import cc.spray.http._
import cc.spray._
import cc.spray.directives.PathElement
import cc.spray.json._
import RequestJsonProtocol._


/**
 * REST API of the importer service
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
trait ImporterService extends SensAppService {

   override lazy val partnerName = "importer"

   val runner = new Runner(partners)

   val service = {
      path("importer") {
         post {
            content(as[Request]) {
               request =>
                  context =>
                     try {
                    	 val result = runner.process(request)
                    	 context.complete(StatusCodes.OK, result)
                    	 
                     } catch {
                        case e: Exception =>
                        	context.complete(StatusCodes.InternalServerError, e.getMessage)
                     }
            }
         } ~ cors("POST")
      } ~
         path("importer" / "formats") {
            get {
               context =>
               	context.complete(StatusCodes.OK, List(Format.XSD, Format.TEXT, Format.ECORE))
            }
         }
   }

}