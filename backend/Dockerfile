FROM gradle:8.4-jdk21 AS build
WORKDIR /app

COPY . .

RUN gradle bootJar --no-daemon

# Runtime image
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]