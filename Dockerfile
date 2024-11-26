FROM maven:latest AS build
WORKDIR /app
ARG CONTAINER_PORT
ENV MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED"
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /app/target/contact-management-service-0.0.1-SNAPSHOT.jar contact-management-service.jar
EXPOSE ${CONTAINER_PORT}
ENTRYPOINT ["java","-jar","contact-management-service.jar"]