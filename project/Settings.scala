import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader._
import de.heikoseeberger.sbtheader.license.Apache2_0
import sbt.Keys._
import sbt._

object Settings {
  lazy val basicSettings = Seq(
    version := Version.Q,
    homepage := Some(new URL(Vars.Q_URI)),
    organization := Vars.Q_ORG,
    organizationHomepage := Some(new URL(Vars.Q_ORG_HOME)),
    description := Vars.Q_DESC,
    startYear := Some(Vars.Q_YEAR),
    licenses := Seq(Vars.LIC_NAME -> new URL(Vars.LIC_URI)),
    scalaVersion := Version.SCALA,
    scalaBinaryVersion := "2.11",
    autoScalaLibrary := false,
    resolvers ++= Libs.repos,
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.8",
      "-language:_",
      "-Xlog-reflective-calls",
      "-Ylog-classpath"
    )
  ) 

  lazy val headerSettings = Seq(
    headers := Map(
      "scala" -> Apache2_0(Vars.Q_YEAR.toString, Vars.Q_ORG),
      "conf" -> Apache2_0(Vars.Q_YEAR.toString, Vars.Q_ORG, "#")
    )
  )
}