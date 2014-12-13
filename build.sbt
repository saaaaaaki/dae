import io.gatling.sbt.GatlingPlugin

name := "myproject"

organization := "myorganization"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.0"

sbtPlugin := true

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"

//libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.7"

//libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.0.1" % "test"

//libraryDependencies += "io.gatling" % "test-framework" % "1.0" % "test"

val test = project.in(file("."))
  .enablePlugins(GatlingPlugin)
  .settings(scalaVersion := "2.11.0")
  .settings(libraryDependencies ++= Seq( "com.typesafe.akka" % "akka-actor_2.11" % "2.3.7",
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.0.1" % "test",
                        "io.gatling" % "test-framework" % "1.0" % "test"))

jetty()