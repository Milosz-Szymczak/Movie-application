package pl.milosz.moviedatabase.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_movie_relations")
public class UserMovieRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMovieRelationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

}
