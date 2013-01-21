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
package net.modelbased.mediation.library.algorithm.mof

import org.specs2.specification.Scope
import org.specs2.mutable.SpecificationWithJUnit


trait SampleClass extends SamplePackageable {
  
  override def thePackageable =
    theClass

}


/**
 * Specification of the behaviour expected for class in a MoF-like data schema
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
class TestClass extends SpecificationWithJUnit with TestPackageable with SampleClass {
  isolated

  "A class" should {    
    
    "support change of the isAbstract property" in {
      val isAbstract = !theClass.isAbstract 
      theClass.isAbstract = isAbstract
      theClass.isAbstract must beEqualTo(isAbstract)
    }

    "support searching feature by name" in {
      theClass.featureNamed("featureA") must beLike {
        case None => ko
        case Some(f) =>
          f must beEqualTo(featureA)
      }
    }

    "support moving a feature from one class to another" in {
    	theClass.featureNamed("featureA") match {
    	  case None => ko
    	  case Some(f) =>
    	    anotherClass.addFeature(f)
    	    f.container must beSome.which{ c => c == anotherClass }
    	    theClass.featureNamed("featureA") must beNone
    	    anotherClass.featureNamed("featureA") must beSome
    	}
    }
    
    "reject two features with the same name" in {
      (new Feature(Some(theClass), "featureA", String)) must throwA[DuplicateClassFeature]
    }
    
    "support proper deletion of features" in {
      theClass.featureNamed("featureA") match {
        case None => ko
        case Some(f) =>
          theClass.deleteFeature("featureA")
          f.container must beNone
      }
    }
    
    "not have any super class by default" in  {
      theClass.superClasses must be empty
    }
    
    "not have any sub class by default" in  {
      theClass.subClasses must be empty
    }
    
    "support adding super class" in  {
      val newSuperClass = new Class("NewSuperClass")
      theClass.addSuperClass(newSuperClass)
      theClass.superClasses must contain(newSuperClass)
      newSuperClass.subClasses must contain(theClass)
    }
    
    "support addition of subclasses" in  {
    	val newSubClass = new Class("NewSubClass")
    	theClass.addSubClass(newSubClass)
    	theClass.subClasses must contain(newSubClass)
    	newSubClass.superClasses must contain(theClass)
    }
    
    "reject duplicated subclasses" in  {
      val newSubClass = new Class("NewSubClass")
      theClass.addSubClass(newSubClass)
      theClass.addSubClass(newSubClass) must throwA[IllegalArgumentException]
    } 
    
    "reject duplicated super classes" in  {
      val newSuperClass = new Class("NewSuperClass")
      theClass.addSuperClass(newSuperClass)
      theClass.addSuperClass(newSuperClass) must throwA[IllegalArgumentException]
    }
    
    "reject inheritance cycles when adding super class" in  {
      val newSuperClass = new Class("NewSuperClass")
      theClass.addSuperClass(newSuperClass)
      val topClass = new Class("TopClass")
      newSuperClass.addSuperClass(topClass)
      theClass.addSubClass(topClass) must throwA[CircularClassInheritance]
    }
    
    "reject inheritance cycles when adding sub class" in  {
      val newSubClass = new Class("newSubClass")
      theClass.addSubClass(newSubClass)
      val bottomClass = new Class("BottomClass")
      newSubClass.addSubClass(bottomClass)
      bottomClass.addSubClass(theClass) must throwA[CircularClassInheritance]
    }
    
    "support proper deletion of super classes" in {
      val newSuperClass = new Class("newSuperClass")
      theClass.addSuperClass(newSuperClass)
      theClass.superClasses must contain(newSuperClass)
      newSuperClass.subClasses must contain(theClass)
      theClass.deleteSuperClass(newSuperClass)
      theClass.superClasses must not contain(newSuperClass)
      newSuperClass.subClasses must not contain(theClass) 
    }
    
    "support proper deletion of sub classes" in {
      val newSubClass = new Class("newSubClass")
      theClass.addSubClass(newSubClass)
      theClass.subClasses must contain(newSubClass)
      newSubClass.superClasses must contain(theClass)
      theClass.deleteSubClass(newSubClass)
      theClass.subClasses must not contain(newSubClass)
      newSubClass.superClasses must not contain(theClass)
    }
  

  }
}