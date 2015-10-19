package org.gatblau.q.dsl

import org.gatblau.q.util.{Cache, Logging}

class Keeper extends Logging {
  def ->(key: String) : Key = Key(key)
  def key(key: String) : Key = Key(key)

  private[Keeper] case class Key(key: String) {
    def as(value: Any) : Value = Value(key, value)

    private[Keeper] case class Value(key: String, value: Any) {
      Cache.set(key, value)
    }
  }
}
