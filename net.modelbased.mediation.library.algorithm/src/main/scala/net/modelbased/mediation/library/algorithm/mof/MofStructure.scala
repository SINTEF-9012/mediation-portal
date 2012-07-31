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
package net.modelbased.mediation.library.algorithm.mof

import scala.collection.mutable

/**
 * Represent the general notion of named element, type, feature, package, etc.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
abstract class Element(initialName: String) extends Visitable {

   protected[this] var _name: String = initialName

   /**
    * @return the name of the element
    */
   def name: String =
      _name

   /**
    * Update the name of the element
    *
    * @param newName the new name to set on this element
    */
   def name_=(newName: String) =
      _name = newName

   /**
    * @return the qualified name of the element
    */
   def qualifiedName: String =
      _name

   /**
    * @inheritdoc
    *
    * @todo to remove
    */
   def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      null.asInstanceOf[O]

}

/**
 * Represent a generalisation of the element that can be put into a package, i.e
 * types.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Packageable(initialContainer: Package = null, initialName: String) extends Element(initialName) {

   private[this] var _container: Option[Package] = None

   container = if (initialContainer == null) { None } else { Some(initialContainer) }

   /**
    * @return the package that contains this packageable element, or None this is
    * the root element/package
    */
   def container: Option[Package] =
      _container

   /**
    * Update the container of this packageable. This has also the side effect of
    * of the changing the elements of both the old container and the elements of
    * the new container given as input
    *
    * @param newContainer the new container for this packageable element
    */
   def container_=(newContainer: Option[Package]): Unit = {
      if (newContainer.map { p => p.elements.exists { e => !(e eq this) && e.name == name } }.getOrElse(false)) throw new DuplicatePackageElement(_container.getOrElse(null), name)
      val oldContainer = _container
      _container = newContainer
      oldContainer.map { e => e.deleteElement(name) }
      newContainer.map { e => if (e.elementNamed(name).isEmpty) e.addElement(this) }
   }

   /**
    * Check whether this packageable is contained within the given package
    *
    * @param thePackage the package whose ownership is to be asserted
    */
   def isContainedBy(thePackage: Package): Boolean =
      _container.map { c => c == thePackage }.getOrElse(false)

   /**
    * Check whether this packageable contains another given packageable. This
    * generalises the behaviour of packages but is supposed to return false on
    * most of "atomic" packageables.
    *
    * @param p the packageable whose containment is to be asserted
    *
    * @return true is this packageable does contain the given packageable
    */
   def contains(p: Packageable): Boolean =
      false

   /**
    * @inheritdoc
    */
   override def qualifiedName: String =
      _container.map { c => c.qualifiedName + "/" }.getOrElse("") ++ name
}

/**
 * Represent the notion of package, which can contain type declarations
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Package(initialName: String, initialContainer: Package = null) extends {

   // FIXME This should be declared as private[this] but for some reason, it 
   // raises an error during compilation.
   private val _contents = mutable.Map[String, Packageable]()

} with Packageable(initialContainer, initialName) {

   /**
    * Check whether a given package contains an element at any depth level.
    *
    * @param element the element whose containment is to be asserted
    *
    * @return true if the given element is contain at any level in the hierarchy
    * of this package
    */
   override def contains(element: Packageable): Boolean =
      _contents.values.exists { e =>
         (e eq element) || e.contains(element)
      }

   /**
    * @return the elements contained in this package
    */
   def elements: Seq[Packageable] =
      _contents.values.toSeq

   /**
    * @param name the name of the needed element
    *
    * @return the element matches the given name, or None if there is no such
    * an element
    */
   def elementNamed(name: String): Option[Packageable] =
      _contents.get(name)

   /**
    * Add a new element in this package. This has the side effect of updating (if
    * necessary) the container of this new element
    *
    * @param newElement the newElement to add in this package
    */
   def addElement(newElement: Packageable): Unit = {
      if (_contents.values.exists { e => !(e eq newElement) && e.name == newElement.name }) throw new DuplicatePackageElement(this, newElement.name)
      require(!newElement.contains(this), "Illegal circular containement relationship")
      _contents += (newElement.name -> newElement)
      if (!newElement.isContainedBy(this)) {
         newElement.container = Some(this)
      }
   }

   /**
    * Delete on element from this package. This has the side-effect of updating
    * the package's container property (set to none). If there is no element
    * whose name matches the given input, nothing is done.
    *
    * @param elementName the name of the element that needs to be deleted
    */
   def deleteElement(elementName: String): Unit = {
      elementNamed(elementName) match {
         case None =>
         case Some(f) =>
            _contents -= elementName
            f.container = None
      }
   }

   /**
    * Check whether this package is the root package, i.e., whether there exists
    * no package that contains it
    *
    * @return true if this has no container
    */
   def isRoot: Boolean =
      this.container.isEmpty

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitPackage(this, input)
}

