package pl.milosz.moviedatabase.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.RatingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private RatingService ratingService;

    @Test
    void homePage_should_ReturnViewWithMovies() throws Exception {
        List<MovieDto> movies = new ArrayList<>();
        movies.add(MovieDto.builder().title("test").category(MovieDto.Category.ACTION).build());

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("movies", movies))
                .andExpect(view().name("guest/home"));

        verify(movieService).getAllMovies();
    }

    @Test
    void moviePage_should_ReturnCorrectMovieViewUsingMovieId() throws Exception {
        //given
        Long movieId = 1L;
        Movie movie = Movie.builder().movieId(movieId).title("test").category(Movie.Category.ACTION).build();
        String ratingOverall = "3.0";

        //when
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(ratingService.getOverallRatingForMovieById(movieId)).thenReturn(ratingOverall);

        //then
        mockMvc.perform(get("/movie/{movieId}", movieId))
                .andExpect(model().attribute("movie", movie))
                .andExpect(model().attribute("ratingOverall", ratingOverall))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/movie"));

        verify(movieService).getMovieById(movieId);
        verify(ratingService).getOverallRatingForMovieById(movieId);
    }

    @Test
    void getFilteredMovies_should_ReturnFilteredMovies_WhenCategorySelected() throws Exception {
        //Given
        String selectedCategory = String.valueOf(Movie.Category.ACTION);
        MovieDto movie = MovieDto.builder().movieId(1L).title("test").category(MovieDto.Category.ACTION).build();

        List<MovieDto> expectedMovies = Arrays.asList(movie, movie);

        //When
        when(movieService.getMoviesByCategory(Movie.Category.ACTION)).thenReturn(expectedMovies);

        //Then
        mockMvc.perform(get("/selectCategory")
                        .param("category", selectedCategory))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/home"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", expectedMovies))
                .andExpect(model().attributeExists("categories"));
        verify(movieService, times(1)).getMoviesByCategory(Movie.Category.ACTION);
    }

    @Test
    void getFilteredMovies_should_ReturnAllMovies_WhenNoCategorySelected() throws Exception {
        // Given
        MovieDto movie = MovieDto.builder().movieId(1L).title("test").category(MovieDto.Category.ACTION).build();
        List<MovieDto> expectedMovies = Arrays.asList(movie, movie);

        // When
        when(movieService.getAllMovies()).thenReturn(expectedMovies);

        //Then
        mockMvc.perform(get("/selectCategory"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest/home"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", expectedMovies))
                .andExpect(model().attributeExists("categories"));
        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void searchMovies_shouldReturnMoviesBasedOnKeyword() throws Exception {
        //given
        String keyword = "test";
        MovieDto movie = MovieDto.builder().movieId(1L).title("test").category(MovieDto.Category.ACTION).build();
        List<MovieDto> expectedMovies = Arrays.asList(movie, movie);

        //when
        when(movieService.searchMovies(keyword)).thenReturn(expectedMovies);

        //then
        mockMvc.perform(get("/search")
                        .param("keyword", keyword))
                        .andExpect(status().isOk())
                        .andExpect(view().name("guest/home"))
                        .andExpect(model().attribute("movies", expectedMovies));

        verify(movieService).searchMovies(keyword);

    }
}