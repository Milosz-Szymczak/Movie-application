package pl.milosz.moviedatabase.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.entity.UserMovieRelation;
import pl.milosz.moviedatabase.mapper.UserMapper;
import pl.milosz.moviedatabase.repository.UserMovieRelationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMovieRelationImplTest {

    @Mock
    private UserMovieRelationRepository userMovieRelationRepository;

    @InjectMocks
    private UserMovieRelationImpl userMovieRelation;
    @Test
    void saveRelation_Should_SaveRelationInDatabase_and_LoggedUserIsNotNull() {
        UserDto loggedUser = UserDto.builder()
                .userId(1L)
                .username("test")
                .password("test")
                .role(String.valueOf(User.Role.USER))
                .build();
        Movie movie = new Movie();

        userMovieRelation.saveRelation(loggedUser, movie);

        verify(userMovieRelationRepository).save(any(UserMovieRelation.class));
        assertNotNull(loggedUser);
    }
}