/**
 * Represent a type, i.e., primitives types such as Boolean, Integer but also
 * complex composite types such as classes or enumerations.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
abstract class Type(initialName: String, initialContainer: Package) extends Packageable(initialContainer, initialName)

abstract class PrimitiveType(initialName: String, initialContainer: Package) extends Type(initialName, initialContainer)

object Character extends PrimitiveType("Character", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitCharacter(this, input)

}

object String extends PrimitiveType("String", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitString(this, input)

}

object Boolean extends PrimitiveType("Boolean", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitBoolean(this, input)

}

object Integer extends PrimitiveType("Integer", null) {
   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitInteger(this, input)
}

object Real extends PrimitiveType("Real", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitReal(this, input)

}

object Byte extends PrimitiveType("Byte", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitByte(this, input)

}


object Any extends PrimitiveType("Any", null) {

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitAny(this, input)

}


/**
 * Represent enumeration of literal values, such as WeekDay= MonDay, Tuesday, etc.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Enumeration(initialName: String, initialContainer: Package = null) extends Type(initialName, initialContainer) {
   private[this] val _literals = mutable.Map[String, Literal]()

   /**
    * @return the list of literal defined for this enumeration
    */
   def literals: Seq[Literal] =
      _literals.values.toSeq

   /**
    * @param name the name of the needed literal.
    *
    * @return the literal whose name matches the given value, or no such a literal exists
    */
   def literalNamed(name: String): Option[Literal] =
      _literals.get(name)

   /**
    * Add a new literal in this enumeration. This has the side effect of modifying
    * the container of the given literal
    *
    * @param newLiteral the new literal to add
    */
   def addLiteral(newLiteral: Literal): Unit = {
      if (_literals.values.exists { l => !(l eq newLiteral) && l.name == newLiteral.name }) throw new DuplicateEnumerationLiteral(this, newLiteral.name)
      _literals += (newLiteral.name -> newLiteral)
      if (!newLiteral.belongsTo(this)) {
         newLiteral.container = Some(this)
      }
   }

   /**
    * Delete one of the literal of this enumeration. It also has the side effect
    * of modifying the container of the literal
    *
    * @param literalName the name of the literal to delete
    */
   def deleteLiteral(literalName: String): Unit = {
      literalNamed(literalName) match {
         case None =>
         case Some(f) =>
            _literals -= literalName
            f.container = None
      }
   }

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitEnumeration(this, input)

}

