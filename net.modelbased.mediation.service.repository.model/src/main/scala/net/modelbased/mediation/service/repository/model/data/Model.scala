package net.modelbased.mediation.service.repository.model.data

import cc.spray.json._
import net.modelbased.sensapp.library.datastore._


/**
 * Model artifacts to be stored in the mediation portal
 */
case class Model(val name: String, var content: String)

/**
 * Spray support for JSON serialization
 */
object ModelJsonProtocol extends DefaultJsonProtocol {
  implicit val modelFormat = jsonFormat(Model, "name", "content")
}


/**
 * persistence manager for models
 */
class ModelRegistry extends DataStore[Model]  {

  import ModelJsonProtocol._
  
  override val databaseName = "mediation_portal"
  override val collectionName = "repository.models" 
  override val key = "name"
    
  override def getIdentifier(e: Model) = e.name
  
  override def deserialize(json: String): Model= { json.asJson.convertTo[Model] }
 
  override def serialize(e: Model): String = { e.toJson.toString }
    
}
