# Build stage
FROM openjdk:21 AS build

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
FROM openjdk:21

VOLUME /tmp
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080