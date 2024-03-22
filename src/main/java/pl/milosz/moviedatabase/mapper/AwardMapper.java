package pl.milosz.moviedatabase.mapper;

import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.entity.Award;

public class AwardMapper {
    public static AwardDto toDto(Award award) {
        return AwardDto.builder()
                .awardId(award.getAwardId())
                .movie(award.getMovie())
                .awardName(award.getAwardName())
                .build();
    }

    public static Award toEntity(AwardDto awardDto) {
        return Award.builder()
                .awardId(awardDto.getAwardId())
                .movie(awardDto.getMovie())
                .awardName(awardDto.getAwardName())
                .build();
    }
}
