package pl.milosz.moviedatabase.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends ListCrudRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Movie> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
}
