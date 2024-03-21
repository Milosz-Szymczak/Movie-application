package pl.milosz.moviedatabase.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.UserMovieRelation;


@Repository
public interface UserMovieRelationRepository extends ListCrudRepository<UserMovieRelation, Long> {
}
