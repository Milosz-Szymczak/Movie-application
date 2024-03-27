package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.mapper.MovieMapper;
import pl.milosz.moviedatabase.repository.MovieRepository;
import pl.milosz.moviedatabase.service.MovieService;

import java.util.List;
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

    @Override
    public Movie getMovieById(Long movieId) {
        Optional<Movie> movieById = movieRepository.findById(movieId);
        return movieById.orElseThrow(() -> new NoSuchElementException("Movie not found with ID: " + movieId));
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(MovieMapper::toDto).toList();
    }

    @Override
    public List<MovieDto> getMoviesByCategory(Movie.Category category) {
        return movieRepository.findAll().stream()
                .filter(m -> m.getCategory().equals(category))
                .map(MovieMapper::toDto).toList();
    }

    @Override
    public List<MovieDto> searchMovies(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(MovieMapper::toDto)
                .toList();
    }
}
