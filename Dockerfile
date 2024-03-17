FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/movie-database.jar /app/movie-database.jar
ENTRYPOINT ["java", "-jar", "movie-database.jar"]