package pl.milosz.moviedatabase.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.Rating;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.mapper.UserMapper;
import pl.milosz.moviedatabase.repository.RatingRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    void saveRating_should_SaveRating() {
        //given
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();;
        User loggedUser = User.builder().username("test").role(User.Role.USER).build();
        UserDto userDto = UserMapper.toDto(loggedUser);
        int rating = 5;
        Rating build = Rating.builder().movie(movie).ratingOverall(rating).user(loggedUser).build();

        //when
        when(ratingRepository.save(build)).thenReturn(build);
        ratingService.saveRating(movie, rating, userDto);

        //then
        verify(ratingRepository).save(build);
    }

    @Test
    void getOverallRatingForMovieById_should_ReturnAverageRating_WhenRatingsExist() {
        //given
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();;
        User loggedUser = User.builder().username("test").role(User.Role.USER).build();
        Rating rating = Rating.builder().movie(movie).user(loggedUser).ratingOverall(3).build();
        List<Rating> ratings = List.of(rating,rating);
        String expectedRating = "3,0";

        //when
        when(ratingRepository.findAll()).thenReturn(ratings);
        String actualRating = ratingService.getOverallRatingForMovieById(movie.getMovieId());

        //then
        assertEquals(expectedRating, actualRating);
        verify(ratingRepository).findAll();
    }

    @Test
    void getOverallRatingForMovieById_should_ReturnString0_WhenNoRatingsExist() {
        //given
        Long movieId = 1L;
        List<Rating> ratings = new ArrayList<>();
        String expectedRating = "0";

        //when
        when(ratingRepository.findAll()).thenReturn(ratings);
        String actualRating = ratingService.getOverallRatingForMovieById(movieId);

        //then
        assertEquals(expectedRating, actualRating);
        verify(ratingRepository).findAll();
    }

    @Test
    void hasUserRatedMovie_should_ReturnTrue_WhenUserHasRated() {
        //given
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).title("test").build();
        User loggedUser = User.builder().username("test").role(User.Role.USER).build();
        UserDto userDto = UserMapper.toDto(loggedUser);

        Rating rating = Rating.builder().movie(movie).user(loggedUser).ratingOverall(3).build();
        List<Rating> ratings = List.of(rating,rating);

        //when
        when(ratingRepository.findAll()).thenReturn(ratings);
        boolean userHasRatedMovie = ratingService.hasUserRatedMovie(movie.getMovieId(), userDto);

        //then
        assertTrue(userHasRatedMovie);
        verify(ratingRepository).findAll();
    }

    @Test
    void hasUserRatedMovie_should_ReturnFalse_WhenUserHasNotRated() {
        //given
        Movie movie = Movie.builder().movieId(2L).category(Movie.Category.ACTION).title("test").build();
        User loggedUser = User.builder().username("test").role(User.Role.USER).build();
        UserDto userDto = UserMapper.toDto(loggedUser);
        userDto.setUsername("withoutRating");

        Rating rating = Rating.builder().movie(movie).user(loggedUser).ratingOverall(3).build();
        List<Rating> ratings = List.of(rating,rating);

        //when
        when(ratingRepository.findAll()).thenReturn(ratings);
        boolean userHasRatedMovie = ratingService.hasUserRatedMovie(movie.getMovieId(), userDto);

        //then
        assertFalse(userHasRatedMovie);
        verify(ratingRepository).findAll();
    }
}