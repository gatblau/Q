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

import scala.collection.mutable.WeakHashMap

trait Caching {
  this: Logging =>
  import Strings._

  object Cache {
    private val values = new WeakHashMap[String, AnyRef]()

    private[q] def get(key: String): AnyRef = {
      logAction(String.format(getString(ACTION_GET_CACHE), key))
      val value = values.get(key)
      value match {
        case Some(value) => {
          value match {
            case r: Record => logPass(String.format(getString(ACTION_CACHE_GET), r.toJSON))
            case _ => logAction(String.format(getString(ACTION_CACHE_GET), value))
          }
        }
        case None => logFail(null, String.format(getString(ACTION_GET_CACHE_FAIL), key))
      }
      value
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
}
