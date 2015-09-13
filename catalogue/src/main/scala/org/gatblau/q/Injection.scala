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

import javax.inject.{Provider, Singleton}

import akka.actor._
import com.google.inject.{Injector, Provides}
import com.typesafe.config.ConfigValueType._
import com.typesafe.config.{Config, ConfigFactory, ConfigRenderOptions, ConfigValue}
import com.typesafe.scalalogging.slf4j.LazyLogging
import net.codingwell.scalaguice.BindingExtensions._
import net.codingwell.scalaguice.ScalaModule

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext
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

class AkkaModule extends ScalaModule {
  def configure {
  }

  @Provides
  @Singleton
  def provideActorSystem(injector: Injector) : ActorSystem = {
    val system = ActorSystem("Q")
    GuiceExtension(system).init(injector)
    system
  }

  @Provides
  @Singleton
  def provideActorRefFactory(systemProvider: Provider[ActorSystem]): ActorRefFactory = {
    systemProvider.get
  }

  @Provides
  @Singleton
  def provideExecutionContext(systemProvider: Provider[ActorSystem]): ExecutionContext = {
    systemProvider.get.dispatcher
  }
}

class ConfigModule extends ScalaModule with LazyLogging {
  def configure {
    val config = loadConfig
    bind[Config].toInstance(config)
    bindConfig(config)
  }

  protected[this] def loadConfig() = {
    enableEnvOverride
    val config = ConfigFactory.load
    logger.trace(s"${config.root.render(ConfigRenderOptions.concise.setFormatted(true))}")
    config
  }

  private def bindConfig(config: Config) {
    for (entry <- config.entrySet) {
      val cv = entry.getValue
      cv.valueType match {
        case STRING | NUMBER | BOOLEAN =>
          bindPrimitive(entry.getKey, entry.getValue)
        case LIST =>
          bindList(entry.getKey, entry.getValue)
        case NULL =>
          throw new AssertionError(
            s"Did not expect NULL entry in ConfigValue.entrySet: ${cv.origin}"
          )
        case OBJECT =>
          throw new AssertionError(
            s"Did not expect OBJECT entry in ConfigValue.entrySet: ${cv.origin}"
          )
      }
    }
  }

  private def bindPrimitive(key: String, value: ConfigValue) {
    val unwrapped = value.unwrapped.toString
    binderAccess.bindConstant.annotatedWithName(key).to(unwrapped)
  }

  private def bindList(key: String, value: ConfigValue) {
    val list = value.unwrapped.asInstanceOf[java.util.List[Any]]
    if (list.size == 0) {
      // Seq[Int|Double|Boolean] type params will only match a value bound as Seq[Any]
      bind[Seq[Any]].annotatedWithName(key).toInstance(Seq())
      // Seq[String] type params will only match a value bound as Seq[String]
      bind[Seq[String]].annotatedWithName(key).toInstance(Seq())
    }
    else {
      val seq = list.get(0) match {
        case x: Integer =>
          val v = list.collect({case x: java.lang.Integer => x.intValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Double =>
          val v = list.collect({case x: java.lang.Double => x.doubleValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Boolean =>
          val v = list.collect({case x: java.lang.Boolean => x.booleanValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: String =>
          val v = list.collect({case x: String => x}).toSeq
          bind[Seq[String]].annotatedWithName(key).toInstance(v)
        case x =>
          throw new AssertionError("Unsupported list type " + x.getClass)
      }
    }
  }

  /**
   * Configures the typesafe config library so that it reads an environment
   * specific configuration file instead of the default application.conf.
   *
   * The prefix of the file to load is taken from the value of the 'env' system
   * property.  For example, to read production.conf rather that application.conf,
   * specify -Denv=production in the command starting the server as in:
   *
   * java -jar server.jar -Denv=production
   */
  def enableEnvOverride {
    val env = System.getProperty("env")
    if (env != null) {
      System.setProperty("config.resource", env + ".conf")
    }
  }
}

class GuiceActorProducer[A <: Actor](injector: Injector, actor: Class[A])
  extends IndirectActorProducer {

  override def actorClass = {
    actor
  }

  override def produce = {
    injector.getInstance(actorClass)
  }
}
