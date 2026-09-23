FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/dealership.jar /app/dealership.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/dealership.jar"]
