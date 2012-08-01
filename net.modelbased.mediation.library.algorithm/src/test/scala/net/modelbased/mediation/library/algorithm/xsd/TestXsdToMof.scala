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

import org.specs2.mutable.SpecificationWithJUnit

import net.modelbased.mediation.service.repository.model.data._
import net.modelbased.mediation.library.algorithm.mof._
import net.modelbased.mediation.library.algorithm.mof.reader.MofReader

/**
 * Specification of the expected behaviour of the XsdToMof converter
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class TestXsdToMof extends SpecificationWithJUnit {
   isolated

   val mof = new MofReader
   val convert = new XsdToMof

   "Converting XSD to MOF" should {



      "properly handle namespaces" in {
         val xsd = <schema xmlns="http://www.w3.org/2001/XMLSchema" targetNamespace="pouet" xmlns:tns="pouet" xmlns:xs="http://www.w3.org/2001/XMLSchema">
                      <element name="foo" type="xs:string"/>
                   </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
        //println(result.content)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.name must beEqualTo("tns")
               p.elements.size must beEqualTo(1)
               p.elementNamed("Schema") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
                     c.featureNamed("foo") must beSome.like {
                        case f : Feature =>
                           f.`type` must beEqualTo(String)
                     }
               }
         }
      }

      "properly handle simple elements at the root level" in {
         val xsd = <schema><element name="foo" type="string"/></schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         //println(result.content)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(1)
               p.elementNamed("Schema") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
                     c.featureNamed("foo") must beSome
               }
         }
      }

      "properly handle a sequence within a complex type" in {
         val xsd =
            <schema>
               <complexType name="Foo">
                  <sequence>
                     <element name="bar1" type="string"/>
                     <element name="bar2" type="string"/>
                  </sequence>
               </complexType>
               <element name="foo" type="Foo"/>
            </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         //println(result.content)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(2)
               p.elementNamed("Foo") must beSome.like {
                  case c: Class =>
                     c.name must beEqualTo("Foo")
                     c.features.size must beEqualTo(2)
                     c.featureNamed("bar1") must beSome
                     c.featureNamed("bar2") must beSome
               }
               p.elementNamed("Schema") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
                     c.featureNamed("foo") must beSome
               }
         }

      }

      "properly handle a sequence within a complex content" in {
         val xsd =
            <schema>
               <complexType name="Foo">
                  <complexContent>
                     <sequence>
                        <element name="bar1" type="string"/>
                        <element name="bar2" type="string"/>
                     </sequence>
                  </complexContent>
               </complexType>
               <element name="foo" type="Foo"/>
            </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         //println(result.content)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(2)
               p.elementNamed("Foo") must beSome.like {
                  case c: Class =>
                     c.name must beEqualTo("Foo")
                     c.features.size must beEqualTo(2)
                     c.featureNamed("bar1") must beSome
                     c.featureNamed("bar2") must beSome
               }
               p.elementNamed("Schema") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
                     c.featureNamed("foo") must beSome
               }
         }

      }

      "properly handle a sequence within a type extension" in {
         val xsd =
            <schema>
               <complexType name="Bar">
                  <sequence>
                     <element name="bar" type="string"/>
                  </sequence>
               </complexType>
               <complexType name="Foo">
                  <complexContent>
                     <extension base="Bar">
                        <sequence>
                           <element name="bar1" type="string"/>
                           <element name="bar2" type="string"/>
                        </sequence>
                     </extension>
                  </complexContent>
               </complexType>
               <element name="foo" type="Foo"/>
            </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         //println(result.content)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(3)
               p.elementNamed("Bar") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
               }
               p.elementNamed("Foo") must beSome.like {
                  case c: Class =>
                     val test = p.elementNamed("Bar").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                     test must beTrue
                     c.name must beEqualTo("Foo")
                     c.features.size must beEqualTo(2)
                     c.featureNamed("bar1") must beSome
                     c.featureNamed("bar2") must beSome
               }
               p.elementNamed("Schema") must beSome.like {
                  case c: Class =>
                     c.features.size must beEqualTo(1)
                     c.featureNamed("foo") must beSome
               }
         }

      }

      "properly handle choice within complex types" in {
         val xsd = <schema>
                      <complexType name="Foo">
                         <choice>
                            <element name="fooA" type="string"/>
                            <element name="fooB" type="string"/>
                         </choice>
                      </complexType>
                      <element name="foo" type="Foo"/>
                   </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(4)
               p.elementNamed("Schema") must beSome
               p.elementNamed("Foo") must beSome
               p.elementNamed("fooASubType") must beSome.like {
                  case c: Class =>
                     val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                     test must beTrue
               }
               p.elementNamed("fooBSubType") must beSome.like {
                  case c: Class =>
                     val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                     test must beTrue
               }
         }
      }

      "properly handle choice within complex content" in {
         val xsd = <schema>
                      <complexType name="Foo">
                         <complexContent>
                            <choice>
                               <element name="fooA" type="string"/>
                               <element name="fooB" type="string"/>
                            </choice>
                         </complexContent>
                      </complexType>
                      <element name="foo" type="Foo"/>
                   </schema>
         val model = new Model("test", xsd.toString)
         val result = convert(model)
         mof.readPackage(result.content) must beRight.like {
            case p: Package =>
               p.elements.size must beEqualTo(4)
               p.elementNamed("Schema") must beSome
               p.elementNamed("Foo") must beSome
               p.elementNamed("fooASubType") must beSome.like {
                  case c: Class =>
                     val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                     test must beTrue
               }
               p.elementNamed("fooBSubType") must beSome.like {
                  case c: Class =>
                     val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                     test must beTrue
               }
         }
      }

   }

   "properly handle choice within type extension" in {
      val xsd = <schema>
                   <complexType name="Bar">
                      <sequence>
                         <element name="bar" type="string"/>
                      </sequence>
                   </complexType>
                   <complexType name="Foo">
                      <complexContent>
                         <extension base="Bar">
                            <choice>
                               <element name="fooA" type="string"/>
                               <element name="fooB" type="string"/>
                            </choice>
                         </extension>
                      </complexContent>
                   </complexType>
                   <element name="foo" type="Foo"/>
                </schema>
      val model = new Model("test", xsd.toString)
      val result = convert(model)
      mof.readPackage(result.content) must beRight.like {
         case p: Package =>
            p.elements.size must beEqualTo(5)
            p.elementNamed("Schema") must beSome
            p.elementNamed("Bar") must beSome
            p.elementNamed("Foo") must beSome.like {
               case c: Class =>
                  val test = p.elementNamed("Bar").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                  test must beTrue
            }
            p.elementNamed("fooASubType") must beSome.like {
               case c: Class =>
                  val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                  test must beTrue
            }
            p.elementNamed("fooBSubType") must beSome.like {
               case c: Class =>
                  val test = p.elementNamed("Foo").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                  test must beTrue
            }
      }
   }

   "properly handle a 'all' within a complex type" in {
      val xsd =
         <schema>
            <complexType name="Foo">
               <all>
                  <element name="bar1" type="string"/>
                  <element name="bar2" type="string"/>
               </all>
            </complexType>
            <element name="foo" type="Foo"/>
         </schema>
      val model = new Model("test", xsd.toString)
      val result = convert(model)
      //println(result.content)
      mof.readPackage(result.content) must beRight.like {
         case p: Package =>
            p.elements.size must beEqualTo(2)
            p.elementNamed("Foo") must beSome.like {
               case c: Class =>
                  c.name must beEqualTo("Foo")
                  c.features.size must beEqualTo(2)
                  c.featureNamed("bar1") must beSome
                  c.featureNamed("bar2") must beSome
            }
            p.elementNamed("Schema") must beSome.like {
               case c: Class =>
                  c.features.size must beEqualTo(1)
                  c.featureNamed("foo") must beSome
            }
      }

   }

   "properly handle a 'all' within a complex content" in {
      val xsd =
         <schema>
            <complexType name="Foo">
               <complexContent>
                  <all>
                     <element name="bar1" type="string"/>
                     <element name="bar2" type="string"/>
                  </all>
               </complexContent>
            </complexType>
            <element name="foo" type="Foo"/>
         </schema>
      val model = new Model("test", xsd.toString)
      val result = convert(model)
      //println(result.content)
      mof.readPackage(result.content) must beRight.like {
         case p: Package =>
            p.elements.size must beEqualTo(2)
            p.elementNamed("Foo") must beSome.like {
               case c: Class =>
                  c.name must beEqualTo("Foo")
                  c.features.size must beEqualTo(2)
                  c.featureNamed("bar1") must beSome
                  c.featureNamed("bar2") must beSome
            }
            p.elementNamed("Schema") must beSome.like {
               case c: Class =>
                  c.features.size must beEqualTo(1)
                  c.featureNamed("foo") must beSome
            }
      }

   }

   "properly handle a 'all' within a type extension" in {
      val xsd =
         <schema>
            <complexType name="Bar">
               <sequence>
                  <element name="bar" type="string"/>
               </sequence>
            </complexType>
            <complexType name="Foo">
               <complexContent>
                  <extension base="Bar">
                     <all>
                        <element name="bar1" type="string"/>
                        <element name="bar2" type="string"/>
                     </all>
                  </extension>
               </complexContent>
            </complexType>
            <element name="foo" type="Foo"/>
         </schema>
      val model = new Model("test", xsd.toString)
      val result = convert(model)
      //println(result.content)
      mof.readPackage(result.content) must beRight.like {
         case p: Package =>
            p.elements.size must beEqualTo(3)
            p.elementNamed("Bar") must beSome.like {
               case c: Class =>
                  c.features.size must beEqualTo(1)
            }
            p.elementNamed("Foo") must beSome.like {
               case c: Class =>
                  val test = p.elementNamed("Bar").map { case sc: Class => c.isASubClassOf(sc) }.getOrElse(false)
                  test must beTrue
                  c.name must beEqualTo("Foo")
                  c.features.size must beEqualTo(2)
                  c.featureNamed("bar1") must beSome
                  c.featureNamed("bar2") must beSome
            }
            p.elementNamed("Schema") must beSome.like {
               case c: Class =>
                  c.features.size must beEqualTo(1)
                  c.featureNamed("foo") must beSome
            }
      }

   }

}
   

  