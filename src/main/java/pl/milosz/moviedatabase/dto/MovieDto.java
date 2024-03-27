package pl.milosz.moviedatabase.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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
