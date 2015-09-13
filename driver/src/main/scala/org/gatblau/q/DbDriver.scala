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

package org.gatblau.q

import org.dbunit.dataset.{ITable, IDataSet}
import java.io.File
import java.util.Map

/**
 * The contract implemented by database drivers.
 * A database setDriver implements operations that modify the state (data) of a database for testing purposes.
 * Different implementations can be used to drive different databases (e.g. sql, no-sql, etc)
 */
trait DbDriver {
  /**
   * Puts the database in a particular state.
   * @param datafile a path to the file containing the information required to put
   *                 the database in a particular state.
   */
  def setup(datafile: String)

  /**
   * Puts the database in a particular state.
   * @param datafile the file containing the information required to put the database in a particular state.
   */
  def setup(datafile: File)

  /**
   * Puts the database in a particular state.
   * @param dataSet the data set containing the information required to put the database in a particular state.
   */
  def setup(dataSet: IDataSet)

  /**
   * Queries the data source.
   * @param query the query statement.
   * @param params an array of query parameters that are merged with the query statement.
   * @return a tabular representation of the result set.
   */
  def query(query: String, params: AnyRef*): ITable

  /**
   * Closes the database setDriver and releases resources.
   */
  def teardown

  /**
   * Exports a specific table to a file.
   * @param tableName the name of the table to be exported.
   * @param query the query used to extract the table data.
   * @param file the file to write the output to.
   */
  def export(tableName: String, query: String, file: File)

  /**
   * Export database tables onto file.
   * @param tables a map containing entries with the table name and the query to extract the table
   *               respectively as key and value pairs.
   * @param file the file to write the output to.
   */
  def export(tables: Map[String, String], file: File)

  /**
   * Export database tables onto a file.
   * @param file the file to write the output to.
   * @param tableNameList a list of table names to export.
   */
  def export(file: File, tableNameList: String*)

  /**
   * Executes an update in the database.
   * @param updateStatement the update statement.
   * @param params a list of parameters used in the update statement.
   */
  def update(updateStatement: String, params: AnyRef*)
}
