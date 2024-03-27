package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;

public interface UserMovieRelationService {
    void saveRelation(UserDto loggedUser, Movie movie);
}
