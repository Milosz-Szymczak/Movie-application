package pl.milosz.moviedatabase.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.Rating;

@Repository
public interface RatingRepository extends ListCrudRepository<Rating, Long> {
}
