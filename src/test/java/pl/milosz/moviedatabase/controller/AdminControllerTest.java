package pl.milosz.moviedatabase.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.service.AwardService;
import pl.milosz.moviedatabase.service.MovieService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private AwardService awardService;

    @Test
    void managePageTest() throws Exception {
        // Given
        List<MovieDto> movies = new ArrayList<>();
        movies.add(MovieDto.builder().title("test").category(MovieDto.Category.ACTION).build());


        // When
        when(movieService.getAllMovies()).thenReturn(movies);

        // Then
        mockMvc.perform(get("/manage-movie"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/manage-movie"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", movies));
    }

    @Test
    void updateMoviePage() throws Exception {
        // Given
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        List<AwardDto> award = new ArrayList<>();
        award.add(AwardDto.builder().awardName("test").build());

        // When
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(awardService.getAwards(any(Movie.class))).thenReturn(award);

        //Then
        mockMvc.perform(get("/update-movie/{movieId}", movieId))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/update-movie"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", movie))
                .andExpect(model().attributeExists("awardsMovie"))
                .andExpect(model().attribute("awardsMovie", award))
                .andExpect(model().attributeExists("award"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    void updateMovieForm() throws Exception {
        // Given
        MovieDto movieDto = MovieDto.builder().movieId(1L).title("test").category(MovieDto.Category.ACTION).build();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/update-movie-form")
                .flashAttr("movieDto", movieDto)
                .header("referer", "/update-movie/" + movieDto.getMovieId());

        // When
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-movie/" + movieDto.getMovieId()));

        // Then
        verify(movieService).updateMovie(movieDto);
    }

    @Test
    void updateAwardForm() throws Exception {
        // Given
        long movieId = 1L;
        Long awardId = 1L;
        String updatedAwardName = "Updated Award Name";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/update-award-form/" + awardId)
                .param("updateAward", updatedAwardName)
                .header("referer", "/update-movie/" + movieId);

        // When
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-movie/" + movieId));

        // Then
        verify(awardService).updateAward(awardId, updatedAwardName);
    }

    @Test
    void deleteAward() throws Exception {
        // Given
        long movieId = 1L;
        Long awardId = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delete-award-form/" + awardId)
                .header("referer", "/update-movie/" + movieId);

        // When
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-movie/" + movieId));

        // Then
        verify(awardService).deleteAward(awardId);
    }

    @Test
    void awardForm() throws Exception {
        // Given
        Movie movie = Movie.builder().movieId(1L).title("test").category(Movie.Category.ACTION).build();
        Long movieId = 1L;
        AwardDto awardDto = AwardDto.builder().awardId(1L).awardName("test").movie(movie).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-award-admin/" + movieId)
                .flashAttr("awardDto", awardDto)
                .header("referer", "/update-movie/" + movieId);


        // When
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/update-movie/" + movieId));

        // Then
        verify(awardService, times(1)).saveAward(awardDto);
    }
}