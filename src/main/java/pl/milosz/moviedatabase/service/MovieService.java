package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.List;

public interface MovieService {
    Movie saveMovie(MovieDto movieDto);
    Movie getMovieById(Long movieId);

    List<MovieDto> getAllMovies();

    List<MovieDto> getMoviesByCategory(Movie.Category category);

    List<MovieDto> searchMovies(String keyword);

    void updateMovie(MovieDto movieDto);
}
