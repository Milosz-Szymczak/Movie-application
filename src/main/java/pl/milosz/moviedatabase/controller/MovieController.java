package pl.milosz.moviedatabase.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.security.service.UserService;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.UserMovieRelationService;


@Controller
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;
    private final UserMovieRelationService userMovieRelationService;

    public MovieController(MovieService movieService, UserService userService, UserMovieRelationService userMovieRelationService) {
        this.movieService = movieService;
        this.userService = userService;
        this.userMovieRelationService = userMovieRelationService;
    }


    @GetMapping("/")
    public String homePage() {
        return "guest_html/home";
    }

    @GetMapping("/add-movie")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String addMoviePage(Model model) {
        model.addAttribute("movie", new MovieDto());
        model.addAttribute("category", MovieDto.Category.values());
        return "user/add-movie";
    }

    @PostMapping("/add-movie-form")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String addMovieForm(@ModelAttribute MovieDto movieDto) {
        UserDto loggedUser = userService.findLoggedUser();
        Movie savedMovie = movieService.saveMovie(movieDto);

        userMovieRelationService.saveRelation(loggedUser, savedMovie);

        return "redirect:/";
    }

}
