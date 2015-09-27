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

package org.gatblau.q

import org.gatblau.q.aspect.{TrackingInterceptor, ManagedProxy, ManagedFactory}
import org.gatblau.q.util.{MapFactory, FileLoader}

trait Q {
  val load = new FileLoader
  val map =new MapFactory

  def track[T](instance: AnyRef)(implicit m: Manifest[T]) : T =
    ManagedFactory.createComponent[T](
      m.runtimeClass.asInstanceOf[Class[T]], new ManagedProxy(instance) with TrackingInterceptor)
}
