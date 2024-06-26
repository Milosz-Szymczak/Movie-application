package pl.milosz.moviedatabase.security.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.exception.UserNotFoundException;
import pl.milosz.moviedatabase.mapper.UserMapper;
import pl.milosz.moviedatabase.repository.UserRepository;


import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @Override
    public UserDto findLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(user);

        if (userOptional.isPresent()){
            return UserMapper.toDto(userOptional.get());
        }else {
            throw new UserNotFoundException("User not found in database");
        }
    }

    @Override
    public void saveUser(User user) {
        user.setRole(User.Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> checkAdminExist() {
        return userRepository.findByRole(pl.milosz.moviedatabase.entity.User.Role.ADMIN);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Optional<pl.milosz.moviedatabase.entity.User> adminExist = checkAdminExist();

            if (adminExist.isEmpty()) {
                pl.milosz.moviedatabase.entity.User admin = pl.milosz.moviedatabase.entity.User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password"))
                        .role(pl.milosz.moviedatabase.entity.User.Role.ADMIN)
                        .build();
                userRepository.save(admin);

                UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(admin.getUsername())
                        .password(admin.getPassword())
                        .roles(String.valueOf(admin.getRole()))
                        .build();
                inMemoryUserDetailsManager.createUser(userDetails);
            }
        };
    }
}
