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
import slick.jdbc.{GetResult => r}
import org.gatblau.q.model.Event

trait EventSource {

  self: DataSource =>

  import profile.api._
  import profile.api.ForeignKeyAction

  implicit def GetResultEvent(implicit e0: r[Long], e1: r[String], e2: r[Timestamp]): r[Event] = r {
    prs => import prs._
    Event.tupled((<<[Long], <<[String], <<[Timestamp], <<[String], <<[Long]))
  }

  class Events(tag: Tag) extends Table[Event](tag, "Event") {
    val id            : Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    val description   : Rep[String] = column[String]("description", O.Length(255, varying = true))
    val time          : Rep[Timestamp] = column[Timestamp]("time")
    val `type`        : Rep[String] = column[String]("type", O.Length(255, varying = true))
    val stepId        : Rep[Long] = column[Long]("step_id")

    lazy val steps    = foreignKey("FK_Event_Step", stepId, step)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    def * = (id, description, time, `type`, stepId) <>(Event.tupled, Event.unapply)

    def ? = (Rep.Some(id), Rep.Some(description), Rep.Some(time), Rep.Some(`type`), Rep.Some(stepId)).shaped.<>({
      r => import r._; _1.map(_ => Event.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }
}
