FROM openjdk:17-slim
COPY ./target/fowu-consumer-1.0.0-SNAPSHOT-fat.jar consumer.jar
CMD ["java", "-jar", "consumer.jar"]
