FROM gradle:7-jdk17 AS builder
WORKDIR /app

# Copy gradle wrapper and settings first to leverage cache (if you have a wrapper)
COPY gradle gradle
COPY gradlew .
COPY build.gradle settings.gradle ./

# Copy sources
COPY src ./src

# Ensure wrapper is executable (if using wrapper)
RUN chmod +x ./gradlew || true

# Build the Spring Boot executable jar (use wrapper if available)
# Use bootJar to guarantee an executable Spring Boot jar is produced.
RUN ./gradlew clean bootJar --no-daemon || gradle clean bootJar --no-daemon


# Stage 2 — runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the produced jar (wildcard is safer than hard-coding the name)
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]