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

import javax.inject.Named

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.{Singleton, Provides, AbstractModule}
import net.codingwell.scalaguice.{ScalaMultibinder, ScalaModule}
import org.gatblau.q.routes.CatalogueRoutes
import org.gatblau.q.services.{CatalogueService, CatalogueServiceImpl}

class MainModule extends AbstractModule with ScalaModule {

  def configure {
    install(new ConfigModule)
    install(new AkkaModule)
    install(new RoutesModule)
    install((new ServicesModule))
    bind[WebServer]
  }

  @Provides
  @Singleton
  @Named("RouterActor")
  def provideRouterActor(system: ActorSystem): ActorRef = {
    system.actorOf(GuiceExtension(system).props[RouterActor])
  }
}

class RoutesModule extends ScalaModule {
  private lazy val routeBinder = ScalaMultibinder.newSetBinder[Routes](binder)

  protected[this] def bindRoute = {
    routeBinder.addBinding
  }

  def configure {
    bindRoute.to[CatalogueRoutes]
  }
}

class ServicesModule  extends ScalaModule {

  def configure {
    bind[CatalogueService].to[CatalogueServiceImpl]
  }
}
