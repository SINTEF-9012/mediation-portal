package net.modelbased.mediation.service.repository.mapping


import cc.spray.json._
import net.modelbased.sensapp.library.datastore._
import net.modelbased.mediation.library.mapping._

class MappingRegistry extends DataStore[Mapping]  {

  import MappingJsonProtocol._
  
  override val databaseName = "mediation_portal"
  override val collectionName = "repository.mappings" 
  override val key = "uid"
    
  override def getIdentifier(e: Mapping) = e.uid
  
  override def deserialize(json: String): Mapping = { json.asJson.convertTo[Mapping] }
 
  override def serialize(e: Mapping): String = { e.toJson.toString }
    
}