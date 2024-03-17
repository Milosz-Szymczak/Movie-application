package pl.milosz.moviedatabase.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.Movie;

@Repository
public interface MovieRepository extends ListCrudRepository<Movie, Long> {
}
