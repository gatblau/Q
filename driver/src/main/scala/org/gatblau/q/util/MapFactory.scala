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

import java.sql.Timestamp
import java.time.LocalDateTime

import scala.collection.mutable
import scala.reflect.runtime.universe._

class MapFactory {
  def of[T: M](key: String)(implicit manifest: Manifest[T]) = {
    val r : Record = Cache.get(key)
    val map = new mutable.HashMap[String, Any]()
    val fields = typeOf[T].members.collect { case m: MethodSymbol if m.isCaseAccessor => m }.toList

    fields.foreach(f => {
      val name = f.asTerm.name.toString
      val isOption = f.typeSignature.toString.startsWith("=> Option[")
      if (isOption) {
        if (r.hasValue(name))
          map += (name -> Option(r.getValue(name)))
        else
          map += (name -> None)
      }
      else {
        var value = getDefaultValue(f)
        try {
          value = r.getValue(name)
        }
        catch {
          case e: Exception =>
        }
        map += (name -> value)
      }
    })
    map.toMap
  }

  private[this] def getDefaultValue(f : MethodSymbol) : Any = {
    val t = f.typeSignature.toString
    if (t.contains("Long")) return 0L
    if (t.contains("Int")) return 0
    if (t.contains("Timestamp")) return Timestamp.valueOf(LocalDateTime.now())
    if (t.contains("String")) return ""
    throw new RuntimeException(s"Default value for type '$t' not defined. Record is missing field case class needs.")
  }
}
