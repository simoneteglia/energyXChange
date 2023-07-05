# Stage 1: Build the application JAR
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final container image
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/EnergyXChange-0.0.1-SNAPSHOT.jar ./EnergyXChange.jar

# Expose the port on which your Spring application listens
EXPOSE 8080

# Run the Spring application when the container starts
ENTRYPOINT ["java", "-jar", "EnergyXChange.jar"]
