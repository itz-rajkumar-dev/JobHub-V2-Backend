
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/Category_JobHubV2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
