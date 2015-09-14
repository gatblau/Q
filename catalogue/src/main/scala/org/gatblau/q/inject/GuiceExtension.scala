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

package org.gatblau.q.inject

import akka.actor._
import com.google.inject.Injector

import scala.reflect._

object GuiceExtension
  extends ExtensionId[GuiceExtensionImpl]
  with ExtensionIdProvider {

  override def lookup() = {
    GuiceExtension
  }

  override def createExtension(system: ExtendedActorSystem) = {
    new GuiceExtensionImpl
  }
}

class GuiceExtensionImpl extends Extension {
  private var injector: Injector = _

  def init(injector: Injector) {
    this.injector = injector
  }

  def props[A <: Actor : ClassTag] = {
    val producerClass = classTag[GuiceActorProducer[A]].runtimeClass
    val actorClass = classTag[A].runtimeClass
    Props(producerClass, injector, actorClass)
  }
}
