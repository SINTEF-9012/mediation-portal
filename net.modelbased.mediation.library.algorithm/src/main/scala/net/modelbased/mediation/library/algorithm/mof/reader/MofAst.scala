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
package net.modelbased.mediation.library.algorithm.mof.reader

import scala.collection.mutable

import net.modelbased.mediation.library.algorithm.mof._

/**
 * A simple companion object for the AST that provides facilities to manipulate
 * AST, especially regarding the definition of global scopes
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
object MofAst {

   def createGlobalScope: Node = {
      val global = new Node(None, true, None)
      val BooleanType = new PrimitiveTypeNode(Some(global), "Boolean", Some(Boolean))
      val IntegerType = new PrimitiveTypeNode(Some(global), "Integer", Some(Integer))
      val RealType = new PrimitiveTypeNode(Some(global), "Real", Some(Real))
      val StringType = new PrimitiveTypeNode(Some(global), "String", Some(String))
      val CharacterType = new PrimitiveTypeNode(Some(global), "Character", Some(Character))
      val ByteType = new PrimitiveTypeNode(Some(global), "Byte", Some(Byte))
      val AnyType = new PrimitiveTypeNode(Some(global), "Any", Some(Any))
      global
   }

}

/**
 * Represent a reference to a symbol. For instance, a reference to the class
 * "Foo" in the package Bar would be "new Reference("Bar", "Foo")"
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
case class Reference(target: String*) {

   def head: String =
      target.head

   def tail: Reference =
      new Reference(target.tail: _*)

   override def toString: String =
      target.mkString("/")
}

/**
 * Define the interface of depth-first walker for the AST
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
abstract trait AstVisitor[I, O] {

   def visitNode(node: Node, input: I): O

   def visitPackageNode(node: PackageNode, input: I): O

   def visitEnumerationNode(node: EnumerationNode, input: I): O

   def visitLiteralNode(node: LiteralNode, input: I): O

   def visitClassNode(node: ClassNode, input: I): O

   def visitFeatureNode(node: FeatureNode, input: I): O

}

/**
 * Represent the AST (Abstract Syntax Tree) of the mini MoF language. It is
 * at the same time a tree structure and a symbol table that permits lookup of
 * symbol defined somewhere else in the tree.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Node(initialParent: Option[Node] = None, val isScope: Boolean, val symbol: Option[String]) extends {

   private[this] var _parent: Option[Node] = None
   private[this] val _children = mutable.ListBuffer[Node]()

   parent = initialParent

   /**
    * @return the list of child attached to this node
    */
   def children: Seq[Node] =
      _children.toSeq

   /**
    * @return the parent node if defined, or None otherwise
    */
   def parent: Option[Node] =
      _parent

   /**
    * Update the parent of a given node. This has the side effect of modifying
    * the children of both the old and new parents. This cam be used to properly
    * move a child from one parent to another.
    *
    * @param newParent the new parent to set up for this node.
    */
   def parent_=(newParent: Option[Node]): Unit = {
      val oldParent = _parent
      _parent = newParent
      oldParent.map { p => p.deleteChild(this) }
      _parent.map { p => if (!p.children.contains(this)) p.addChild(this) }
   }

   /**
    * Add a new child node to this node. This has the side effect of modifying the
    * parent node of the child, and potentially, the children of the parent node
    * of the child, when the child is moved from one parent to another.
    *
    * @param newChild the new child node to add to this node
    */
   def addChild(newChild: Node): Unit = {
      _children += newChild
      if (!newChild.isAChildOf(this)) {
         newChild.parent = Some(this)
      }
   }

   /**
    * Detach a given child from its parent. This has the side effect of modifying
    * the parent property of the child node.
    *
    * @param child the child that must be detached from this node
    */
   def deleteChild(child: Node): Unit = {
      _children -= child
      if (child.isAChildOf(this)) {
         child.parent = None
      }
   }

   /**
    * @return true if this node has no parent defined, i.e, if it is a root node
    */
   def isRoot: Boolean =
      _parent.isEmpty

   /**
    * @return true if this node has no child i.e., if it is a leaf node
    */
   def isLeaf: Boolean =
      _children.isEmpty

   /**
    * @return true if this node is the parent of the the given node
    */
   def isParentOf(child: Node): Boolean =
      children.contains(child)

   /**
    * @return true if the given node is the parent of this node
    */
   def isAChildOf(parent: Node): Boolean =
      _parent.map { p => p == parent }.getOrElse(false)

   /**
    * @return true if this node is descendant of the given ancestor
    */
   def isADescendantOf(ancestor: Node): Boolean =
      isAChildOf(ancestor) || _parent.map { p => p.isADescendantOf(ancestor) }.getOrElse(false)

   /**
    * @return true if this node is the ancestor of the given node
    */
   def isAncestorOf(descendant: Node): Boolean =
      isParentOf(descendant) || _children.exists { c => c.isAncestorOf(descendant) }

   /**
    * @return true if this node is a symbol
    */
   def isSymbol: Boolean =
      symbol.isDefined

   /**
    * Resolve the given reference and return the associated node, matching the
    * given symbol reference, from the scope containing this node.
    *
    * @param reference the reference to resolve
    *
    * @return the node matching the given symbol reference, or None if this symbol
    * does not exist in this scope
    */
   def resolve(reference: Reference): Option[Node] = {
      reference.target.toList match {
         case Nil => {
            None
         }
         case name :: more => symbols.find { x => x.symbol.map { s => s == name }.getOrElse(false) } match {
            case None => {
               _parent.map { p => p.resolve(reference) }.getOrElse(None)
            }
            case Some(x) =>
               if (more.isEmpty) {
                  Some(x)
               }
               else {
                  x.resolve(reference.tail)
               }
         }
      }
   }

   /**
    * @return the closest scope containing this node
    */
   def scope: Option[Node] = {
      if (this.isScope) Some(this) else _parent.map { p => p.scope }.getOrElse(None)
   }

   /**
    * @return all the symbols defined within this node
    */
   def symbols: Seq[Node] =
      children.map { c => if (c.isSymbol) Seq(c) else c.symbols }.flatten

   /**
    * Switch a given visitor object to the proper processing methods, depending
    * on the type of the node.
    *
    *  @param visitor the visitor to switch
    *
    *  @param input the input to pass on the visitor
    *
    *  @return the result of applying the given visitor on this node
    */
   def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitNode(this, input)
   }
}

