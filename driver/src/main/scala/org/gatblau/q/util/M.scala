/*
 * Copyright 2015 gatblau.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gatblau.q.util

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

// Original code from: http://blog.echo.sh/2013/11/04/exploring-scala-macros-map-to-case-class-conversion.html
// Adapted from: https://gist.github.com/mbedward/6e3dbb232bafec0792ba

trait M[T] {
  def to(t: T): Map[String, Any]
  def from(map: Map[String, Any]): T
}

object M {

  implicit def newInstance[T]: M[T] = macro newInstanceImpl[T]

  def newInstanceImpl[T: c.WeakTypeTag](c: blackbox.Context): c.Expr[M[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion

    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }.get.paramLists.head

    val (toMapParams, fromMapParams) = fields.map { field =>
      val name = field.asTerm.name
      val key = name.decodedName.toString
      val returnType = tpe.decl(name).typeSignature

      (q"$key -> t.$name", q"map($key).asInstanceOf[$returnType]")
    }.unzip

    c.Expr[M[T]] { q"""
      new M[$tpe] {
        def to(t: $tpe): Map[String, Any] = Map(..$toMapParams)
        def from(map: Map[String, Any]): $tpe = $companion(..$fromMapParams)
      } """
    }
  }
}
