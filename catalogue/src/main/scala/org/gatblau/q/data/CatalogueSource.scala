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
import slick.jdbc.{ GetResult => r }

trait CatalogueSource {

  self : DataSource =>

  import profile.api._

  implicit def GetResultCatalogue(implicit e0: r[Long], e1: r[Option[String]], e2: r[String], e3: r[java.sql.Timestamp]): r[Catalogue] = r {
    prs => import prs._
    Catalogue.tupled((<<[Long], <<?[String], <<?[String], <<[String], <<?[String], <<[Timestamp], <<[Long]))
  }

  class Catalogues(tag: Tag) extends Table[Catalogue](tag, "Catalogue") {
    val id          : Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    val author      : Rep[Option[String]] = column[Option[String]]("author", O.Length(255, varying = true), O.Default(None))
    val description : Rep[Option[String]] = column[Option[String]]("description", O.Length(255, varying = true), O.Default(None))
    val name        : Rep[String] = column[String]("name", O.Length(255, varying = true))
    val owner       : Rep[Option[String]] = column[Option[String]]("owner", O.Length(255, varying = true), O.Default(None))
    val time        : Rep[Timestamp] = column[Timestamp]("time")
    val version     : Rep[Long] = column[Long]("version")

    def * = (id, author, description, name, owner, time, version) <> (Catalogue.tupled, Catalogue.unapply)

    def ? = (Rep Some id, author, description, Rep Some name, owner, Rep Some time, Rep Some version).shaped <>( {
      r => import r._; _1.map(_ => Catalogue.tupled((_1.get, _2, _3, _4.get, _5, _6.get, _7.get)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }
}
