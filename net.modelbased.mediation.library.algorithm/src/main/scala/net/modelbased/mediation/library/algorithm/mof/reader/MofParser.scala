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

import scala.util.parsing.combinator._
import scala.util.matching.Regex

/**
 * Define the parsing rules to build up a MoF model from a string (i.e., a
 * text file typically)
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MofParser extends RegexParsers {
   
   var global: Option[Node] = Some(MofAst.createGlobalScope)
   
   // Redefine the notion white space so as to eliminate comments starting with '#'
   protected override val whiteSpace = """(\s|#.*)+""".r
   
   def integerLiteral: Parser[Int] =
      """[0-9]+""".r ^^ {
         case v => Integer.decode(v)
      }

   def identifier: Parser[String] =
      """[a-zA-Z_][a-zA-Z0-9_]*""".r

   def enumeration: Parser[EnumerationNode] =
      ("enumeration" ~> identifier) ~ ("{" ~> repsep(enumerationLiteral, ",") <~ "}") ^^ {
         case name ~ literals => new EnumerationNode(global, name.toString(), literals)
      }

   def enumerationLiteral: Parser[LiteralNode] =
      identifier ^^ {
         case i => new LiteralNode(global, i)
      }
   
   
   def `package`: Parser[PackageNode] = {
      ("package" ~> identifier) ~ ("{" ~> rep(definition) <~ "}") ^^ {
         case name ~ defs =>
            new PackageNode(global, name, defs)
      }
   }
   
   
   def definition: Parser[Node] = {
      `package` ^^ { case p => p } |
      `class` ^^ { case c => c } |
      enumeration ^^ { case e => e }
   }
   
   
   def `class`: Parser[ClassNode] =
      opt("abstract") ~ ("class" ~> identifier) ~ opt(inheritance) ~ opt(("{" ~> rep(feature) <~ "}")) ^^ {
      case isAbstract ~ name ~ superClasses ~ features => 
         new ClassNode(global, name, isAbstract.isDefined, features.getOrElse(Seq.empty), superClasses.getOrElse(Seq.empty))
     }

   
   def inheritance: Parser[Seq[Reference]] =
      "extends" ~> repsep(reference, ",") ^^ {
         case sc => sc 
      }
      
   
   def feature: Parser[FeatureNode] =
      identifier ~ (":" ~> reference) ~ opt(multiplicity) ^^ {
         case name ~ ref ~ mul => 
            mul match {
               case None => new FeatureNode(global, name, ref)
               case Some((l, u)) => new FeatureNode(global, name, ref, l, u)
            }
      }

   def reference: Parser[Reference] =
      repsep(identifier, ".") ^^ {
         case all => new Reference(all: _*)
      }

   def multiplicity: Parser[(Int, Option[Int])] =
      "[" ~> integerLiteral ~ (".." ~> upperBound) <~ "]" ^^ {
         case l ~ u => (l, u)
      }

   def upperBound: Parser[Option[Int]] =
      "*" ^^^ None |
         integerLiteral ^^ { case v => Some(v) }
}



