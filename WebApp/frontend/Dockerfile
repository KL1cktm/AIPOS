FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/frontend-0.0.1-SNAPSHOT.jar frontend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "frontend.jar"]
