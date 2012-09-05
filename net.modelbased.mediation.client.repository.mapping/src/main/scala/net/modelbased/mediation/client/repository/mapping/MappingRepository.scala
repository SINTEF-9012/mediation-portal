/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.client.repository.mapping
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
package net.modelbased.mediation.client.repository.mapping

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

import net.modelbased.mediation.service.repository.mapping._
import net.modelbased.mediation.service.repository.mapping.data._
import net.modelbased.mediation.service.repository.mapping.data.Conversions._
import net.modelbased.mediation.service.repository.mapping.data.MappingJsonProtocol._

/**
 * Client API for the mapping repository
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 *
 */
trait MappingRepository extends Portal {

   val MAPPING_REPOSITORY = "/sensapp/mediation/repositories/mappings"

   /**
    * Retrieve the url of all the models stored in the repository
    *
    * @return the list of url of all models stored in the repository
    */
   def fetchAllMappingUrls(): List[String] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[String]] }
      }
      val futureUrl = conduit.pipeline(Get(MAPPING_REPOSITORY, None))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }

   
    /**
    * Retrieve all the information about the models stored in the repository
    * 
    * @return a list of ModelInfo object describing the content of the repository
    */
   def fetchAllMappingInfo(): List[MappingInfo] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = { simpleRequest ~> sendReceive ~> unmarshal[List[MappingInfo]] }
      }
      val futureUrl = conduit.pipeline(Get(MAPPING_REPOSITORY + "?flatten=true"))
      Await.result(futureUrl, intToDurationInt(5) seconds)
   }
   
   /**
    * Publish a mapping in the repository
    *
    * @param mapping the mapping that must be published in the repository
    *
    * @return a string representing the status of the publication
    */
   def storeMapping(mapping: Mapping): String = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest[MappingData] ~> sendReceive ~> unmarshal[String]
      }
      val result = conduit.pipeline(Post(MAPPING_REPOSITORY, mapping))
      Await.result(result, 5 seconds)
   }

   /**
    * Fetch a given mapping, from its ID.
    *
    * @param mappingUid the identifier of the mapping to fetch
    *
    * @return the corresponding mapping object
    */
   def fetchMappingById(mappingUid: String): Mapping = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[MappingData]
      }
      var r = conduit.pipeline(Get(MAPPING_REPOSITORY + "/" + mappingUid, None))
      Await.result(r, 5 seconds) match {
         case m: MappingData => m
      }
   }

   /**
    * Fetch a given mapping, from its URL. (mapping URL are typically produced
    * by the mediator service)
    *
    * @param url the URL where the mapping is located
    *
    * @return the corresponding mapping object
    */
   def fetchMappingAt(url: String): Mapping = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[MappingData]
      }
      var r = conduit.pipeline(Get(url, None))
      Await.result(r, 5 seconds) match {
         case m: MappingData => m
      }
   }

   /**
    * Export a given mapping into an XML document
    *
    * @param mapping the mapping that has to be exported to XML
    *
    * @return the corresponding XML string
    */
   def exportMappingToXml(mappingId: String): String = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
      }
      var r = conduit.pipeline(Get(MAPPING_REPOSITORY + "/" + mappingId + "/asXML", None))
      Await.result(r, 5 seconds) 
   }
   
   
   /**
    * Delete a a given mapping from the repository
    *
    * @param mapping the mapping that must be deleted from the repository
    *
    */
   def deleteMapping(mapping: Mapping) = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal
      }
      var r = conduit.pipeline(Delete(MAPPING_REPOSITORY + "/" + mapping.uid, None))
      Await.result(r, 5 seconds)
   }
   
   
   /**
    * Fetch a specific entry of the given mapping
    * 
    * @param uid the unique identifier of the mapping of interest
    * 
    * @param source the source element of the needed entry
    * 
    * @param target the target element of the needed entry
    */
   def fetchEntry(uid: String, source: String, target: String): Entry = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[Entry]
      }
      var r = conduit.pipeline(Get(MAPPING_REPOSITORY + "/" + uid + "/content/" + source + "/" + target, None))
      Await.result(r, 5 seconds)      
   }
   
   
   /**
    * Fetch all entries for a given source element, within a specific mapping.
    * 
    * @param uid the unique identifier of the mapping of interest
    * 
    * @param source the source element of the needed entries
    */
   def fetchEntriesBySource(uid: String, source: String): List[Entry] = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[List[Entry]]
      }
      var r = conduit.pipeline(Get(MAPPING_REPOSITORY + "/" + uid + "/content/" + source, None))
      Await.result(r, 5 seconds)      
   }
   
   
   /**
    * Approve a given entry in a given mapping. The mapping is identified by the
    * mapping UID, and the entry is identified by its source and target element
    * 
    * @param uid the unique identifier that characterises the needed mapping
    * 
    * @param source the source element of the entry
    * 
    * @param target the target element of the entry
    */
   def approve(uid: String, source: String, target: String): String = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
      }
      var r = conduit.pipeline(Put(MAPPING_REPOSITORY + "/" + uid + "/content/" + source + "/" + target + "/approve", None))
      Await.result(r, 5 seconds)
   }
   
   
   /**
    * Disapprove a given entry in a given mapping. The mapping is identified by the
    * mapping UID, and the entry is identified by its source and target element
    * 
    * @param uid the unique identifier that characterises the needed mapping
    * 
    * @param source the source element of the entry
    * 
    * @param target the target element of the entry
    */
   def disapprove(uid: String, source: String, target: String): String = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
      }
      var r = conduit.pipeline(Put(MAPPING_REPOSITORY + "/" + uid + "/content/" + source + "/" + target + "/disapprove", None))
      Await.result(r, 5 seconds)
   }

   
   /**
    * Set a given entry in a given mapping as unknown/undecided (i.e., neither 
    * approved or disapproved). The mapping is identified by the
    * mapping UID, and the entry is identified by its source and target element
    * 
    * @param uid the unique identifier that characterises the needed mapping
    * 
    * @param source the source element of the entry
    * 
    * @param target the target element of the entry
    */
   def setAsUndecided(uid: String, source: String, target: String): String = {
      val conduit = new HttpConduit(httpClient, host, port) {
         val pipeline = simpleRequest ~> sendReceive ~> unmarshal[String]
      }
      var r = conduit.pipeline(Put(MAPPING_REPOSITORY + "/" + uid + "/content/" + source + "/" + target + "/unknown", None))
      Await.result(r, 5 seconds)
   }
   

}