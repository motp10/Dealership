FROM gradle:8-jdk21 AS builder
WORKDIR /app

COPY settings.gradle build.gradle ./
COPY commonLib ./commonLib
COPY storageService ./storageService

RUN gradle :storageService:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/storageService/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]