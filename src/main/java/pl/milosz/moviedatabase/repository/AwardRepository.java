package pl.milosz.moviedatabase.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.Award;

@Repository
public interface AwardRepository extends ListCrudRepository<Award, Long> {
}
