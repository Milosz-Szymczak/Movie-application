package pl.milosz.moviedatabase.security.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.security.config.SecurityConfig;
import pl.milosz.moviedatabase.security.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityConfig securityConfig;

    @Test
    void loginForm_should_ReturnOkStatus_And_ReturnLoginView() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void logout_should_ReturnRedirectionStatus_And_ReturnRedirectUrl() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void registration_should_ReturnOkStatus_And_AddUserModel() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", new User()))
                .andExpect(view().name("auth/registration"));
    }

    @Test
    void registrationForm_Should_SaveUserToDatabase_And_AddUserToInMemory_And_RedirectToLogin() throws Exception {
        User user = new User();

        doNothing().when(userService).saveUser(user);
        doNothing().when(securityConfig).addNewUser(user);

        mockMvc.perform(post("/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).saveUser(user);
        verify(securityConfig).addNewUser(user);
    }
}