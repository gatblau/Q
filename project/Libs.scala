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

  val akkaActor     = "com.typesafe.akka"           %   "akka-actor_2.11"         % Version.AKKA
  val akkaSlf4j     = "com.typesafe.akka"           %  "akka-slf4j_2.11"          % Version.AKKA
  val logback       = "ch.qos.logback"              %   "logback-classic"         % Version.LOGBACK
  val scalaz        = "org.scalaz"                  %   "scalaz-core_2.11"        % Version.SCALAZ
  val sprayCan      = "io.spray"                    %   "spray-can_2.11"          % Version.SPRAY
  val sprayRouting  = "io.spray"                    %   "spray-routing_2.11"      % Version.SPRAY
  val sprayClient   = "io.spray"                    %   "spray-client_2.11"       % Version.SPRAY
  val sprayjson     = "io.spray"                    %  "spray-json_2.11"          % "1.3.2"
  val spraytest     = "io.spray"                    %  "spray-testkit_2.11"       % Version.SPRAY
  val slick         = "com.typesafe.slick"          %  "slick_2.11"               % Version.SLICK
  val slickhttp     = "com.typesafe.slick"          %  "slick-http_2.11"          % Version.SLICK
  val slickcodegen  = "com.typesafe.slick"          %  "slick-codegen_2.11"       % Version.SLICK
  val typesafeConf  = "com.typesafe"                %   "config"                  % Version.CONFIG
  val scalaGuice    = "net.codingwell"              %  "scala-guice_2.11"         % Version.SCALAGUICE
  val mysqlconn     = "mysql"                       %   "mysql-connector-java"    % Version.MYSQLCONN
  val scalaLogging  = "com.typesafe.scala-logging"  %   "scala-logging_2.11"      % Version.SCALALOGGING
  val cucumberJUnit = "info.cukes"                  %   "cucumber-junit"          % Version.CUCUMBER
  val cucumberGuice = "info.cukes"                  %   "cucumber-guice"          % Version.CUCUMBER
  val gherkin       = "info.cukes"                  %   "gherkin"                 % Version.GHERKIN
  val dbunit        = "org.dbunit"                  %   "dbunit"                  % Version.DBUNIT
  val inject        = "javax.inject"                %   "javax.inject"            % Version.INJECT
  val hikaricp      = "com.zaxxer"                  %   "HikariCP"                % Version.HIKARICP
  val scalaCompiler = "org.scala-lang"              %   "scala-compiler"          % Version.SCALA
  val scalaReflect  = "org.scala-lang"              %   "scala-reflect"           % Version.SCALA
  val scalaModules  = "org.scala-lang-modules"      %   "scala-xml"               % "1.0.5"
  val aspectj       = "org.aspectj"                 %   "aspectjrt"               % Version.ASPECTJ
  val aspectjWeaver = "org.aspectj"                 %   "aspectjweaver"           % Version.ASPECTJ
  val jacksonBind   = "com.fasterxml.jackson.core"  %   "jackson-databind"        % Version.JACKSON
  val jacksonScala  = "com.fasterxml.jackson.module"% "jackson-module-scala_2.11" % Version.JACKSONMODULE
  val scalajhttp    = "org.scalaj"                  %   "scalaj-http_2.11"        % Version.SCALAJHTTP
}