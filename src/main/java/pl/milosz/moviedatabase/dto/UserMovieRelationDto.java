package pl.milosz.moviedatabase.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMovieRelationDto {
    private Long addedMovieId;
    private Long userId;
    private Long movieId;
}
