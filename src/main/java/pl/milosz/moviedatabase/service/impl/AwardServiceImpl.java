package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.repository.AwardRepository;
import pl.milosz.moviedatabase.service.AwardService;

@Service
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    public AwardServiceImpl(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }
}
