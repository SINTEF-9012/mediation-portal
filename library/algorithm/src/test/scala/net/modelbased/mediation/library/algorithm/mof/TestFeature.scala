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
import org.specs2.mutable._



trait SampleFeature extends SampleMof {
  
  override def theElement = 
    theFeature
  
}

/**
 * Specification of the behaviour expected for a feature in a MoF-like data schema
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestFeature extends SpecificationWithJUnit with TestElement with SampleFeature { 
	isolated
    
  "A feature" should {

    "prevent renaming that creates duplicated features in the container" in {
      theClass.featureNamed("featureA") match {
        case None => ko
        case Some(feature) =>
          (feature.name = "featureB") must throwA[DuplicateClassFeature]
      }
    }

    "not have a null type" in {
      theFeature.`type` must not beNull
    }

    "support change of its type" in {
      theFeature.`type` = Boolean
      theFeature.`type` must beEqualTo(Boolean)
    }
    
    "have not specific opposite property initially defined" in {
       theFeature.opposite must beNone
    }
    
    "support update of its opposite" in {
       val newFeature = new Feature(None, "theNewFeature", String)
       theFeature.opposite = Some(newFeature)
       theFeature.opposite must beSome.which{ x => x == newFeature}
       newFeature.opposite must beSome.which{ x => x == theFeature}
    }
    
    "have a qualified name that refer to the containing class" in {
       theFeature.qualifiedName must contain(".").when(theFeature.container.isDefined)
    }

    "support change of its isOrdered property" in  {
      val isOrdered = !theFeature.isOrdered
      theFeature.isOrdered = isOrdered
      theFeature.isOrdered must beEqualTo(isOrdered)
    }

    "support change of its isUnique property" in {
      val isUnique = !theFeature.isUnique
      theFeature.isUnique = isUnique
      theFeature.isUnique must beEqualTo(isUnique)
    }

    "support change of its lower bound multiplicity" in  {
      val lowerBound = theFeature.lower + 1
      theFeature.lower = lowerBound
      theFeature.lower must beEqualTo(lowerBound)
    }

    "support change of its upper bound multiplicity" in {
      val upperBound = theFeature.upper.map{ v => v + 1}.getOrElse(1) 
      theFeature.upper = Some(upperBound)
      theFeature.upper must beSome.which{ v => v == upperBound}
    }

    "reject a lower bound greater than its upper bound" in  {
      val lowerBound = theFeature.upper.map{ v => v + 1 }.getOrElse(1)
      (theFeature.lower = lowerBound) must throwA[IllegalArgumentException]
    }

    "reject a lower bound smaller than 0" in {
      (theFeature.lower = -1) must throwA[IllegalArgumentException]
    }

    "reject an upper bound smaller than the lower bound" in  {
      (theFeature.upper = Some(theFeature.lower - 1)) must throwA[IllegalArgumentException]
    }

    "have a non null container" in {
      theFeature.container must not beNull
    }
    
    "reject null as a container" in {
       (theFeature.container = null) must throwA[IllegalArgumentException]
    }

    "support change of its container" in {
      val newContainer = new Class("MyNewClass") 
      theFeature.container = Some(newContainer)
      theClass.features.exists { f => f.name == "myFeature" } must beFalse
      newContainer.features.exists { f => f.name == "myFeature" } must beTrue
    }
    
    "reject change of container that could lead to duplicate features' name" in {
       val newFeature = new Feature(None, "featureA", String)
       (newFeature.container = Some(theClass)) must throwA[DuplicateClassFeature]
    }
    
    "accepts None as a new container, but then remove the feature from its container" in {
      theClass.featureNamed("featureA") match {
        case None => ko
        case Some(feature) =>
          feature.container = None
          theClass.featureNamed("featureA") must beNone
      }
    }

  }

}