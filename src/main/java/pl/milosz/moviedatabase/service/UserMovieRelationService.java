package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.Optional;

public interface UserMovieRelationService {
    void saveRelation(UserDto loggedUser, Movie movie);
}
