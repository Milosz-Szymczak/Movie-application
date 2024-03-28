package pl.milosz.moviedatabase.security.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.repository.UserRepository;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User admin;

    @BeforeEach
    public void init() {
        admin = User.builder().userId(1L).username("admin").password("admin").role(User.Role.ADMIN).build();
    }

    @Test
    void findByRole_should_ReturnUserByRole() {
        userRepository.save(admin);

        Optional<User> findUserByRole = userRepository.findByRole(admin.getRole());

        Assertions.assertThat(findUserByRole).isNotNull();
        Assertions.assertThat(findUserByRole.get().getRole()).isEqualTo(admin.getRole());
    }

    @Test
    void findByUsername_should_ReturnUserByUsername() {
        userRepository.save(admin);

        Optional<User> findByUsername = userRepository.findByUsername(admin.getUsername());

        Assertions.assertThat(findByUsername.get()).isEqualTo(admin);
        Assertions.assertThat(findByUsername).isNotNull();
    }
}