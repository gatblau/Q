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

package org.gatblau.q.aspect

import java.lang.reflect.Method

case class Invocation(val method: Method, val args: Array[AnyRef], val target: AnyRef) {
  def invoke: AnyRef = method.invoke(target, args:_*)
  override def toString: String = "Invocation [method: " + method.getName + ", args: " + args + ", target: " + target + "]"
//  override def hashCode(): Int = ???
//  override def equals(that: Any): Boolean = ???
}
