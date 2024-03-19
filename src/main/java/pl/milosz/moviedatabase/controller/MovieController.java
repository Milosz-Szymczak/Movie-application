package pl.milosz.moviedatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.milosz.moviedatabase.service.MovieService;

@Controller
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/")
    public String homePage() {
        return "guest_html/home";
    }

}
