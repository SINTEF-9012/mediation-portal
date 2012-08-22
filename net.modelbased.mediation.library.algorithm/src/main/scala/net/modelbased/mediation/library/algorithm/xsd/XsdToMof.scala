/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.library.algorithm
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
package net.modelbased.mediation.library.algorithm.xsd

import scala.xml
import scala.xml._

import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.library.algorithm._

import net.modelbased.mediation.library.algorithm.mof.reader

/**
 * Convert a model whose content is an XSD file (i.e., an XML schema) into
 * an equivalent model whose content is the MOF internal representation. This
 * transformation includes the pre-processing (cleansing) of the given input
 * XSD content.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class XsdToMof extends ModelProcessor {

   val XSD_URI = "http://www.w3.org/2001/XMLSchema"

   val clean = new XsdCleaner()

   var targetNamespace: String = _
   var prefix: String = _

   override def apply(input: Model): Model = {
      val cleanXsd = clean(input)

      val xsd = Utility.trim(XML.loadString(cleanXsd.content))
      //println(xsd.toString)

      targetNamespace = xsd.attribute("targetNamespace").map { x => x.text }.getOrElse(null)
      xsd match {
         case Elem(_, "schema", _, scope, _*) =>
            prefix = scope.getPrefix(targetNamespace)
         //            println("TNS: " + targetNamespace)
         //            println("PREFIX: " + prefix)
      }

      val typeDefinition = createTypes(xsd \ "complexType" ++ xsd \ "simpleType" )

      val schemaClass = createSchemaClass(xsd)

      val packageName = if (prefix == null) input.name else prefix
      val content = "package %s { %s %s }".format(packageName, typeDefinition, schemaClass)

      return new Model(input.name + " (as MOF)", input.description, "text/mof", content)

   }

   private[this] def createSchemaClass(xsd: Node): String = {
      val definitions = (xsd \ "element").foldLeft("") { (acc, e) => acc + toFeature(e) }
      "class Schema { %s }".format(definitions)
   }

   private[this] def createTypes(types: NodeSeq): String = {
      types.foldLeft("") {
         (acc, t) => "%s %s".format(acc, toType(t))
      }
   }

   private[this] def toType(node: Node): String = {
      node match {
         case Elem(_, "simpleType", _, _, stc) =>
           val name = node.attribute("name").map { x => x.text }.getOrElse("Anonymous")
            stc match {
               case Elem(_, "restriction", _, _, rc @ Elem(_, "enumeration", _, _) *) =>
                  toEnumeration(name, rc)

               case _ =>
                  "crap!"
            }

         case Elem(_, "complexType", _, _, ctc) =>
            val name = node.attribute("name").map { x => x.text }.getOrElse("Anonymous")
            ctc match {
               case Elem(_, "complexContent", _, _, ccc) =>
                  ccc match {
                     case e @ Elem(_, "extension", _, _, ec) =>
                        val superClass = e.attribute("base").map { x => x.text }.getOrElse("Any")
                        ec match {
                           case Elem(_, "sequence", _, _, c @ _*) =>
                              val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                              "class %s extends %s { %s }".format(asMofIdentifier(name), processName(node, superClass), features)

                           case Elem(_, "all", _, _, c @ _*) =>
                              val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                              "class %s extends %s { %s }".format(asMofIdentifier(name), processName(node, superClass), features)

                           case Elem(_, "choice", _, _, c @ _*) =>
                              val features = c.foldLeft("") { (acc, f) => acc + " " + toSubClass(f, name) }
                              "class %s extends %s %s".format(asMofIdentifier(name), processName(node, superClass), features)

                           case _ =>
                              "crap!"
                        }

                     case Elem(_, "sequence", _, _, c @ _*) =>
                        val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                        "class %s { %s }".format(asMofIdentifier(name), features)

                     case Elem(_, "all", _, _, c @ _*) =>
                        val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                        "class %s { %s }".format(asMofIdentifier(name), features)

                     case Elem(_, "choice", _, _, c @ _*) =>
                        val features = c.foldLeft("") { (acc, f) => acc + " " + toSubClass(f, name) }
                        "class %s %s".format(asMofIdentifier(name), features)

                     case _ =>
                        "crap!"
                  }
               case Elem(_, "sequence", _, _, c @ _*) =>
                  val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                  "class %s { %s }".format(asMofIdentifier(name), features)

               case Elem(_, "all", _, _, c @ _*) =>
                  val features = c.foldLeft("") { (acc, f) => acc + " " + toFeature(f) }
                  "class %s { %s }".format(asMofIdentifier(name), features)

               case Elem(_, "choice", _, _, c @ _*) =>
                  val features = c.foldLeft("") { (acc, f) => acc + " " + toSubClass(f, name) }
                  "class %s %s".format(asMofIdentifier(name), features)

               case _ =>
                  "crap!"
            }

         case _ =>
            "crap!"
      }
   }

   private[this] def toEnumeration(name: String, literals: NodeSeq): String = {
      val literalsText = literals.map { x =>
         x match {
            case e @ Elem(_, "enumeration", _, _) =>
               e.attribute("value").map { x => x.text }.getOrElse("Anonymous")
            case _ =>
               "crap!"
         }
      }.mkString(",")
      "enumeration %s { %s }".format(name, literalsText)
   }

   private[this] def toSubClass(node: Node, superClassName: String): String = {
      node match {
         case Elem(_, "element", _, _) =>
            val name = node.attribute("name").map { x => x.text }.getOrElse("anonymous")
            val typeName = node.attribute("type").map { x => x.text }.getOrElse { "Any" }
            "class %s extends %s { %s: %s }".format(asMofIdentifier(name + "SubType"), processName(node, superClassName), name, processName(node, typeName))
         case _ =>
            "crap!"
      }
   }

   private[this] def toFeature(node: Node): String =
      node match {
         case Elem(_, "element", _, _) =>
            val featureName = node.attribute("name").map { x => x.text }.getOrElse("anonymous")
            val featureType = node.attribute("type").map { x => x.text }.getOrElse("Any")
            " %s: %s".format(asMofIdentifier(featureName), processName(node, featureType))
         case _ =>
            " crap"
      }

   private[this] def convertType(xsdTypeName: String): String =
      xsdTypeName match {
         case "string"  => "String"
         case "anyType" => "Any"
         case _         => xsdTypeName
      }

   private[this] def processName(context: Node, name: String): String = {
      asMofIdentifier(name).span { x => x != ':' } match {
         case (prefix, "") =>
            convertType(prefix)
         case (prefix, label) =>
            if (context.getNamespace(prefix) == XSD_URI) {
               //println("XSD type !!!!!!")
               convertType(label.tail) // tail remove the remain ':' in front
            }
            else {
               //println("regular type.")
               "%s.%s".format(prefix, convertType(label.tail))
            }
      }
      //println("prefix: " + prefix + " ; label: " + label)
   }

   private[this] def asMofIdentifier(name: String): String = name.replace("-", "_")

}