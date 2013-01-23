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
package net.modelbased.mediation.client.repositories.algorithm

import scala.xml._
import scala.io.Source
import net.modelbased.sensapp.library.system._
import akka.dispatch._
import akka.util.duration._
import cc.spray.client._
import cc.spray.json._
import cc.spray.typeconversion.SprayJsonSupport._
import cc.spray.typeconversion.DefaultUnmarshallers._
import cc.spray.json.DefaultJsonProtocol._

import net.modelbased.mediation.client.portal.Portal

import net.modelbased.mediation.library.data.Algorithm
import net.modelbased.mediation.services.repositories.algorithm._
import net.modelbased.mediation.services.repositories.algorithm.AlgorithmJsonProtocol._

/**
 * Client API for the algorithm repository. It enable basic CRUD operations over
 * algorithms descriptions.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
trait AlgorithmRepository extends Portal {

  val ALGORITHM_REPOSITORY = "/sensapp/mediation/repositories/algorithms"

  /**
   * Retrieve the complete list of algorithms stored in the repository
   *
   * @return the list of mediation algorithms
   */
  def fetchAllAlgorithms: List[Algorithm] = {
    val conduit = new HttpConduit(httpClient, host, port) {
      val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Algorithm]] }
    }
    val futureUrl = conduit.pipeline(Get(ALGORITHM_REPOSITORY + "?flatten=true", None))
    Await.result(futureUrl, intToDurationInt(5) seconds) 
  }

  /**
   * Retrieve a given algorithm based on its unique ID
   *
   * @param id the unique ID (i.e., name) of the algorithm of interest
   *
<<<<<<< HEAD
   * @return the algorithm if the given name matches an algorithm in the repository
=======
   * @return Some(algo) if the given name matches an algorithm in the repository
   * or None otherwise
>>>>>>> 6e49550c048a4a7b70a1a046be9355c2b2f5b5fd
   */
  def fetchAlgorithmById(id: String): Algorithm = {
    val conduit = new HttpConduit(httpClient, host, port) {
      val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Algorithm] }
    }
    val futureUrl = conduit.pipeline(Get(ALGORITHM_REPOSITORY + "/" + id, None))
    Await.result(futureUrl, intToDurationInt(5) seconds)		 
  }

  /**
   * Add a new description in the algorithm repository
   *
   * @param algo the algorithm to be added
   *
   */
  def addAlgorithm(algo: Algorithm): String = {
    val conduit = new HttpConduit(httpClient, host, port) {
      val pipeline = { simpleRequest[Algorithm] ~> sendReceive ~> unmarshal[String] }
    }
    val futureUrl = conduit.pipeline(Post(ALGORITHM_REPOSITORY, algo))
    Await.result(futureUrl, intToDurationInt(5) seconds)
  }

  /**
   * Delete a given algorithm description from the repository
   *
   * @param the algorithm to be deleted from the repository
   */
  def deleteAlgorithm(algo: Algorithm): Unit = {
    val conduit = new HttpConduit(httpClient, host, port) {
      val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[String] }
    }
    val futureUrl = conduit.pipeline(Delete(ALGORITHM_REPOSITORY + "/" + algo.id, None))
    Await.result(futureUrl, intToDurationInt(5) seconds)
  }

}