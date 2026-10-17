
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/Application_JobHubV2-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
