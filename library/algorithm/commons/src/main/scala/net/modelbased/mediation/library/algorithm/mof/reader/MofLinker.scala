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
package net.modelbased.mediation.library.algorithm.mof.reader

import net.modelbased.mediation.library.algorithm.mof._

/**
 * The MofLinker is in charge of walking through an AST that have been previously
 * processed by a MofBuilder. Assuming that nodes have thus been instantiating,
 * the MofLinker will resolve references, such as features' type, classes'
 * super classes, etc.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MofLinker extends AstVisitor[List[MofError], List[MofError]] {

   def visitNode(node: Node, input: List[MofError]): List[MofError] = {
      node.children.foldLeft(input) { (acc, n) => n.accept(this, acc) }
   }

   def visitPackageNode(node: PackageNode, input: List[MofError]): List[MofError] = {
      node.elements.foldLeft(input) { (acc, n) => n.accept(this, acc) }
   }

   def visitClassNode(node: ClassNode, input: List[MofError]): List[MofError] = {
      // Propagate link on features
      val errors = node.features.foldLeft(input) { (acc, n) => n.accept(this, acc) }
      // Link the super classes
      node.superClasses.foldLeft(errors) {
         (acc, scr) =>
            node.resolve(scr) match {
               case None => new UnknownSuperClass(node, scr.toString) :: acc
               case Some(scn: ClassNode) =>
                  node.modelElement match {
                     case None => new InternalError(node, "The node should have been processed by a builder") :: acc
                     case Some(c: Class) =>
                        scn.modelElement match {
                           case None => new InternalError(node, "The node should have been processed by a builder") :: acc
                           case Some(sc: Class) =>
                              try {
                                 c.addSuperClass(sc)
                                 acc
                              }
                              catch {
                                 case e: CircularClassInheritance =>
                                    new CircularInheritance(node, scr.toString) :: acc
                              }
                        }

                  }
               case _ => new IllegalInheritance(node, scr.toString) :: acc
            }
      }
   }

   def visitFeatureNode(node: FeatureNode, input: List[MofError]): List[MofError] = {
      val errors1 = resolveFeatureType(node, input)
      resolveFeatureOpposite(node, errors1)
   }

   private[this] def resolveFeatureType(node: FeatureNode, input: List[MofError]): List[MofError] = {
      node.modelElement match {
         case None => new InternalError(node, "The node should have been processed by a builder") :: input
         case Some(feature: Feature) =>
            node.resolve(node.`type`) match {
               case None => new UnknownFeatureType(node, node.`type`.toString()) :: input
               case Some(e: ClassNode) =>
                  e.modelElement match {
                     case None => new InternalError(node, "The node should have been processed by a builder") :: input
                     case Some(c: Class) =>
                        feature.`type` = c
                        input
                  }
               case Some(ptn: PrimitiveTypeNode) =>
                  ptn.modelElement match {
                     case None                    => new InternalError(node, "PrimitiveType have not been properly initialized in the global scope") :: input
                     case Some(pt: PrimitiveType) => feature.`type` = pt; input
                  }
               case Some(en: EnumerationNode) =>
                  en.modelElement match {
                     case None => new InternalError(node, "EnumerationType '%s' have not been properly initialized in the global scope".format(en.name)) :: input
                     case Some(e: Enumeration) => feature.`type` = e; input
                        
                  }
                case Some(dtn: DataTypeNode) =>
                  dtn.modelElement match {
                     case None => new InternalError(node, "DataType '%s' have not been properly initialized in the global scope".format(dtn.name)) :: input
                     case Some(dt: DataType) => feature.`type` = dt; input
                        
                  }
               case _ => new InternalError(node, "Illegal type") :: input
            }
      }
   }

   private[this] def resolveFeatureOpposite(node: FeatureNode, input: List[MofError]): List[MofError] = {
      node.modelElement match {
         case None => new InternalError(node, "The node should have been processed by a builder") :: input
         case Some(feature: Feature) =>
            node.opposite match {
               case None => input
               case Some(ref: Reference) =>
                  node.resolve(ref) match {
                     case None => new UnknownFeatureOpposite(node, ref.toString) :: input
                     case Some(fn: FeatureNode) =>
                        fn.modelElement match {
                           case None => new InternalError(node, "The node should have been processed by a builder") :: input
                           case Some(f: Feature) =>
                              feature.opposite = Some(f)
                              input
                        }
                     case _ => new IllegalFeatureOpposite(node, ref.toString) :: input 
                  }
            }
      }
   }

   def visitDataTypeNode(node: DataTypeNode, input: List[MofError]): List[MofError] = {
      input
   }
   
   def visitEnumerationNode(node: EnumerationNode, input: List[MofError]): List[MofError] = {
      node.literals.foldLeft(input) { (acc, n) => n.accept(this, acc) }
   }

   def visitLiteralNode(node: LiteralNode, input: List[MofError]): List[MofError] = {
      input // Nothing special needs to be done on literals
   }

}