/**
 * This file is part of SensApp [ http://sensapp.modelbased.net ]
 *
 * Copyright (C) 2010-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: net.modelbased.mediation.library.algorithm
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
package net.modelbased.mediation.library.algorithm.mof.reader

import net.modelbased.mediation.library.algorithm.mof._

/**
 * Walk through the AST and populate the "modelElement" fields by instantiating
 * node with respect to their type and the data they contain
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MofBuilder extends AstVisitor[List[MofError], List[MofError]] {

   /**
    * @inheritdoc
    */
   def visitNode(node: Node, input: List[MofError]): List[MofError] = {
      node.children.foldLeft(input) { (acc, child) =>
         child.accept(this, acc)
      }
   }

   /**
    * @inheritdoc
    */
   def visitPackageNode(node: PackageNode, input: List[MofError]): List[MofError] = {
      val result = new Package(node.name, null)
      val errors = node.elements.foldLeft(input) { (acc, element) =>
         val errors = element.accept(this, acc)
         val modelElement: Packageable = element match {
            case c: ClassNode       => c.modelElement.getOrElse(null)
            case p: PackageNode     => p.modelElement.getOrElse(null)
            case e: EnumerationNode => e.modelElement.getOrElse(null)
            case _                  => null
         }
         if (modelElement == null) {
            new InternalError(node, "Element should have been built before their containing package") :: errors
         } else {
        	 try {
        	    result.addElement(modelElement)
        	    errors
        	 } catch {
        	    case dpe: DuplicatePackageElement => 
        	       new DuplicateElement(node, modelElement.name) :: errors
        	 }
         }
      }
      node.modelElement = Some(result)
      errors
   }

   /**
    * @inheritdoc
    */
   def visitEnumerationNode(node: EnumerationNode, input: List[MofError]): List[MofError] = {
      val result = new Enumeration(node.name, null)
      val errors = node.literals.foldLeft(input) { (acc, literal) =>
         val errors = literal.accept(this, acc)
         literal.modelElement match {
            case None => new InternalError(node, "The literal '%s' should have built before its containing enumeration!") :: errors
            case Some(me) =>
               try {
                  result.addLiteral(me)
                  errors
               }
               catch {
                  case e: DuplicateEnumerationLiteral =>
                     new DuplicateLiteral(node, literal.name) :: errors
               }
         }
      }
      node.modelElement = Some(result)
      errors
   }

   /**
    * @inheritdoc
    */
   def visitLiteralNode(node: LiteralNode, input: List[MofError]): List[MofError] = {
      node.modelElement = Some(new Literal(null, node.name))
      input ++ Nil
   }

   /**
    * @inheritdoc
    */
   def visitClassNode(node: ClassNode, input: List[MofError]): List[MofError] = {
      val result = new Class(node.name, node.isAbstract, null)
      // We don't take care of super classes, as it is the duty of the linker
      val errors = node.features.foldLeft(input) { (acc, feature) =>
         val errors = feature.accept(this, acc)
         feature.modelElement match {
            case None => new InternalError(node, "The feature should have been built before its containing class!") :: acc
            case Some(f) =>
               try {
                  result.addFeature(f)
                  acc
               }
               catch {
                  case dcf: DuplicateClassFeature =>
                     new DuplicateFeature(node, feature.name) :: acc
               }
         }
      }
      node.modelElement = Some(result)
      errors
   }

   /**
    * @inheritdoc
    */
   def visitFeatureNode(node: FeatureNode, input: List[MofError]): List[MofError] = {
      val result = new Feature(None, node.name, null, node.lower, node.upper, node.isOrdered, node.isUnique)
      node.modelElement = Some(result)
      input ++ Nil
   }

}