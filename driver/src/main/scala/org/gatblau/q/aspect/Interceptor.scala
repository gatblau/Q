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

import java.lang.annotation.Annotation

import org.aspectj.weaver.tools.{PointcutExpression, PointcutParser}

trait Interceptor {
  protected val parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution

  protected def matches(pointcut: PointcutExpression, invocation: Invocation): Boolean = {
    pointcut.matchesMethodExecution(invocation.method).alwaysMatches ||
      invocation.target.getClass.getDeclaredMethods.exists(pointcut.matchesMethodExecution(_).alwaysMatches)
  }

  protected def matches(annotationClass: Class[T] forSome {type T <: Annotation}, invocation: Invocation): Boolean = {
    invocation.method.isAnnotationPresent(annotationClass) ||
      invocation.target.getClass.isAnnotationPresent(annotationClass)
  }

  def invoke(invocation: Invocation): AnyRef
}
