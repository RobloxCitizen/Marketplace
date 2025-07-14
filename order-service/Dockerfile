FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y netcat-openbsd curl
WORKDIR /app
COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]