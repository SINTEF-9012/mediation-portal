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
package net.modelbased.mediation.client.repository.comparison

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

import net.modelbased.mediation.service.repository.comparison._
import net.modelbased.mediation.service.repository.comparison.data._
import net.modelbased.mediation.service.repository.comparison.data.JsonEvaluationProtocol._

/**
 * Client API for the model repository
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
trait ComparisonRepository extends Portal {

   val COMPARISON_REPOSITORY = "/sensapp/mediation/repositories/comparisons"

   /**
    * Retrieve the list of comparisons from the repository
    *
    * @return the list of existing comparisons
    */
   def fetchAllOracleUrls(): List[String] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Fetch the comparison stored at the given URL
    *
    * @param url the URL where the comparison is stored
    *
    * @return the list of existing comparisons
    */
   def fetchComparisonAt(url: String): Evaluation = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Evaluation] }
      }
      val futureUrl = conduit.pipeline(Get(url))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Retrieve all the comparison stored in the repository
    */
   def fetchAllComparisons(): List[Evaluation] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Evaluation]] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY + "?flatten=true"))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Fetch the urls of all comparison with the given oracle ID.
    *
    * @param oracleId the ID of the oracle mapping
    *
    * @return a list of string containing all the URL of the different comparison
    */
   def fetchComparisonsWithOracle(oracleId: String): List[Evaluation] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[Evaluation]] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY + "/" + oracleId))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }
   
   
   /**
    * Fetch a specific comparison in the repository
    *
    * @param oracleId the ID of the oracle mapping
    * 
    * @param mappingID the ID of the mapping compared to the oracle
    * 
    * @return the related evaluation object
    */
   def fetchComparisonById(oracleId: String, mappingId: String): Evaluation = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Evaluation] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY + "/" + oracleId + "/" + mappingId))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }
   
    /**
    * Fetch the statistics of a given comparison in the repository
    *
    * @param oracleId the ID of the oracle mapping
    * 
    * @param mappingID the ID of the mapping compared to the oracle
    * 
    * @return the related statistics object
    */
   def fetchStatisticsById(oracleId: String, mappingId: String): Statistics = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[Statistics] }
      }
      val futureUrl = conduit.pipeline(Get(COMPARISON_REPOSITORY + "/" + oracleId + "/" + mappingId + "/stats"))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Publish a given evaluation on the comparison repository
    *
    * @param evaluation the evaluation that needs to be published
    *
    * @return the REST response as a string
    */
   def storeComparisons(evaluations: List[Evaluation]): List[String] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest[List[Evaluation]] ~> sendReceive ~> unmarshal[List[String]]
      }
      var r = conduit.pipeline(Post(COMPARISON_REPOSITORY, evaluations))
      Await.result(r, 5 seconds)
   }

   /**
    * Delete a given comparison from the repository
    *
    * @param comparison the comparison to delete
    */
   def deleteComparison(comparison: Evaluation) = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal }
      }
      val futureUrl = conduit.pipeline(Delete(COMPARISON_REPOSITORY + "/" + comparison.oracle + "/" + comparison.mapping))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   /**
    * Delete all the comparisons with a given oracle ID.
    * 
    * @param oracleId the ID of the oracle whose comparison with have to be deleted
    */
   def deleteComparisonsWithOracle(oracleId: String) = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal }
      }
      val futureUrl = conduit.pipeline(Delete(COMPARISON_REPOSITORY + "/" + oracleId))
      Await.result(futureUrl, intToDurationInt(5) seconds)

   }

}