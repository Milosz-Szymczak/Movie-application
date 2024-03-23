package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;

public interface RatingService {
    void saveRating(Movie movieById, int rating, UserDto loggedUser);

    String getOverallRatingForMovieById(Long movieId);

    boolean hasUserRatedMovie(Long movieId, UserDto loggedUser);
}
