package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.Optional;

public interface MovieService {
    Movie saveMovie(MovieDto movieDto);
}
