# --- ЭТАП СБОРКИ ---
FROM gradle:8-jdk21-alpine AS builder
WORKDIR /app

# Копируем файлы настроек Gradle из КОРНЯ проекта
# (Поскольку context: .., Docker видит файлы в корне)
COPY settings.gradle build.gradle ./
COPY commonLib ./commonLib
COPY orderService ./orderService

# Собираем конкретный модуль
RUN gradle :orderService:bootJar --no-daemon

# --- ЭТАП ЗАПУСКА ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем JAR из папки билда модуля
COPY --from=builder /app/orderService/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]