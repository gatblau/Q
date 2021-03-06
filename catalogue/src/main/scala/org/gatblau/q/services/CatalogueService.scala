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

package org.gatblau.q.services

import org.gatblau.q.data.DataSource
import org.gatblau.q.model.Catalogue

import scala.concurrent.Future

trait CatalogueService {
   def findAll : Future[Seq[Catalogue]]
   def create(catalogue: Catalogue) : Long
}

class CatalogueServiceImpl
    extends CatalogueService
    with DataSource {
  import profile.api._

  override def findAll: Future[Seq[Catalogue]] = {
    db.run(catalogue.result)
  }

  override def create(catalogue: Catalogue): Long = {
    10
  }
}
