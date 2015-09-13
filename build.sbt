import Libs._
import de.heikoseeberger.sbtheader.license.Apache2_0

lazy val q = Project("q",file("."))
  .settings(Settings.basicSettings: _*)
    .aggregate(catalogue, client, driver)

lazy val catalogue = (project in file("catalogue"))
  .settings(libraryDependencies ++=
    Libs.compile(
      scalaGuice,
      slick,
      akkaActor,
      sprayRouting,
      sprayjson,
      sprayCan,
      scalaLogging,
      typesafeConf,
      hikaricp,
      mysqlconn exclude("mysql", "javadoc")
   )
  )
  .settings(Settings.headerSettings)
  .dependsOn(client)

lazy val client = (project in file("client"))
  .settings(Settings.headerSettings)
  .settings(libraryDependencies ++=
    Libs.test(
      scalaGuice,
      cucumberJUnit exclude("info.cukes", "javadoc"),
      cucumberGuice,
      inject
    )
  )
  .dependsOn(driver)

lazy val driver = (project in file("driver"))
  .settings(Settings.headerSettings)
  .settings(libraryDependencies ++=
    Libs.compile(
      gherkin,
      dbunit
    )
  )
