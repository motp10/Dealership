# syntax=docker/dockerfile:1
FROM gradle:8.14.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .
RUN gradle --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring --create-home spring

COPY --from=build /home/gradle/project/build/libs/dealership.jar /app/dealership.jar

USER spring:spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/dealership.jar"]