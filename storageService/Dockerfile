# --- ЭТАП СБОРКИ ---
FROM gradle:8-jdk21-alpine AS builder
WORKDIR /app

# Копируем файлы настроек Gradle из КОРНЯ проекта
COPY settings.gradle build.gradle ./
COPY commonLib ./commonLib
COPY storageService ./storageService

# Собираем конкретный модуль
RUN gradle :storageService:bootJar --no-daemon

# --- ЭТАП ЗАПУСКА ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем JAR из папки билда модуля
COPY --from=builder /app/storageService/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]