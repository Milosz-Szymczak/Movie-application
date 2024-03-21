package pl.milosz.moviedatabase.security.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.milosz.moviedatabase.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByRole(User.Role role);
}