/**
 * Represent enumeration literal, that are ordered and whose label is unique for
 * a given enumeration
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Literal(initialContainer: Enumeration = null, initialName: String) extends Element(initialName) {

   private[this] var _container: Option[Enumeration] = None

   container = if (initialContainer == null) None else Some(initialContainer)

   /**
    * Update the name of the literal
    *
    * @param newName the new name for this literal
    */
   override def name_=(newName: String) = {
      if (!container.map { e => e.literalNamed(newName).isEmpty }.getOrElse(true)) throw new DuplicateEnumerationLiteral(container.getOrElse(null), newName)
      _name = newName
   }

   /**
    * @return the container of this literal, or None if this is a pending enumeration
    */
   def container: Option[Enumeration] =
      _container

   /**
    * Update the container of the literal
    *
    * @param newContainer the new enumeration that now contain this literal
    */
   def container_=(newContainer: Option[Enumeration]): Unit = {
      if (newContainer.map { e => e.literals.exists { l => !(l eq this) && l.name == name } }.getOrElse(false)) throw new DuplicateEnumerationLiteral(newContainer.getOrElse(null), name)
      val oldContainer = _container
      _container = newContainer
      oldContainer.map { e => e.deleteLiteral(name) }
      newContainer.map { e => if (!e.literals.contains(this)) e.addLiteral(this) }
   }

   /**
    * Check whether this literal belongs to a given enumeration
    * @param enumeration the enumeration whose ownership need to be asserted
    * @return true if this literal belongs to the given enumeration
    */
   def belongsTo(enumeration: Enumeration): Boolean =
      _container.map { e => e == enumeration }.getOrElse(false)

   /**
    * @inheritdoc
    */
   override def qualifiedName: String =
      _container.map { x => x.qualifiedName + "/" }.getOrElse("") + name

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitLiteral(this, input)
}

/**
 * Represent the notion of complex data type, or class by analogy with object-
 * oriented languages.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Class(initialName: String, var isAbstract: Boolean = false, initialContainer: Package = null) extends Type(initialName, initialContainer) {

   private[this] val _features = mutable.Map[String, Feature]()

   private[this] val _superClasses = mutable.Set[Class]()

   private[this] val _subClasses = mutable.Set[Class]()

   /**
    * @return the list of feature defined in this class
    */
   def features: List[Feature] =
      _features.values.toList

   /**
    * @param name the name of the feature that is needed
    * @return the feature whose name matches the given input or None otherwise
    */
   def featureNamed(name: String): Option[Feature] =
      features.find { f => f.name == name }

   /**
    * Add a new feature in the class. This has the side effect of updating the
    * container of the given feature if needed.
    *
    * @param newFeature the new feature to add to this class
    */
   def addFeature(newFeature: Feature) = {
      if (_features.values.exists { l => !(l eq newFeature) && l.name == newFeature.name }) throw new DuplicateClassFeature(this, newFeature.name)
      _features += (newFeature.name -> newFeature)
      if (!newFeature.container.map { c => c == this }.getOrElse(false)) {
         newFeature.container = Some(this)
      }
   }

   /**
    * Delete the feature whose name matches the given value. It has the side effect
    * of nullifying the container of the given feature
    *
    * @param featureName the name of the feature to remove
    */
   def deleteFeature(featureName: String) = {
      featureNamed(featureName) match {
         case None =>
         case Some(f) =>
            _features -= featureName
            f.container = None
      }
   }

   /**
    * @param aClass the class whose parent relationship is to be asserted
    *
    * @return true if this class is a super class (not necessarily a direct super class)
    * of the class given as input
    */
   def isASuperClassOf(aClass: Class): Boolean =
      _subClasses.exists { c => c == aClass || c.isASuperClassOf(aClass) }

   /**
    * @return all super classes
    */
   def superClasses: Seq[Class] =
      _superClasses.toSeq

   /**
    * Add a new super class to this class. This has the side effect of creating a
    * new the subclass on the given class
    *
    * @param superClass the super class to add
    *
    * @throws CircularClassInheritance when the given super class is already
    * a sub class of this class
    */
   def addSuperClass(superClass: Class): Unit = {
      if (isASuperClassOf(superClass)) throw new CircularClassInheritance(this)
      require { !_superClasses.contains(superClass) }
      _superClasses += superClass
      if (!superClass.isASuperClassOf(this)) {
         superClass.addSubClass(this)
      }
   }

   /**
    * Delete the given direct super class. This has the side effect to delete also
    * this class from the subclasses of the given super class.
    *
    * @param superClass the super class which is to be removed
    */
   def deleteSuperClass(superClass: Class): Unit = {
      _superClasses -= superClass
      if (superClass.subClasses.contains(this)) {
         superClass.deleteSubClass(this)
      }
   }

   /**
    * @param superClass the class whose parental relationship is to be asserted
    *
    * @return true if there this class is indeed a sub class of the given superClass, at
    * any level in the hierarchy
    */
   def isASubClassOf(superClass: Class): Boolean =
      _superClasses.exists { c => c == superClass || c.isASubClassOf(superClass) }

   /**
    * @return all sub classes of this class
    */
   def subClasses: Seq[Class] = {
      _subClasses.toSeq
   }

   /**
    * Add a new sub class to this class
    */
   def addSubClass(subClass: Class): Unit = {
      require { !_subClasses.contains(subClass) }
      _subClasses += subClass
      if (!subClass.isASubClassOf(this)) {
         subClass.addSuperClass(this)
      }
   }

   /**
    * Delete a sub class from the sub classes of this class. This also has the side
    * effect of deleting this class from the super class of the given sub class.
    *
    * @param subClass the subClass that sis to be removed
    */
   def deleteSubClass(subClass: Class): Unit = {
      _subClasses -= subClass
      if (subClass.superClasses.contains(this)) {
         subClass.deleteSuperClass(this)
      }
   }

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitClass(this, input)

}

