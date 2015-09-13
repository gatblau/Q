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

package org.gatblau.q.data

import java.sql.Timestamp

import org.gatblau.q.model.Catalogue
import slick.jdbc.GetResult

/**
 * Represents the data source Q uses to store feature catalogues
 */
trait DataSource extends CatalogueSource
    with EventSource
    with FeatureSource
    with FeatureSetSource
    with ScenarioSource
    with StepSource {

  // change the driver here to connect to a different database
  val profile = slick.driver.MySQLDriver

  import profile.api._

  lazy val catalogue = new TableQuery(tag => new Catalogues(tag))
  lazy val event = new TableQuery(tag => new Events(tag))
  lazy val feature = new TableQuery(tag => new Features(tag))
  lazy val featureSet = new TableQuery(tag => new FeatureSets(tag))
  lazy val scenario = new TableQuery(tag => new Scenarios(tag))
  lazy val step = new TableQuery(tag => new Steps(tag))

  lazy val schema = Array(
    catalogue.schema,
    event.schema,
    feature.schema,
    featureSet.schema,
    scenario.schema,
    step.schema
  ).reduceLeft(_ ++ _)

  def ddl = schema

  val db = Database.forConfig("db")
}
