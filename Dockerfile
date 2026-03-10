# Build stage
FROM eclipse-temurin:21-jdk AS build

# Install findutils (potrzebne do niektórych skryptów)
RUN apt-get update && apt-get install -y findutils && rm -rf /var/lib/apt/lists/*

# Ustawienie katalogu roboczego
WORKDIR /app

# Kopiowanie plików Gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY gradlew .
COPY gradle gradle

# Ustawienie prawa do wykonywania gradlew
RUN chmod +x ./gradlew

# Budowa aplikacji z wyłączeniem testów, bez Daemona, ograniczenie pamięci JVM
RUN ./gradlew build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m" --info --stacktrace

# Run stage (finalny obraz)
FROM eclipse-temurin:21-jdk

# Wolumen tymczasowy
VOLUME /tmp

# Kopiowanie JAR z etapu build
COPY --from=build /app/build/libs/*.jar app.jar

# Uruchomienie aplikacji
ENTRYPOINT ["java","-jar","/app.jar"]

# Port aplikacji
EXPOSE 8080