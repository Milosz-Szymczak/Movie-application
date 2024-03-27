package pl.milosz.moviedatabase.dto;

import lombok.*;
import pl.milosz.moviedatabase.entity.Movie;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AwardDto {
    private Long awardId;
    private Movie movie;
    private String awardName;

}
