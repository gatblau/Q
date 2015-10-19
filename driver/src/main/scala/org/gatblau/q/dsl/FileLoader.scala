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

import org.dbunit.dataset.IDataSet
import org.dbunit.util.fileloader.FlatXmlDataFileLoader
import org.gatblau.q.StringValues
import StringValues._
import org.gatblau.q.util.{Cache, Logging, Record}

class FileLoader extends Logging {
  def ->(path: String) : Persist = Persist(path)
  def from(path: String) : Persist = Persist(path)

  private[FileLoader] case class Persist(path: String) {

    Cache.set(path, loadRecord(path))

    def ->>(source: String) : To = To(path, source)
    def to(source: String) : To = To(path, source)

    private[FileLoader] case class To(path: String, source: String) {
    }
  }

  private[q] def loadRecord(dataFilePath: String): Record = {
    logStep
    logAction(String.format(getString(LOAD_RECORD), dataFilePath))
    var set: IDataSet = null
    try {
      val loader: FlatXmlDataFileLoader = new FlatXmlDataFileLoader
      set = loader.load(dataFilePath)
    }
    catch {
      case ex: Exception => logFail(ex, String.format(getString(LOAD_RECORD_FAIL), dataFilePath))
    }
    val record = new Record(set)
    logPass(String.format(getString(LOAD_RECORD_PASS), record.toJSON))
    record
  }
}
