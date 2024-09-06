# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by sbt-assembly to the working directory
COPY target/scala-2.13/dental-link-assembly-0.0.1-SNAPSHOT.jar /app/dental-link.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/dental-link.jar"]