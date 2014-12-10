name := "myproject"

organization := "myorganization"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.0"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.7"

jetty()