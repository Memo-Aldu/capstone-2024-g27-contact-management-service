FROM maven:latest AS build
LABEL authors="senoussial"
WORKDIR /app
ARG CONTAINER_PORT
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /opt/app
COPY --from=build /app/target/contact-management-service-0.0.1-SNAPSHOT.jar contact-management-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "contact-management-service-0.0.1-SNAPSHOT.jar"]
