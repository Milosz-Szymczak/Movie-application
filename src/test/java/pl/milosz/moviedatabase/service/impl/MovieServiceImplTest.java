package pl.milosz.moviedatabase.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void saveMovie() {
        MovieDto movie = MovieDto.builder().movieId(1L).category(MovieDto.Category.ACTION).title("test").build();;

        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());
        movieService.saveMovie(movie);

        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void getMovieById_should_ReturnMovieObject() {
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();;

        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
        Movie movieById = movieService.getMovieById(movie.getMovieId());

        Assertions.assertThat(movieById).isEqualTo(movie);
        verify(movieRepository).findById(movie.getMovieId());

    }

    @Test
    void getMovieById_should_ThrowNoSuchElementException() {
        Movie movieNotExist = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();;

        when(movieRepository.findById(movieNotExist.getMovieId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> movieService.getMovieById(movieNotExist.getMovieId()));

    }

    @Test
    void getAllMovies_should_ReturnMovieDtoList() {
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();
        List<Movie> movies = List.of(movie);

        when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> allMovies = movieService.getAllMovies();

        Assertions.assertThat(allMovies).isNotNull();
        verify(movieRepository).findAll();
    }

    @Test
    void getMoviesByCategory_should_ReturnMoviesFilteredByCategory() {
        //Given
        Movie.Category category = Movie.Category.ACTION;
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();
        Movie secondMovie = Movie.builder().movieId(2L).category(Movie.Category.ACTION).title("test2").build();
        List<Movie> movies = Arrays.asList(movie, secondMovie);

        //When
        when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> result = movieService.getMoviesByCategory(category);

        //Then
        assertEquals(2, result.size());
        assertEquals("test", result.get(0).getTitle());
        assertEquals("test2", result.get(1).getTitle());
    }

    @Test
    void getMoviesByCategory_should_ReturnEmptyListWhenNoMoviesFound() {
        //Given
        Movie.Category category = Movie.Category.ACTION;
        List<Movie> movies = List.of();


        //When
        when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> result = movieService.getMoviesByCategory(category);

        //Then
        assertEquals(0, result.size());
    }

    @Test
    void searchMovies_should_ReturnMoviesBasedOnKeyword() {
        String keyword = "test";
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();
        Movie secondMovie = Movie.builder().movieId(2L).category(Movie.Category.ACTION).title("test").build();
        List<Movie> searchResults = Arrays.asList(movie, secondMovie);

        when(movieRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(searchResults);

        List<MovieDto> result = movieService.searchMovies(keyword);
        assertEquals(2, result.size());
        assertEquals("test", result.get(0).getTitle());
        assertEquals("test", result.get(1).getTitle());
    }

    @Test
    void searchMovies_should_ReturnEmptyListWhenNoMoviesFound() {
        String keyword = "notExisting";
        List<Movie> searchResults = List.of();

        when(movieRepository.findByTitleContainingIgnoreCase(keyword)).thenReturn(searchResults);

        List<MovieDto> result = movieService.searchMovies(keyword);
        assertEquals(0, result.size());
    }
}