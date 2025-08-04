name := "example"
version := "0.1.0"
lazy val root = (project in file("."))
scalaVersion := "2.12.10"

val elastic4sVersion = "7.3.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.26"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.10"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.5.26"
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-client-akka" % elastic4sVersion
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-json-spray" % elastic4sVersion
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
libraryDependencies += "ch.megard" %% "akka-http-cors" % "0.4.1"
