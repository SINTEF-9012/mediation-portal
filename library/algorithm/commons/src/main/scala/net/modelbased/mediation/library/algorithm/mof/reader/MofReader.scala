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
 * This is a facade to the construction process that includes, parsing, building
 * and linking (names resolutions). Each of these steps are embedded into separate
 * classes that manipulates AST.
 *
 * @author Franck Chauvel - SINTEF ICT
 *
 * @since 0.0.1
 */
class MofReader {

   /**
    * Read an enumeration literal from a string
    *
    * @param text the string containing the text to be analysed
    *
    * @return the corresponding literal object or a list of syntactic/semantic
    * errors
    */
   def readLiteral(text: String): Either[List[MofError], Literal] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker()
      parser.parse(parser.enumerationLiteral, text) match {
         case parser.Success(node: LiteralNode, _) =>
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil => node.modelElement match {
                  case None          => Left(List(new InternalError(node, "The model element (literal) was not instantiated, while no error were reported")))
                  case Some(literal) => Right(literal)
               }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) =>
            Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }

   /**
    * Read an enumeration literal from a string
    *
    * @param text the string containing the enumeration literal to be analysed
    *
    * @return the corresponding enumeration object of a list of syntactic/semantic
    * errors
    */
   def readEnumeration(text: String): Either[List[MofError], Enumeration] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker()
      parser.parse(parser.enumeration, text) match {
         case parser.Success(node: EnumerationNode, _) =>
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil => node.modelElement match {
                  case None              => Left(List(new InternalError(node, "The model element (enumeration) was not instantiated, while no error were reported")))
                  case Some(enumeration) => Right(enumeration)
               }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) => Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }
   
   
   /**
    * Read a data type declaration from a string
    * 
    * @param text the string containing the declaration of the data type literal
    *
    * @return the corresponding enumeration object of a list of syntactic/semantic
    * errors
    */
   def readDataType(text: String): Either[List[MofError], DataType] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker() 
      parser.parse(parser.dataType, text) match {
         case parser.Success(node: DataTypeNode, _) =>
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil => node.modelElement match {
                  case None              => Left(List(new InternalError(node, "The model element (data type) was not instantiated, while no error were reported")))
                  case Some(dt) => Right(dt)
               }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) => Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }
   

   /**
    * Read a feature from a string
    *
    * @param text the string containing the text of the feature to be parsed
    *
    * @return the corresponding feature object or a list of syntactic/semantic errors
    */
   def readFeature(text: String): Either[List[MofError], Feature] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker()
      parser.parse(parser.feature, text) match {
         case parser.Success(node: FeatureNode, _) =>
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil =>
                  node.modelElement match {
                     case None          => 
                        Left(List(new InternalError(node, "The model element (feature) was not instantiated, while no error were reported")))
                     case Some(feature) => 
                        Right(feature)
                  }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) => Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }
   
   
   /**
    * Read a class from a string
    * 
    * @param text the string containing the text of the class to be parsed
    * 
    * @return the corresponding class object or a list of syntactic/semantic errors
    */
   def readClass(text: String): Either[List[MofError], Class] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker()
      parser.parse(parser.`class`, text) match {
         case parser.Success(node: ClassNode, _) => 
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil => 
                  node.modelElement match {
                     case None => Left(List(new InternalError(node, "The model element (class) was not instantiated, while no error were reported")))
                     case Some(c: Class) => Right(c)
                  }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) => Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }
   
   
   /**
    * read a package from a string
    * 
    * @param text the string that contains the text of the package to be analysed
    * 
    * @return the corresponding package object or a list of syntactic/semantic errors
    */
   def readPackage(text: String): Either[List[MofError], Package] = {
      val parser = new MofParser()
      val builder = new MofBuilder()
      val linker = new MofLinker()
      parser.parse(parser.`package`, text) match {
         case parser.Success(node: PackageNode, _) =>
            val errors = node.accept(builder, Nil)
            node.accept(linker, errors) match {
               case Nil => 
                  node.modelElement match {
                     case None => Left(List(new InternalError(node, "The model element (package) was not instantiated, while no error were reported")))
                     case Some(p: Package) => Right(p)
                  }
               case e @ _ => Left(e)
            }
         case parser.Failure(message, next) => Left(List(new ParsingError(message, next.pos.line, next.pos.column)))
      }
   }

}