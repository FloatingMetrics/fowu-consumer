FROM maven:3.8.6-eclipse-temurin-17 as build
WORKDIR .
COPY . .
RUN mvn clean package

FROM openjdk:17-slim
COPY --from=build target/fowu-consumer-1.0.0-SNAPSHOT-fat.jar /consumer/
CMD ["java", "-jar", "consumer/fowu-consumer-1.0.0-SNAPSHOT-fat.jar"]
