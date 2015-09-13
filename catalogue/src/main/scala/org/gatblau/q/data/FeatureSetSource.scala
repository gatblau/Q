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
import org.gatblau.q.model.FeatureSet
import slick.jdbc.{ GetResult => r }

trait FeatureSetSource {

  self: DataSource =>

  import profile.api._

  implicit def GetResultFeatureSet(implicit e0: r[Long], e1: r[Option[String]], e2: r[String], e3: r[java.sql.Timestamp]): r[FeatureSet] = r {
    prs => import prs._
    FeatureSet.tupled((<<[Long], <<?[String], <<[String], <<[java.sql.Timestamp], <<[Long]))
  }

  class FeatureSets(tag: Tag) extends Table[FeatureSet](tag, "FeatureSet") {
    val id          : Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    val description : Rep[Option[String]] = column[Option[String]]("description", O.Length(255, varying = true), O.Default(None))
    val name        : Rep[String] = column[String]("name", O.Length(255, varying = true))
    val time        : Rep[java.sql.Timestamp] = column[Timestamp]("time")
    val catalogueId : Rep[Long] = column[Long]("catalogue_id")

    lazy val catalogues = foreignKey("FK_FeatureSet_Catalogue", catalogueId, catalogue)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    val featureSetNameUK = index("UK_FeatureSet_Name", name, unique = true)

    def * = (id, description, name, time, catalogueId) <> (FeatureSet.tupled, FeatureSet.unapply)

    def ? = (Rep.Some(id), description, Rep.Some(name), Rep.Some(time), Rep.Some(catalogueId)).shaped <>( {
      r => import r._; _1.map(_ => FeatureSet.tupled((_1.get, _2, _3.get, _4.get, _5.get)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }
}
