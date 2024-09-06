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
  "org.springframework.boot" % "spring-boot-starter-data-jpa" % "3.3.3",
  "org.springframework.boot" % "spring-boot-starter-hateoas" % "3.3.3",
  "org.springframework.boot" % "spring-boot-starter-web" % "3.3.3",

  // Scala JSON library
  "com.typesafe.play" %% "play-json" % "2.10.0",

  // Lombok (optional)
  "org.projectlombok" % "lombok" % "1.18.26" % Optional,

  // Scala library
  "org.scala-lang" % "scala-library" % "2.13.14",

  // MySQL connector for the database
  "mysql" % "mysql-connector-java" % "8.0.33",

  // Test dependencies
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "org.mockito" % "mockito-core" % "3.6.28" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % Test,
  "org.springframework.boot" % "spring-boot-starter-test" % "3.3.3" % Test
)

// Define the main class for running Spring Boot
Compile / mainClass := Some("com.strawary.dental_link.DentalLinkApplication")

// Assembly plugin to bundle everything into a single jar
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

