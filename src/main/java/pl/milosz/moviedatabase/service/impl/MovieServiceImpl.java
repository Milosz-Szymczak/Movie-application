package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.repository.MovieRepository;
import pl.milosz.moviedatabase.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
