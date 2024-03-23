package pl.milosz.moviedatabase.dto;

import lombok.*;
import pl.milosz.moviedatabase.entity.Award;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.Rating;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class MovieDto {
    private Long movieId;
    private String title;
    private Integer releaseYear;
    private Category category;
    private String description;
    private List<Long> ratingIds;
    private List<Long> awardIds;

    @Getter
    public enum Category {
        ACTION("Action"),
        DRAMA("Drama"),
        COMEDY("Comedy"),
        HORROR("Horror"),
        SCI_FI("Sci-Fi"),
        FANTASY("Fantasy"),
        ANIMATION("Animation"),
        THRILLER("Thriller"),
        ROMANCE("Romance"),
        OTHER("Other");

        private final String name;

        Category(String name) {
            this.name = name;
        }
    }
}
