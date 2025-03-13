FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "backend.jar"]
