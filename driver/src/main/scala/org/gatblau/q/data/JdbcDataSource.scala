package org.gatblau.q.data

import java.io.{IOException, InputStream}
import java.util.Properties

import org.dbunit.{IDatabaseTester, JdbcDatabaseTester}

class JdbcDataSource()
  extends DataSource {
  private var tester: IDatabaseTester = null

  def this(jdbcDriver: String, connectionString: String, username: String, password: String) {
    this()
    try {
      tester = new JdbcDatabaseTester(jdbcDriver, connectionString, username, password)
    }
    catch {
      case e: ClassNotFoundException => {
        throw new RuntimeException("Could not create JDBC database driver. JDBC driver class not found", e)
      }
    }
  }

  def this(propertiesFile: String) {
    this()
    val props: Properties = new Properties
    val loader: ClassLoader = Thread.currentThread.getContextClassLoader
    val stream: InputStream = loader.getResourceAsStream(propertiesFile)
    if (stream == null) {
      throw new RuntimeException(String.format("Failed to load database configuration properties file '%s'", propertiesFile), null)
    }
    try {
      props.load(stream)
    }
    catch {
      case e: IOException => {
        throw new RuntimeException("Could not load database driver property file.", e)
      }
    }
    try {
      tester = new JdbcDatabaseTester(
        props.getProperty("jdbcDriver"),
        props.getProperty("connectionString"),
        props.getProperty("username"),
        props.getProperty("password"))
    }
    catch {
      case e: ClassNotFoundException => {
        throw new RuntimeException("Could not create instance of the JDBC database driver. JDBC driver class not found." + "Add a dependency to the database driver artifact.", e)
      }
    }
  }
}
