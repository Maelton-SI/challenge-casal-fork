#JDK 17
FROM eclipse-temurin:17.0.12_7-jdk-jammy

COPY . /opt/vehicle-rental-api

WORKDIR /opt/vehicle-rental-api

#Building project
RUN chmod +x mvnw && ./mvnw clean install -DskipTests

#Running project
ENTRYPOINT ["java","-jar","target/vehicle-rental-api-1.0.0.jar"]