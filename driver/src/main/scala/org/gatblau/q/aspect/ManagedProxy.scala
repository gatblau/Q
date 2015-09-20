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

import java.lang.reflect.{ Method, InvocationHandler }

class ManagedProxy(val target: AnyRef) extends InvocationHandler {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = invoke(Invocation(m, args, target))
  def invoke(invocation: Invocation): AnyRef = invocation.invoke
}
