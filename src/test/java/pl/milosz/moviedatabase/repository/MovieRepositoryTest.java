package pl.milosz.moviedatabase.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findByTitleContainingIgnoreCase() {
        String keyword = "test";
        Movie movie = Movie.builder().movieId(1L).category(Movie.Category.ACTION).releaseYear(2000).description("test").title("test").build();

        movieRepository.save(movie);

        List<Movie> moviesByKeyword = movieRepository.findByTitleContainingIgnoreCase(keyword);

        Assertions.assertThat(moviesByKeyword).isNotNull();
        Assertions.assertThat(moviesByKeyword.get(0).getTitle()).isEqualTo(keyword);
    }
}