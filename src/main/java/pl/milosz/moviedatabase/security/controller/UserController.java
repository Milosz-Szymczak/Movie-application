package pl.milosz.moviedatabase.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.moviedatabase.entity.User;
import pl.milosz.moviedatabase.security.config.SecurityConfig;
import pl.milosz.moviedatabase.security.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final SecurityConfig securityConfig;

    public UserController(UserService userService, SecurityConfig securityConfig) {
        this.userService = userService;
        this.securityConfig = securityConfig;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @PostMapping("/register")
    public String registrationForm(@ModelAttribute User user) {
        userService.saveUser(user);
        securityConfig.addNewUser(user);
        return "redirect:/login";
    }
}
