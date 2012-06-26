package net.modelbased.mediation.library.mapping

import cc.spray.json._

object MappingJsonProtocol extends DefaultJsonProtocol {
  implicit val resultFormat = jsonFormat(Result, "degree", "origin")
  implicit val associationFormat = jsonFormat(Association, "source", "target", "result")
  implicit val mappingFormat = jsonFormat(Mapping, "uid", "status", "content")
}
  
  
