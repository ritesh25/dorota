import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "dorota"
  val appVersion      = "0.1-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "com.google.guava" % "guava" % "14.0.1",
    "com.tzavellas" % "sse-guice" % "0.7.1",
    "net.debasishg" %% "redisclient" % "2.10",
    "com.twitter" %% "storehaus-core" % "0.5.0",
    "com.twitter" %% "storehaus-redis" % "0.5.0",
    "net.sourceforge.nekohtml" % "nekohtml" % "1.9.18",
    jdbc,
    anorm,
    "org.specs2" %% "specs2" % "1.12.4.1" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    parallelExecution in Test := false
  )

}
