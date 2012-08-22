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

import org.specs2.mutable.SpecificationWithJUnit

class TestMofAst extends SpecificationWithJUnit with SampleMofAst {
   isolated

   val theRoot = new Node(None, true, None)
   val theChildA = new Node(Some(theRoot), true, None)
   val theGreatChildA = new Node(Some(theChildA), false, None)
   val theChildB = new Node(Some(theRoot), true, None)

   "A node" should {

      "have a non null children list" in {
         theRoot.children must not beNull
      }

      "have a parent, when it has been initialised" in {
         theChildA.isAChildOf(theRoot) must beTrue
         theChildA.isAChildOf(theChildB) must beFalse
      }

      "have children, when those have been initialised with a parent" in {
         theRoot.isParentOf(theChildA) must beTrue
         theRoot.isParentOf(theChildB) must beTrue
         theChildB.isParentOf(theGreatChildA) must beFalse
      }

      "be ancestor when properly initialised" in {
         theRoot.isAncestorOf(theGreatChildA) must beTrue
         theChildB.isAncestorOf(theGreatChildA) must beFalse
      }

      "be descendant when properly initialised" in {
         theGreatChildA.isADescendantOf(theRoot) must beTrue
         theGreatChildA.isADescendantOf(theChildB) must beFalse
      }

      "support adding children" in {
         val child = new Node(None, true, None)
         child.parent must beNone
         theRoot.addChild(child)
         child.parent must beSome.which { n => n == theRoot }
         theRoot.children must contain(child)
      }

      "support deleting children" in {
         val theChild = theRoot.children.head
         theRoot.deleteChild(theChild)
         theChild.parent must beNone
         theRoot.children must not contain (theChild)
      }

      "be a Root when it has no parent" in {
         theRoot.isRoot must beEqualTo(theRoot.parent.isEmpty)
      }

      "be a Leaf when it has no children" in {
         theChildA.isLeaf must beEqualTo(theChildA.children.isEmpty)
      }

      "support resolution of built-in types" in {
         element.resolve(new Reference("String")) must beSome
         element.resolve(new Reference("Character")) must beSome
         element.resolve(new Reference("Boolean")) must beSome
         element.resolve(new Reference("Integer")) must beSome
         element.resolve(new Reference("Real")) must beSome
         element.resolve(new Reference("Byte")) must beSome

      }

      "support the resolution of relevant references" in {
         element.resolve(new Reference("Element")) must beSome.which { n => n == element }
         element.resolve(new Reference("data")) must beSome.which { n => n == data }
         element.resolve(new Reference("data", "WeekDays")) must beSome.which { n => n == weekDays }
         data.resolve(new Reference("WeekDays", "Saturday")) must beSome.which { n => n == saturday }

      }

      "reject the resolution of irrelevant references" in {
         data.resolve(new Reference("this", "does", "not", "exists")) must beNone
         element.resolve(new Reference("this", "does", "not", "exists")) must beNone
         test.resolve(new Reference("this", "does", "not", "exists")) must beNone
      }

   }

   "A PackageNode" should {

      "be a scope" in {
         data.isScope must beTrue
      }

      "be a symbol" in {
         data.isSymbol must beTrue
         data.symbol must beSome.which { s => s == "data" }
      }

      "have a name" in {
         data.name must beEqualTo("data")
      }

      "be the parent of its direct elements" in {
         weekDays.parent must beSome.which { n => n == data }
      }

      "support adding and deleting elements" in {
         val newClass = new ClassNode(None, "TheNewClass") 
         data.addElement(newClass)
         data.elements must contain(newClass)
         data.deleteElement(newClass)
         data.elements must not contain(newClass)
      }

      "have newly added class node as elements" in {
         val newClass = new ClassNode(None, "TheNewClass")
         data.addChild(newClass)
         data.elements must contain(newClass)
      }

      "be the ancestor of its indirect elements" in {
         weekDays.isAncestorOf(saturday) must beTrue
         weekDays.isAncestorOf(sunday) must beTrue
      }

   }

   "An EnumerationNode" should {

      "be a scope" in {
         weekDays.isScope must beTrue
      }

      "be a symbol" in {
         weekDays.isSymbol must beTrue
         weekDays.symbol must beSome.which { s => s == "WeekDays" }
      }

      "have a name" in {
         weekDays.name must beEqualTo("WeekDays")
      }

      "have a list of literal nodes" in {
         weekDays.literals must not beEmpty
      }

      "be the parent of its literals" in {
         saturday.parent must beSome.which { n => n == weekDays }
         sunday.parent must beSome.which { n => n == weekDays }
      }
      
      
      "support adding/deleting literals" in {
         val newLiteral = new LiteralNode(None, "Manchedi")
         weekDays.addLiteral(newLiteral)
         weekDays.literals must contain(newLiteral)
         weekDays.deleteLiteral(newLiteral)
         weekDays.literals must not contain(newLiteral)
      }
      
      "be the parent of newly added literals" in {
         val newLiteral = new LiteralNode(None, "Manchedi")
         weekDays.addLiteral(newLiteral)
         weekDays.children must contain(newLiteral)
      }

   }

   "A LiteralNode" should {

      "not be a scope" in {
         saturday.isScope must beFalse
      }

      "be a symbol" in {
         sunday.isSymbol must beTrue
         sunday.symbol must beSome.which { s => s == "Sunday" }
      }

   }

   "A ClassNode" should {

      "be a scope" in {
         element.isScope must beTrue
      }

      "be a symbol" in {
         element.isSymbol must beTrue
         element.symbol must beSome.which { s => s == "Element" }
      }

      "be the parent of its features" in {
         previous.parent must beSome.which { n => n == element }
      }
      
      "support adding/deleting features" in {
         val theFeature = new FeatureNode(None, "aNewFeature", new Reference("string"), 1, Some(1), false, false, None)
         element.addFeature(theFeature)
         element.features must contain(theFeature)
         element.deleteFeature(theFeature)
         element.features must not contain(theFeature)
      }
      
      "be the parent of the newly added feature" in {
         val theFeature = new FeatureNode(None, "aNewFeature", new Reference("string"), 1, Some(1), false, false, None)
         element.addFeature(theFeature)
         element.children must contain(theFeature)
      }

      "have a isAbstract property" in {
         element.isAbstract must not beNull
      }

      "have references to its superclasses" in {
         element.superClasses must beEmpty
      }
   }

   "A FeatureNode" should {

      "be a symbol" in {
         previous.isSymbol must beTrue
         previous.symbol must beSome.which { s => s == "previous" }
      }

      "not be a scope" in {
         previous.isScope must beFalse
      }

      "have a lower bound" in {
         previous.lower must not beNull
      }

      "have an upper bound" in {
         previous.upper must not beNull
      }

      "have a non null isUnique property" in {
         previous.isUnique must not beNull
      }

      "have a non-null isOrdered property" in {
         previous.isOrdered must not beNull
      }

      "have a non null opposite" in {
         previous.opposite must beLike {
            case Some(ref) => ref must beEqualTo(Reference("Element", "next"))
            case _         => ko
         }
      }

      "have a reference to its type" in {
         previous.`type` must beLike {
            case ref: Reference => ref must beEqualTo(Reference("test", "Element"))
            case _              => ko
         }
      }

   }

}