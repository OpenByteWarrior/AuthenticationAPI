FROM eclipse-temurin:17-jdk-alpine

LABEL authors="OPENBYTEWARRIOR"

ENTRYPOINT ["top", "-b"]

WORKDIR /app

COPY build/libs/AuthenticationAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
