package pl.milosz.moviedatabase.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.exception.UserNotFoundException;
import pl.milosz.moviedatabase.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser_ValidUser_SuccessfullySaved() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        userService.saveUser(user);

        verify(userRepository).save(user);
        assertEquals(User.Role.USER, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<User> userList = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(userList.size(), result.size());
        assertTrue(result.containsAll(userList));
    }

    @Test
    void checkAdminExist_AdminExists_ReturnsAdminUser() {
        User adminUser = new User();
        adminUser.setRole(User.Role.ADMIN);
        when(userRepository.findByRole(User.Role.ADMIN)).thenReturn(Optional.of(adminUser));

        Optional<User> result = userService.checkAdminExist();

        assertTrue(result.isPresent());
        assertEquals(adminUser, result.get());
    }

    @Test
    void checkAdminExist_NoAdminExists_ReturnsEmptyOptional() {
        when(userRepository.findByRole(User.Role.ADMIN)).thenReturn(Optional.empty());

        Optional<User> result = userService.checkAdminExist();

        assertTrue(result.isEmpty());
    }

    @Test
    void initDatabase_NoAdminExists_AdminUserCreated() throws Exception {
        when(userRepository.findByRole(User.Role.ADMIN)).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        CommandLineRunner initRunner = userService.initDatabase();

        initRunner.run();

        verify(userRepository).findByRole(User.Role.ADMIN);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void initDatabase_AdminExists_NoUserCreated() throws Exception {
        User adminUser = new User();
        adminUser.setRole(User.Role.ADMIN);
        when(userRepository.findByRole(User.Role.ADMIN)).thenReturn(Optional.of(adminUser));

        CommandLineRunner initRunner = userService.initDatabase();

        initRunner.run();

        verify(userRepository).findByRole(User.Role.ADMIN);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void findLoggedUser_Should_FoundUser() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDto result = userService.findLoggedUser();

        assertEquals(username, result.getUsername());
    }

    @Test
    public void testFindLoggedUser_Should_NotFoundUser() {
        String username = "nonExistentUser";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findLoggedUser());
    }
}