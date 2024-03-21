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
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.security.service.UserService;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.UserMovieRelationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private UserMovieRelationService userMovieRelationService;

    @Test
    void homePage_should_return_ok_status_and_correct_view() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("guest_html/home"));
    }

    @Test
    void addMoviePage_should_return_ok_status_and_correct_view() throws Exception {
        mockMvc.perform(get("/add-movie"))
                .andExpect(model().attribute("movie", new MovieDto()))
                .andExpect(model().attribute("category", MovieDto.Category.values()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add-movie"));
    }


    @Test
    void addMovieForm() throws Exception {
        MovieDto movieDto = new MovieDto();
        UserDto loggedUser = new UserDto();
        Movie savedMovie = new Movie();
        when(userService.findLoggedUser()).thenReturn(loggedUser);
        when(movieService.saveMovie(movieDto)).thenReturn(savedMovie);

        mockMvc.perform(post("/add-movie-form")
                        .flashAttr("movieDto", movieDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService).findLoggedUser();
        verify(movieService).saveMovie(movieDto);
        verify(userMovieRelationService).saveRelation(loggedUser, savedMovie);
    }
}