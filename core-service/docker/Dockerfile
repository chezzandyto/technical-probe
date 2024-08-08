FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY .. .
RUN mvn clean package

FROM openjdk:17-alpine
MAINTAINER Andres Toapanta "andres_toapanta@hotmail.com"
ENV LANG C.UTF-8
ENV TZ America/Guayaquil
EXPOSE 8000
WORKDIR /api
COPY --from=build /app/target/*.jar /api/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]