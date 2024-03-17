package pl.milosz.moviedatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
    private Long ratingId;
    private Movie movie;
    private User user;
    private Integer ratingOverall;
}
