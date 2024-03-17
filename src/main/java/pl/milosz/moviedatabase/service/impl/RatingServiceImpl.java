package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.repository.RatingRepository;
import pl.milosz.moviedatabase.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
}