/**
 * Specific class of nodes that represent packages
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class PackageNode(thePackage: Option[Node] = None, val name: String, initialElements: Seq[Node] = Seq.empty) extends Node(thePackage, true, Some(name)) {
   initialElements.foreach { e => e.parent = Some(this) }

   /**
    * @return the list of elements defined for this package node
    */
   def elements: Seq[Node] =
      children.filter { c =>
         c.isInstanceOf[ClassNode] ||
            c.isInstanceOf[EnumerationNode] ||
            c.isInstanceOf[PrimitiveTypeNode] ||
            c.isInstanceOf[PackageNode]
      }

   /**
    * Add a given element in the package node
    *
    * @param newElement the new element to add in this package node
    */
   def addElement(newElement: Node) =
      this.addChild(newElement)

   /**
    * Delete a given element from the elements of this package
    *
    * @param the element to delete from this package
    */
   def deleteElement(element: Node) =
      deleteChild(element)

   var modelElement: Option[Package] = None

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitPackageNode(this, input)
   }
}

/**
 * Specific class of nodes to represent enumeration types
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class EnumerationNode(var thePackage: Option[Node] = None,
                      val name: String,
                      initialLiterals: Seq[LiteralNode] = Seq.empty) extends Node(thePackage, true, Some(name)) {
   initialLiterals.foreach { l => l.parent = Some(this) }

   var modelElement: Option[Enumeration] = None

   /**
    * @return the list of literal nodes defined for this enumerations node
    */

   def literals: Seq[LiteralNode] = {
      children.filter { c => c.isInstanceOf[LiteralNode] }.map { x => x.asInstanceOf[LiteralNode] }
   }

   /**
    * Add a new literal node to this enumeration. This new literal will also
    * appears in the children of the node
    *
    * @param newLiteral the new literalNode to add to this enumeration
    */
   def addLiteral(newLiteral: LiteralNode) = {
      addChild(newLiteral)
   }

   /**
    * Delete a given literalNode from this enumeration
    *
    * @param literal the literal node to delete from this enumeration
    */
   def deleteLiteral(literal: LiteralNode) = {
      deleteChild(literal)
   }

   override def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitEnumerationNode(this, input)
   }

}

/**
 * Specific class of node to represent enumeration literal
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class LiteralNode(var theEnumeration: Option[Node] = None, val name: String) extends Node(theEnumeration, false, Some(name)) {

   var modelElement: Option[Literal] = None

   override def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitLiteralNode(this, input)
   }

}

/**
 * Specific class of nodes that represent classes
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class ClassNode(var thePackage: Option[Node] = None,
                val name: String,
                val isAbstract: Boolean = false,
                initialFeatures: Seq[FeatureNode] = Seq.empty,
                val superClasses: Seq[Reference] = Seq.empty) extends Node(thePackage, true, Some(name)) {
   initialFeatures.foreach { f => f.parent = Some(this) }

   var modelElement: Option[Class] = None

   /**
    * @return all the featureNodes attached to this class node
    */
   def features: Seq[FeatureNode] =
      children.filter { c => c.isInstanceOf[FeatureNode] }.map { c => c.asInstanceOf[FeatureNode] }

   /**
    * Add a new featureNode to this class node
    *
    * @param newFeature the new featureNode to add to this class node
    */
   def addFeature(newFeature: FeatureNode) =
      addChild(newFeature)
      
   
   /**
    * Delete a given feature node from the feature of this class node
    * 
    * @param feature the feature node to delete
    */
   def deleteFeature(feature: FeatureNode) =
      deleteChild(feature)

   override def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitClassNode(this, input)
   }

}

/**
 * Specific class of nodes that represent features
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class FeatureNode(var theClass: Option[Node] = None,
                  val name: String,
                  val `type`: Reference,
                  val lower: Int = 1,
                  val upper: Option[Int] = Some(1),
                  val isOrdered: Boolean = true,
                  val isUnique: Boolean = false,
                  val opposite: Option[Reference] = None) extends Node(theClass, false, Some(name)) {

   var modelElement: Option[Feature] = None

   override def accept[I, O](visitor: AstVisitor[I, O], input: I): O = {
      visitor.visitFeatureNode(this, input)
   }

}

/**
 * Specific class of nodes to capture primitive types and built-in types such
 * as String, Boolean, etc.
 *
 * @author Franck Chauve - SINTEF ICT
 *
 * @since 0.0.1
 */
class PrimitiveTypeNode(var thePackage: Option[Node] = None, var name: String, val modelElement: Option[PrimitiveType]) extends Node(thePackage, false, Some(name)) 
