# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/project-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8300

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
