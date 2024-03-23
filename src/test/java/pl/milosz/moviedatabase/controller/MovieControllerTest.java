package pl.milosz.moviedatabase.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.security.service.UserService;
import pl.milosz.moviedatabase.service.AwardService;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.RatingService;
import pl.milosz.moviedatabase.service.UserMovieRelationService;

import java.util.ArrayList;
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
    private UserService userService;

    @MockBean
    private AwardService awardService;

    @MockBean
    private UserMovieRelationService userMovieRelationService;

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
    void moviePageForm_should_ReturnViewWithMovie_and_Categories() throws Exception {
        mockMvc.perform(get("/add-movie"))
                .andExpect(model().attribute("movie", new MovieDto()))
                .andExpect(model().attribute("category", MovieDto.Category.values()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add-movie"));
    }


    @Test
    void movieForm_should_ReturnCorrectView() throws Exception {
        MovieDto movieDto = new MovieDto();
        UserDto loggedUser = new UserDto();
        Movie savedMovie = new Movie();
        savedMovie.setMovieId(1L);

        when(userService.findLoggedUser()).thenReturn(loggedUser);
        when(movieService.saveMovie(movieDto)).thenReturn(savedMovie);

        mockMvc.perform(post("/add-movie-form")
                        .flashAttr("movieDto", movieDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-award?movieId=" + savedMovie.getMovieId()));

        verify(userService).findLoggedUser();
        verify(movieService).saveMovie(movieDto);
        verify(userMovieRelationService).saveRelation(loggedUser, savedMovie);
    }

    @Test
    void awardPageForm_should_ReturnCorrectView() throws Exception {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);

        List<AwardDto> award = new ArrayList<>();
        award.add(AwardDto.builder().awardName("test").build());

        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(awardService.getAwards(movie)).thenReturn(award);

        mockMvc.perform(get("/add-award")
                        .param("movieId", String.valueOf(movieId)))
                .andExpect(model().attribute("movie", movie))
                .andExpect(model().attribute("award", new AwardDto()))
                .andExpect(model().attribute("awardsMovie", award))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add-award"));

        verify(movieService).getMovieById(movieId);
        verify(awardService).getAwards(movie);
    }

    @Test
    void awardForm_should_ReturnCorrectView() throws Exception {
        Long movieId = 1L;
        Movie mockMovie = new Movie();
        when(movieService.getMovieById(movieId)).thenReturn(mockMovie);
        doNothing().when(awardService).saveAward(new AwardDto());

        mockMvc.perform(post("/add-award-form/{movieId}", movieId)
                        .flashAttr("awardDto", new AwardDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-award?movieId=" + movieId));

        verify(movieService).getMovieById(movieId);
        verify(awardService).saveAward(any(AwardDto.class));
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
    void processMovieRatingRequest_should_RedirectToMoviePage() throws Exception {
        //given
        Long movieId = 1L;
        UserDto userDto = UserDto.builder().userId(2L).build();
        Movie movie = new Movie();

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(ratingService.hasUserRatedMovie(movieId, userDto)).thenReturn(false);
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        mockMvc.perform(post("/rate/{movieId}", movieId)
                        .param("rating", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movie/1"));

    }
}