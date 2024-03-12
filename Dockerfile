
FROM maven:3.8.1 AS build

# Add Maintainer Info
LABEL authors="senoussial"

LABEL MAINTAINER="senoussi08@gmail.com"

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-alpine
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
