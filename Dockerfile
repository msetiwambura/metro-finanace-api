# Use Java 21 JDK as base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/metro-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/config/application.properties

ENV SPRING_CONFIG_LOCATION=file:/app/config/

# Expose port (adjust if needed)
EXPOSE 2022

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
