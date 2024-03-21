package pl.milosz.moviedatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMovieRelationDto {
    private Long addedMovieId;
    private Long userId;
    private Long movieId;
}
