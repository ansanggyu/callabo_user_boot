FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/callabo_user_boot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
