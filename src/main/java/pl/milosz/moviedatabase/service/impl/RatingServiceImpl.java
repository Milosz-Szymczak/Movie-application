package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.Rating;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.mapper.UserMapper;
import pl.milosz.moviedatabase.repository.RatingRepository;
import pl.milosz.moviedatabase.service.RatingService;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void saveRating(Movie movieById, int rating, UserDto loggedUser) {
        User entity = UserMapper.toEntity(loggedUser);
        Rating build = Rating.builder().movie(movieById).ratingOverall(rating).user(entity).build();
        ratingRepository.save(build);
    }

    @Override
    public String getOverallRatingForMovieById(Long movieId) {
        final String EmptyAverageRating = "0";

        OptionalDouble averageRating = ratingRepository.findAll().stream()
                .filter(rating -> rating.getMovie().getMovieId().equals(movieId))
                .mapToDouble(Rating::getRatingOverall).average();

        if (averageRating.isPresent()) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            return decimalFormat.format(averageRating.getAsDouble());
        } else {
            return EmptyAverageRating;
        }
    }

    @Override
    public boolean hasUserRatedMovie(Long movieId, UserDto loggedUser) {
        User entityUser = UserMapper.toEntity(loggedUser);

        long count = ratingRepository.findAll().stream()
                .filter(r -> r.getUser().equals(entityUser) && r.getMovie().getMovieId().equals(movieId))
                .count();
        return count > 0;
    }
}
