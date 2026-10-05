FROM gradle:8.10.2-jdk21 AS build
WORKDIR /workspace

COPY ./gradlew ./
COPY ./gradle ./gradle
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradle.properties ./
COPY ./src ./src

RUN chmod +x ./gradlew && ./gradlew --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring --create-home spring

COPY --from=build /workspace/build/libs/dealership.jar /app/dealership.jar

USER spring:spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/dealership.jar"]