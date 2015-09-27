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

package org.gatblau.q.util

import org.gatblau.q.util.Strings._

import scala.collection.mutable

private[q] object Cache extends Logging {
  private var values = getCache

  private[Cache] def getCache : mutable.WeakHashMap[String, AnyRef] = {
    if (values == null) {
      values = new mutable.WeakHashMap[String, AnyRef]()
    }
    values
  }

  private[q] def get[T](key: String): T = {
    logAction(String.format(getString(ACTION_GET_CACHE), key))
    val value = values.get(key)
    value match {
      case Some(v) => v match {
          case r: Record => logPass(String.format(getString(ACTION_CACHE_GET), r.toJSON))
          case _ => logAction(String.format(getString(ACTION_CACHE_GET), value))
        }
      case None => logFail(null, String.format(getString(ACTION_GET_CACHE_FAIL), key))
    }
    value.get.asInstanceOf[T]
  }

  private[q] def set(key: String, value: AnyRef) {
    value match {
      case r : Record => logAction(String.format(getString(ACTION_SET_CACHE), key, " Record => " + r.toJSON))
      case _ => logAction(String.format(getString(ACTION_SET_CACHE), key, value))
    }
    values.put(key, value)
    logPass(getString(ACTION_CACHE_SET))
  }
}