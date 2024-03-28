package pl.milosz.moviedatabase.security.service;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    Optional<User> checkAdminExist();

    UserDto findLoggedUser();
}
