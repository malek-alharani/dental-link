import sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.MergeStrategy

// Project name and version
name := "dental-link"

version := "0.0.1-SNAPSHOT"

// Scala version
scalaVersion := "2.13.14"

// Java version compatibility (SBT doesn't need this directly, but Java 21 will be handled by JDK environment)
javacOptions ++= Seq("-source", "21", "-target", "21")

// Library dependencies
libraryDependencies ++= Seq(
  // Spring Boot dependencies
  "org.springframework.boot" % "spring-boot-autoconfigure" % "3.3.3" exclude("org.springframework.boot", "spring-boot-starter-logging") exclude("ch.qos.logback", "logback-classic"),
  "org.springframework.boot" % "spring-boot-starter-data-jpa" % "3.3.3" exclude("org.springframework.boot", "spring-boot-starter-logging") exclude("ch.qos.logback", "logback-classic"),
  "org.springframework.boot" % "spring-boot-starter-hateoas" % "3.3.3" exclude("org.springframework.boot", "spring-boot-starter-logging") exclude("ch.qos.logback", "logback-classic"),
  "org.springframework.boot" % "spring-boot-starter-web" % "3.3.3" exclude("org.springframework.boot", "spring-boot-starter-logging") exclude("ch.qos.logback", "logback-classic"),

  // Scala JSON library
  "com.typesafe.play" %% "play-json" % "2.10.5",

  // SLF4J API
  "org.slf4j" % "slf4j-api" % "2.0.12",

  // Scala library
  "org.scala-lang" % "scala-library" % "2.13.14",

  // MySQL connector for the database
  "mysql" % "mysql-connector-java" % "8.0.33",

  // Test dependencies
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "org.mockito" % "mockito-core" % "5.11.0" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test,
  "org.springframework.boot" % "spring-boot-starter-test" % "3.3.3" % Test
)

// Define the main class for running Spring Boot
Compile / mainClass := Some("com.strawary.dental_link.DentalLinkApplication")

// Assembly plugin to bundle everything into a single jar
assembly / assemblyOption := (assembly / assemblyOption).value.copy(includeScala = true)


// Merge strategy with proper handling for empty paths
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "spring.factories") => MergeStrategy.concat
  case PathList("META-INF", "spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports") => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) if xs.nonEmpty && xs.last == "MANIFEST.MF" => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) if xs.nonEmpty && xs.last == "spring.schemas" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) if xs.nonEmpty && xs.last == "spring.handlers" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

