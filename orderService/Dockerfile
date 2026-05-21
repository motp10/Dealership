FROM gradle:8-jdk21-alpine AS builder
WORKDIR /app

RUN apk add --no-cache libc6-compat

COPY settings.gradle build.gradle ./
COPY commonLib ./commonLib
COPY orderService ./orderService

RUN gradle :orderService:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/orderService/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]