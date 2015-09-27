import Libs._
import de.heikoseeberger.sbtheader.license.Apache2_0

lazy val q = Project("q", file("."))
    .aggregate(catalogue, client, driver)

lazy val catalogue = (project in file("catalogue"))
  .settings(Settings.basicSettings: _*)
  .settings(libraryDependencies ++=
    Libs.compile(
      scalaGuice,
      slick,
      akkaActor,
      sprayRouting,
      sprayjson,
      sprayCan,
      scalaLogging,
//      scalaModules,
      typesafeConf,
      hikaricp,
      mysqlconn exclude("mysql", "javadoc")
   )
  )
  .settings(Settings.headerSettings)
  .dependsOn(client)

lazy val client = (project in file("client"))
  .settings(Settings.headerSettings)
  .settings(Settings.basicSettings: _*)
  .settings(libraryDependencies ++=
    Libs.test(
      scalaGuice,
      cucumberJUnit exclude("info.cukes", "javadoc"),
      cucumberGuice,
      inject,
      jacksonBind,
      logback
    ) ++
    Libs.compile(
      sprayClient,
      sprayjson,
      akkaActor
    )
  )
  .dependsOn(driver)

lazy val driver = (project in file("driver"))
  .settings(Settings.headerSettings)
  .settings(Settings.basicSettings: _*)
  .settings(libraryDependencies ++=
    Libs.compile(
      gherkin,
      dbunit,
      scalaCompiler,
      scalaReflect,
      aspectj,
      aspectjWeaver,
      scalaGuice,
      jacksonBind,
      jacksonScala,
      logback
    )
  )
