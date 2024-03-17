package pl.milosz.moviedatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.milosz.moviedatabase.entity.Movie;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AwardDto {
    private Long awardId;
    private Movie movie;
    private String awardName;

}
