# Movie Application
This project is designed to store videos added by users. The application includes the ability to register a new user. Users can add videos, search for videos of interest and rate them. The administrator can modify and delete videos.
## Table of contents
* [Technologies](#Technologies)
* [Functionalities of Movie Database App](#Functionalities-of-Movie-Database-App)
* [Setup](#Setup)
* [Screenshots](#Screenshots)

## Technologies
This is a web application built with:
* Java 17
* Spring Boot 3.1.0
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JUnit
* Mockito
* Thymeleaf
* HTML
* CSS
* Bootstrap
* Docker

## Functionalities of Movie Database App
* Login
* Registration
* Adding movie and awards
* Viewing movie
* Filtering movies by categories
* Search by title
* Administrator can modify and delete movies and adwards

## Setup
Steps to launch this project.
```bash
git clone https://github.com/Milosz-Szymczak/movies-database.git
cd movies-database
mvnw package
docker-compose build
docker-compose up
```
You can then access the Movie Application at http://localhost:8080/.

#### Administrator login details:
Login: admin <br/>
Password: password

## Screenshots

### Home page:
![home](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/8daff6aa-6de9-4b8e-986a-97681c6c8698)
![movie-page](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/0eeda5f8-bf01-4ca1-a44a-a67d3f1461a5)

### User view:

#### Add Movie
![add-movie](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/91382363-224f-4e8b-bfd6-0a997edfb039)

#### Add Award
![add-award](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/3304ec70-ed16-44b2-83dd-402bbc7c0f2c)

#### User profile
![my-profile](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/c7dff2b4-c14f-4b1f-8562-c6db0aa57e35)



### Administrator view:

#### Management Movies
![movie-management](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/a861c81b-b68c-4e11-8e63-e5162820b26d)


#### Update Movies
![update-movie](https://github.com/Milosz-Szymczak/movies-database/assets/99685108/7823cc45-3600-4a52-bdfb-d36babd06610)



