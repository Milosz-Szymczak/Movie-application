FROM openjdk:17-oracle
COPY target/movie-database.jar movie-database.jar
ENTRYPOINT ["java", "-jar", "movie-database.jar"]