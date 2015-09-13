import sbt._

object Libs {
  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  val repos = Seq(
    "Central Maven Repository" at "https://repo1.maven.org/maven2",
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    "Spray Releases" at "http://repo.spray.io/",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Scalaz Releases" at "http://dl.bintray.com/scalaz/releases/"
  )

  val akkaActor     = "com.typesafe.akka"           %%  "akka-actor"              % Version.AKKA
  val akkaSlf4j     = "com.typesafe.akka"           %%  "akka-slf4j"              % Version.AKKA
  val logback       = "ch.qos.logback"              %   "logback-classic"         % Version.LOGBACK
  val scalaz        = "org.scalaz"                  %%  "scalaz-core"             % Version.SCALAZ
  val sprayCan      = "io.spray"                    %%  "spray-can"               % Version.SPRAY
  val sprayRouting  = "io.spray"                    %%  "spray-routing"           % Version.SPRAY
  val sprayjson     = "io.spray"                    %%  "spray-json"              % "1.3.2"
  val spraytest     = "io.spray"                    %%  "spray-testkit"           % Version.SPRAY
  val slick         = "com.typesafe.slick"          %%  "slick"                   % Version.SLICK
  val slickhttp     = "com.typesafe.slick"          %%  "slick-http"              % Version.SLICK
  val slickcodegen  = "com.typesafe.slick"          %%  "slick-codegen"           % Version.SLICK
  val typesafeConf  = "com.typesafe"                %   "config"                  % Version.CONFIG
  val scalaGuice    = "net.codingwell"              %%  "scala-guice"             % Version.SCALAGUICE
  val mysqlconn     = "mysql"                       %   "mysql-connector-java"    % Version.MYSQLCONN
  val scalaLogging  = "com.typesafe.scala-logging"  %%  "scala-logging-slf4j"     % Version.SCALALOGGING
  val cucumberJUnit = "info.cukes"                  %   "cucumber-junit"          % Version.CUCUMBER
  val cucumberGuice = "info.cukes"                  %   "cucumber-guice"          % Version.CUCUMBER
  val gherkin       = "info.cukes"                  %   "gherkin"                 % Version.GHERKIN
  val dbunit        = "org.dbunit"                  %   "dbunit"                  % Version.DBUNIT
  val inject        = "javax.inject"                %   "javax.inject"            % Version.INJECT
  val hikaricp      = "com.zaxxer"                  %   "HikariCP"                % Version.HIKARICP
}