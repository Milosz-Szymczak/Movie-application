package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.mapper.MovieMapper;
import pl.milosz.moviedatabase.repository.MovieRepository;
import pl.milosz.moviedatabase.service.MovieService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie saveMovie(MovieDto movieDto) {
        Movie movie = MovieMapper.toEntity(movieDto);
        return movieRepository.save(movie);
    }

}
