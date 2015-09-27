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

import javax.inject.Inject

import akka.actor.ActorSystem
import org.gatblau.q.model._
import spray.client.pipelining._
import spray.http.HttpRequest
import spray.httpx.SprayJsonSupport

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

@Inject
class ClientImpl(serviceURI: String) extends Client {
  implicit val system = ActorSystem()
  private val timeout = 5.seconds
  import JsonFormatters._
  import SprayJsonSupport._
  import system.dispatcher

  override def isAvailable: Boolean = ???

  override def createFeatureSet(featureSetData: FeatureSet): Long = ???

  override def createCatalogue(catalogueData: Catalogue): Long = {
    val request : HttpRequest => Future[String] = sendReceive ~> unmarshal[String]
    val result = Await.result(request(Post(s"$serviceURI/catalogue", catalogueData)), timeout)
    result.toLong
  }

  override def createFeature(featureData: Feature): Long = ???

  override def getCatalogues: Seq[Catalogue] = {
    val request: HttpRequest => Future[Seq[Catalogue]] = sendReceive ~> unmarshal[Seq[Catalogue]]
    Await.result(request(Get(s"$serviceURI/catalogue")), timeout)
  }
}
