package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.UserMovieRelation;

import java.util.List;

public interface UserMovieRelationService {
    void saveRelation(UserDto loggedUser, Movie movie);

    List<MovieDto> getMoviesAddedByUser(UserDto loggedUser);
}
