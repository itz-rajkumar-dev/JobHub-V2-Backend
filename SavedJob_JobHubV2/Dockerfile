
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/SavedJob_JobHubV2-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
