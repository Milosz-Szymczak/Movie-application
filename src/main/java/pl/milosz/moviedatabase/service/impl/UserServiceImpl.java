package pl.milosz.moviedatabase.service.impl;

import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.repository.UserRepository;
import pl.milosz.moviedatabase.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