/**
 * Represent feature of classes. Each feature is supposed to be identified by
 * a name unique in the context of a class
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class Feature(
      var theClass: Option[Class] = None,
      initialName: String,
      var `type`: Type,
      min: Int = 0,
      max: Option[Int] = Some(1),
      var isOrdered: Boolean = false,
      var isUnique: Boolean = false) extends Element(initialName) {

   private[this] var _lower: Int = min

   private[this] var _upper: Option[Int] = max

   private[this] var _container: Option[Class] = None

   container = theClass

   /**
    * Update the name of the feature
    *
    * @param newName the new name to set for this feature
    *
    * @throws IllegalArgumentException when the given new name is already used by another
    * feature in the context of the containing class
    */
   override def name_=(newName: String) = {
      if (_container.map { c => c.features.exists { f => f.name == newName } }.getOrElse(false)) throw new DuplicateClassFeature(_container.getOrElse(null), newName)
      _name = newName
   }

   /**
    * @return the class containing the feature
    */
   def container: Option[Class] =
      _container

   /**
    * Update the container of this feature. This method has side effects and also
    * update the list of features of both the new and old containers
    *
    * @param newContainer the new container of this feature
    */
   def container_=(newContainer: Option[Class]): Unit = {
      require(newContainer != null, "'null' is not acceptable as a container")
      if (newContainer.map { c => c.features.exists { f => !(f eq this) && f.name == name } }.getOrElse(false)) throw new DuplicateClassFeature(_container.getOrElse(null), name)
      val oldContainer = _container
      _container = newContainer
      oldContainer.map { c => c.deleteFeature(name) }
      newContainer.map { c => if (!c.features.contains(this)) c.addFeature(this) }
   }

   /**
    * @return the lower bound multiplicity of this feature
    */
   def lower: Int =
      _lower

   /**
    * Update the lower bound multiplicity of this feature
    * @param lb the ne lower bound multiplicity
    */
   def lower_=(lb: Int) = {
      require(_upper.map { ub => ub >= lb }.getOrElse(true), "The lower bound cannot greater than the upper bound")
      require(lb >= 0, "The lower bound cannot be lesser than 0")
      _lower = lb
   }

   /**
    * @return the upper boun multiplicity of this feature
    */
   def upper: Option[Int] =
      _upper

   /**
    * Update the upper bound multiplicity of this feature
    * @param ub the new upper bound to set
    */
   def upper_=(ub: Option[Int]) = {
      require(ub.map { ubv => ubv >= _lower }.getOrElse(true), "the upper bound cannot be smaller than the lower bound")
      _upper = ub
   }

   override def qualifiedName: String =
      _container.map { x => x.qualifiedName + "/" }.getOrElse("") + name

   /**
    * @inheritdoc
    */
   override def accept[I, O](visitor: MofVisitor[I, O], input: I): O =
      visitor.visitFeature(this, input)

}