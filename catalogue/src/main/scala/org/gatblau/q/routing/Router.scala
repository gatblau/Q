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

package org.gatblau.q.routing

import akka.event.Logging
import com.typesafe.scalalogging.slf4j.LazyLogging
import spray.http.StatusCodes
import spray.routing._
import spray.util.LoggingContext

trait Router extends HttpService with Directives with LazyLogging {

  protected[this] def routeSeq: Seq[Routes]

  private val rejectionHandler = {
    RejectionHandler {
      case MissingQueryParamRejection(param) :: _ =>
        complete(StatusCodes.BadRequest, s"Request is missing required query parameter '$param'")
    }
  }

  lazy val routes =
    logRequestResponse("Q", Logging.InfoLevel) {
      handleRejections(rejectionHandler) {
        handleExceptions(exceptionHandler) {
          routeSeq.tail.foldLeft(routeSeq.head.route) {
            (routes, next) => routes ~ next.route
          }
        }
      }
    }

  private def exceptionHandler(implicit log: LoggingContext) = {
    ExceptionHandler {
      case e: ServiceException =>
        complete(e.statusCode, e.msg, e.cause)
    }
  }
}
