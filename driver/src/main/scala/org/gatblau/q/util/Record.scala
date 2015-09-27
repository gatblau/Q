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

import org.dbunit.dataset.{Column, DataSetException, ITable, IDataSet}
import com.fasterxml.jackson.databind.ObjectMapper

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Record {

  var set: IDataSet = null
  private var table: ITable = null
  private var mapper: ObjectMapper = null

  private[q] def this(table: ITable) {
    this()
    setTable(table)
  }

  private[q] def this(set: IDataSet) {
    this()
    this.set = set
    try {
      val tableNames: Array[String] = set.getTableNames
      if (tableNames.length > 1) {
        throw new RuntimeException("Data set must contain only one table.")
      }
      setTable(set.getTable(tableNames(0)))
    }
    catch {
      case ex: Exception => throw new RuntimeException("Fail to create record.", ex)
    }
  }

  private def setTable(table: ITable) {
    this.table = table
    mapper = new ObjectMapper
    if (table == null) {
      throw new RuntimeException("Null reference passed to Record constructor.")
    }
    if (table.getRowCount > 1) {
      throw new RuntimeException("Table passed to Record constructor should have only one record.")
    }
  }

  /**
   * Checks that the field values in the passed in record match the ones in this record.
   * @param other the other record used to match field values against.
   * @return a MatchResult object with match = true if the field values in the passed in record match
   *         the ones in this record; and match = false if one field value did not match, and the name of
   *         the mismatched column.
   */
  def matchFields(other: Record): MatchResult = {
    val rowCount: Int = table.getRowCount
    try {
      val columns: Array[Column] = other.table.getTableMetaData.getColumns
      val len = columns.length - 1
      for (i <- 0 to len) {
        val columnName: String = columns(i).getColumnName
        if (!(other.table.getValue(0, columnName) == this.table.getValue(0, columnName))) {
          return new MatchResult(false, columnName)
        }
      }
    }
    catch {
      case e: DataSetException =>
        e.printStackTrace()
        return MatchResult(false, "")
    }
    MatchResult(true, "")
  }

  private[q] case class MatchResult(matched: Boolean, mismathedColumn: String)

  def hasValue(name: String) : Boolean = {
    getFieldNames.indexOf(name) != -1
  }

  def getValue[T](columnName: String): T = {
    try {
      table.getValue(0, columnName).asInstanceOf[T]
    }
    catch {
      case e: DataSetException => throw new RuntimeException(e)
    }
  }

  private[q] def getFieldNames : Array[String] = {
    table.getTableMetaData.getColumns.map(c => c.getColumnName)
  }

  private[q] def getFieldValues : Seq[AnyRef] = {
    val list = ListBuffer()
    getFieldNames.foreach(name => list += getValue(name))
    list.toSeq
  }

  private[q] def toMap : Map[String, String] = {
    val map = new mutable.HashMap[String, String]()
    getFieldNames.foreach(name => map += (name -> getValue(name)))
    map.toMap
  }

  def toJSON: String = {
    val builder: StringBuilder = new StringBuilder
    try {
      val columns: Array[Column] = table.getTableMetaData.getColumns
      val len = columns.length - 1
      builder.append("{ ")
      for(i <- 0 to len){
        val columnName: String = columns(i).getColumnName
        builder.append(columnName)
        builder.append(" : ")
        builder.append(""""""")
        builder.append(table.getValue(0, columnName))
        builder.append(""""""")
        if (i < columns.length - 1) {
          builder.append(", ")
        }
      }
      builder.append(" }")
    }
    catch {
      case e: DataSetException => throw new RuntimeException(e)
    }
    builder.toString()
  }
}
