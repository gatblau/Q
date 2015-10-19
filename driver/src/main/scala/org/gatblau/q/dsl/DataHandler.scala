package org.gatblau.q.dsl

import org.gatblau.q.data.JdbcDataSource
import org.gatblau.q.util.Cache

class DataHandler {
  def ->(cfg: String) : Driver = Driver(cfg)

  private[DataHandler] case class Driver(cfg: String) {
    def jdbc : Unit = {
      Cache.set(cfg, new JdbcDataSource(cfg))
    }

    def mongo : Unit = {
      throw new UnsupportedOperationException("MongoDb datasource not yet supported.")
    }
  }
}
