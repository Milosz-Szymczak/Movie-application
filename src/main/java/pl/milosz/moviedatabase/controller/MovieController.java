package pl.milosz.moviedatabase.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.security.service.UserService;
import pl.milosz.moviedatabase.service.AwardService;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.UserMovieRelationService;

import java.util.List;


@Controller
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;
    private final UserMovieRelationService userMovieRelationService;
    private final AwardService awardService;

    public MovieController(MovieService movieService, UserService userService, UserMovieRelationService userMovieRelationService, AwardService awardService) {
        this.movieService = movieService;
        this.userService = userService;
        this.userMovieRelationService = userMovieRelationService;
        this.awardService = awardService;
    }


    @GetMapping("/")
    public String homePage(Model model) {
        List<MovieDto> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "guest_html/home";
    }

    @GetMapping("/add-movie")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String moviePageForm(Model model) {
        model.addAttribute("movie", new MovieDto());
        model.addAttribute("category", MovieDto.Category.values());
        return "user/add-movie";
    }

    @PostMapping("/add-movie-form")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String movieForm(@ModelAttribute MovieDto movieDto) {
        UserDto loggedUser = userService.findLoggedUser();

        Movie savedMovie = movieService.saveMovie(movieDto);
        userMovieRelationService.saveRelation(loggedUser, savedMovie);

        return "redirect:/add-award?movieId=" + savedMovie.getMovieId();
    }

    @GetMapping("/add-award")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String awardPageForm(@RequestParam("movieId") Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);

        List<AwardDto> awardsMovie = awardService.getAwards(movie);

        model.addAttribute("movie", movie);
        model.addAttribute("award", new AwardDto());
        model.addAttribute("awardsMovie", awardsMovie);
        return "user/add-award";
    }

    @PostMapping("/add-award-form/{movieId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String awardForm(@PathVariable Long movieId, @ModelAttribute AwardDto awardDto) {

        Movie movieById = movieService.getMovieById(movieId);
        awardDto.setMovie(movieById);
        awardService.saveAward(awardDto);

        return "redirect:/add-award?movieId=" + movieId;
    }
}
