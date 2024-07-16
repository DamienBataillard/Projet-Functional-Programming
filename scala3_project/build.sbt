val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3_project",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.2.0-M2",
      "dev.zio" %% "zio-streams" % "2.0.0",
      "dev.zio" %% "zio-console" % "2.0.0"
    )
  )