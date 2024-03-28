package pl.milosz.moviedatabase.dto;

import lombok.*;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.User;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
    private Long ratingId;
    private Movie movie;
    private User user;
    private Integer ratingOverall;
}
