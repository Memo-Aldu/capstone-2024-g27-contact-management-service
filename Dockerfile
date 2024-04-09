# Use the latest version of Maven as a build image
FROM maven:latest AS build

# Add metadata to the image to describe that the container is maintained by the following authors
LABEL authors="senoussial"

# Set the working directory in the container to /app
WORKDIR /app

# Define the argument for the container port
ARG CONTAINER_PORT

# Copy the project's pom.xml (Project Object Model) file to the /app directory in the container
COPY pom.xml /app

# Resolve the project's dependencies
RUN mvn dependency:resolve

# Copy the rest of the project to the /app directory in the container
COPY . /app

# Clean the project and remove test files
RUN mvn clean

# Build the project and skip the tests
RUN mvn clean package -DskipTests

# Use the OpenJDK 21 image for the runtime stage
FROM openjdk:21-jdk

# Set the working directory in the container to /opt/app
WORKDIR /opt/app

# Copy the built JAR file from the build stage to the /opt/app directory in the runtime container
COPY --from=build /app/target/contact-management-service-0.0.1-SNAPSHOT.jar contact-management-service-0.0.1-SNAPSHOT.jar

# Expose port 8080 for the application
EXPOSE 8080

# Define the command to start the application
ENTRYPOINT ["java", "-jar", "contact-management-service-0.0.1-SNAPSHOT.jar"]