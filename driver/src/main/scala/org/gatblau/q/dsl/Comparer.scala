package org.gatblau.q.dsl

import org.gatblau.q.util.{Cache, Logging, Record}

class Comparer extends Logging {
  def ->(key: String) : Compare = Compare(key)
  def record(key: String) : Compare = Compare(key)

  private [Comparer] case class Compare(key: String) {
    val record : Record = Cache.get(key);

    def to(query: String) : Source = Source(record, query)
  }

  private [Comparer] case class Source(record: Record, query: String) {

    def in(source: String) : Unit = {
      val a = 0
    }
  }

  private[Comparer] def check(recordKey: String, dsKey: String, query: String, params: AnyRef*) {

  }
}
