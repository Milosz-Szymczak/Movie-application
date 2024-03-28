package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.entity.UserMovieRelation;
import pl.milosz.moviedatabase.mapper.MovieMapper;
import pl.milosz.moviedatabase.mapper.UserMapper;
import pl.milosz.moviedatabase.repository.UserMovieRelationRepository;
import pl.milosz.moviedatabase.service.UserMovieRelationService;

import java.util.List;

@Service
public class UserMovieRelationImpl implements UserMovieRelationService {
    private final UserMovieRelationRepository userMovieRelationRepository;


    public UserMovieRelationImpl(UserMovieRelationRepository userMovieRelationRepository) {
        this.userMovieRelationRepository = userMovieRelationRepository;
    }

    @Override
    public void saveRelation(UserDto loggedUser, Movie movie) {
        User entity = UserMapper.toEntity(loggedUser);

        UserMovieRelation build = UserMovieRelation.builder()
                .user(entity)
                .movie(movie)
                .build();
        userMovieRelationRepository.save(build);
    }

    @Override
    public List<MovieDto> getMoviesAddedByUser(UserDto loggedUser) {
        return userMovieRelationRepository.findAll().stream()
                .filter(user -> user.getUser().getUserId().equals(loggedUser.getUserId()))
                .map(UserMovieRelation::getMovie)
                .map(MovieMapper::toDto)
                .toList();
    }
}
