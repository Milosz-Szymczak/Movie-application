package pl.milosz.moviedatabase.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JoinColumn(name = "movie")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Award> awards;

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
