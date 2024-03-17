package pl.milosz.moviedatabase.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
}
