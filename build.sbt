ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "HumidityComputationApp",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.16",
      "dev.zio" %% "zio-streams" % "2.0.16",
      "dev.zio" %% "zio-cli" % "0.5.0",
      "dev.zio" %% "zio-nio" % "2.0.0",
      "dev.zio" %% "zio-test" % "2.0.16" % Test,
      "dev.zio" %% "zio-test-sbt" % "1.0.12" % "test"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
