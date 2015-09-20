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

import org.dbunit.dataset.IDataSet
import org.dbunit.util.fileloader.FlatXmlDataFileLoader

trait RecordLoading {
  this: Logging =>
  import Strings._

  private[q] def loadRecord(dataFilePath: String): Record = {
    logStep
    logAction(String.format(getString(LOAD_RECORD), dataFilePath))
    var set: IDataSet = null
    try {
      val loader: FlatXmlDataFileLoader = new FlatXmlDataFileLoader
      set = loader.load(dataFilePath)
    }
    catch {
      case ex: Exception => {
        logFail(ex, String.format(getString(LOAD_RECORD_FAIL), dataFilePath))
      }
    }
    val record = new Record(set)
    logPass(String.format(getString(LOAD_RECORD_PASS), record.toJSON))
    return record
  }
}
