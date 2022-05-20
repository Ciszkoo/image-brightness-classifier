name := "is-image-bright"
version := "0.0.1"

scalaVersion := "3.1.2"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-encoding", "utf8"
)

libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.8.0"