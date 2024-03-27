package pl.milosz.moviedatabase.service;

import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.List;

public interface AwardService {
    List<AwardDto> getAwards(Movie movie);

    void saveAward(AwardDto awardDto);

    void deleteAward(Long awardId);

    void updateAward(Long awardId, String updatedAwardName);
}
