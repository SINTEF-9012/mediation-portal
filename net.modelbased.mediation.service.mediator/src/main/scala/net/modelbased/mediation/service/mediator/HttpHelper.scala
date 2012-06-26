/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.service.mediator
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
package net.modelbased.mediation.service.mediator

import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.DefaultUnmarshallers._
import net.modelbased.sensapp.library.system._

object HttpHelper extends HttpSpraySupport {

  def httpClientName = "mediator-helper"
  
  def createMappingInRepository(partners: PartnerHandler): Future[String] = {
    val repository = partners("mapping-repository").get
    val conduit = new HttpConduit(httpClient, repository._1, repository._2) {
      val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
    }
    conduit.pipeline(Post("/mediation/repositories/mappings",None))
  }
    
} 