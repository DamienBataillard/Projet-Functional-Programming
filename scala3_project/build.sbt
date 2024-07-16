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
      "dev.zio" %% "zio-json" % "0.3.0-RC8", // Mise à jour vers une version stable si disponible
      "dev.zio" %% "zio-streams" % "2.0.0",
      "dev.zio" %% "zio-console" % "1.0.0" // Ajout de la dépendance zio-console
    )
  )
