package pl.milosz.moviedatabase.mapper;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;

public class MovieMapper {

    public static Movie toEntity(MovieDto movieDto) {
        return Movie.builder()
                .movieId(movieDto.getMovieId())
                .title(movieDto.getTitle())
                .releaseYear(movieDto.getReleaseYear())
                .category(Movie.Category.valueOf(movieDto.getCategory().name()))
                .description(movieDto.getDescription())
                .build();
    }

    public static MovieDto toDto(Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .category(MovieDto.Category.valueOf(movie.getCategory().name()))
                .description(movie.getDescription())
                .build();
    }
}
