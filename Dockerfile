# Build stage
FROM openjdk:21-jdk-slim AS build

# Install findutils
RUN apt-get update && apt-get install -y findutils && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY gradlew .
COPY gradle gradle

# Make gradlew executable
RUN chmod +x ./gradlew

# Build the project (skip tests)
RUN ./gradlew build -x test

# Run stage
FROM openjdk:21-jdk-slim

# Create tmp volume
VOLUME /tmp

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose application port
EXPOSE 8080