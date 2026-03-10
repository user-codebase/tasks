# Build stage
FROM eclipse-temurin:21-jdk AS build

# Install findutils
RUN apt-get update && apt-get install -y findutils && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY gradlew .
COPY gradle gradle

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# Run stage
FROM eclipse-temurin:21-jdk

VOLUME /tmp
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080