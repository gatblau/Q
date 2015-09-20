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

package org.gatblau.q.dsl

import org.gatblau.q.util.{Caching, Logging, RecordLoading}

trait CacheDirective extends Caching with RecordLoading with Logging {

  object cache {

    def loadFromFile(path: String) : Persist = Persist(path)

    private[CacheDirective] case class Persist(path: String) {

      Cache.set(path, loadRecord(path))

      def saveToDatabase(source: String) : To = To(path, source)

      private[Persist] case class To(path: String, source: String) {

      }
    }
  }
}
