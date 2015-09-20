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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.gatblau.q.util.Logging

trait TrackingInterceptor extends ManagedProxy with Interceptor with Logging {
  val loggingPointcut = parser.parsePointcutExpression("execution(* *.*(..))")
  val mapper = new ObjectMapper()

  mapper.registerModule(DefaultScalaModule)

  abstract override def invoke(invocation: Invocation): AnyRef = {
    if (matches(loggingPointcut, invocation)) {
      logBefore(invocation)
      try {
        val result = super.invoke(invocation)
        logAfter(invocation)
        result
      }
      catch {
        // TODO: exception information is erased - need to find a solution
        case e : Exception => {
          logFail(e, String.format("Invocation failed."))
          null
        }
      }
    }
    else {
      super.invoke(invocation)
    }
  }

  private def logBefore(inv: Invocation) : Unit = {
    logAction(String.format(
      "Invoking method '%s' with parameters '%s'",
      inv.method,
      params(inv.args)
    ))
  }

  private def logAfter(inv: Invocation) : Unit = {
    logPass(String.format("Method '%s' invoked successfully.", inv.method))
  }

  private def params(args: Array[AnyRef]) : String = {
    var s = ""
    val len = args.length - 1
    for (i <- 0 to len) {
      s += mapper.writeValueAsString(args(i))
      if (i < len) s += " - "
    }
    s
  }
}