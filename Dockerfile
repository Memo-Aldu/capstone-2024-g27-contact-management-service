FROM maven:latest AS build
LABEL authors="senoussial"
WORKDIR /app
ARG CONTAINER_PORT
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-alpine
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
