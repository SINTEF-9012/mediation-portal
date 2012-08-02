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

import scala.xml._
import scala.xml.transform._

import net.modelbased.mediation.library.algorithm.ModelProcessor
import net.modelbased.mediation.service.repository.model.data.Model

/**
 * This transformation accepts as input an schema and produces another XML
 * schemas where all the type have been extracted and are described separately.
 *
 *
 * @author Franck Chauvel - SINTEF ICT
 * @since 0.0.1
 *
 */
class XsdCleaner extends ModelProcessor {

   private[this] val typeSuffix = "Type"
   private[this] val anonymousType = "AnonymousType"
   private[this] var counter = 0

   override def apply(model: Model): Model = {
      counter = 0
      val schema = Utility.trim(XML.loadString(model.content))
      val (_, contents) = process(schema)
      new Model(model.name, contents.toString())
   }

   private[this] def process(node: NodeSeq): (NodeSeq, NodeSeq) =
      node match {
         case Elem(p, "schema", a, s, c @ _*) => {
            //println("I AM A SCHEMA")
            val (types, elements) = process(c)
            (NodeSeq.Empty, Elem(p, "schema", a, s, (types ++ elements): _*))
         }
         case e @ Elem(p, "element", a, s, <complexType>{ c @ _* }</complexType>) => {
            val typeName = e.attribute("name").map { x => x.text + typeSuffix }.getOrElse("AnonymousType")
            val (types, elements) = process(c)
            (types ++ Elem(p, "complexType", new PrefixedAttribute(p, "name", typeName, Null), s, elements: _*),
               Elem(p, "element", new PrefixedAttribute(p, "type", typeName, a), s, NodeSeq.Empty: _*))
         }
         case e @ Elem(p, "element", a, s, <simpleType>{ c @ _* }</simpleType>) => {
            val typeName = e.attribute("name").map { x => x.text + typeSuffix }.getOrElse("AnonymousType")
            val (types, elements) = process(c)
            (types ++ Elem(p, "simpleType", new PrefixedAttribute(p, "name", typeName, Null), s, elements: _*),
               Elem(p, "element", new PrefixedAttribute(p, "type", typeName, a), s, NodeSeq.Empty: _*))
         }

         case Elem(_, "element", _, _) => {
            (NodeSeq.Empty, node)
         }
         case Elem(p, "simpleType", a, s, c @ _*) => {
            //println("I AM IN A CLEAN SIMPLE TYPE")
            val (types, elements) = process(c)
            (types ++ Elem(p, "simpleType", a, s, elements: _*), NodeSeq.Empty)
         }
         case Elem(p, "restriction", a, s, c @ _*) => {
            val (types, elements) = process(c)
            (types, Elem(p, "restriction", a, s, elements: _*))
         }
         case Elem(p, "enumeration", a, s, c @ _*) => {
            val (types, elements) = process(c)
            (types, Elem(p, "enumeration", a, s, elements: _*))
         }
         case Elem(p, "complexType", a, s, c @ _*) => {
            //println("I AM IN A CLEAN COMPLEX TYPE")
            val (types, elements) = process(c)
            (types ++ Elem(p, "complexType", a, s, elements: _*), NodeSeq.Empty)
         }
         case Elem(p, "all", a, s, c @ _*) => {
            //println("I AM A ALL")
            val (types, elements) = process(c)
            (types, Elem(p, "all", a, s, elements: _*))
         }
         case Elem(p, "choice", a, s, c @ _*) => {
            //println("I AM A CHOICE")
            val (types, elements) = process(c)
            (types, Elem(p, "choice", a, s, elements: _*))
         }
         case Elem(p, "sequence", a, s, c @ _*) => {
            //println("I AM A SEQUENCE")
            val (types, elements) = process(c)
            (types, Elem(p, "sequence", a, s, elements: _*))
         }
         case Elem(p, "extension", a, s, c @ _*) => {
            val (types, elements) = process(c)
            (types, Elem(p, "extension", a, s, elements: _*))
         }
         case Elem(p, "complexContent", a, s, c @ _*) => {
            val (types, elements) = process(c)
            (types, Elem(p, "complexContent", a, s, elements: _*))
         }
         case n: Node => {
            //println("I AM SOME OTHER SORT OF NODE (%s)".format(n.toString))
            process(n.child)
         }
         case _ =>
            //println("I AM A NODESEQ (%s)".format(node.toString))
            node.foldLeft((NodeSeq.Empty, NodeSeq.Empty)) {
               case ((t, e), n) =>
                  val (t1, e2) = process(n)
                  (t ++ t1, e ++ e2)
            }
      }

}