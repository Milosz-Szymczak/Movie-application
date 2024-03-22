package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.entity.Award;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.mapper.AwardMapper;
import pl.milosz.moviedatabase.repository.AwardRepository;
import pl.milosz.moviedatabase.service.AwardService;

import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    public AwardServiceImpl(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    @Override
    public List<AwardDto> getAwards(Movie movie) {
        List<Award> allAward = awardRepository.findAll();
        return allAward.stream()
                .filter(c -> c.getMovie().getMovieId().equals(movie.getMovieId()))
                .map(AwardMapper::toDto)
                .toList();
    }

    @Override
    public void saveAward(AwardDto awardDto) {
        Award award = AwardMapper.toEntity(awardDto);
        awardRepository.save(award);
    }
}
