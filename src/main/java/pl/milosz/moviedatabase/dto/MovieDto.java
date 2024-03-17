package pl.milosz.moviedatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.milosz.moviedatabase.entity.Award;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.Rating;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Long movieId;
    private String title;
    private Integer releaseYear;
    private Movie.Category category;
    private String description;
    private List<Rating> ratings;
    private List<Award> awards;
}
