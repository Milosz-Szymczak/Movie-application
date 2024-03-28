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
import pl.milosz.moviedatabase.service.RatingService;
import pl.milosz.moviedatabase.service.UserMovieRelationService;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final MovieService movieService;
    private final RatingService ratingService;
    private final UserService userService;
    private final UserMovieRelationService userMovieRelationService;
    private final AwardService awardService;

    public UserController(MovieService movieService, RatingService ratingService, UserService userService, UserMovieRelationService userMovieRelationService, AwardService awardService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.userService = userService;
        this.userMovieRelationService = userMovieRelationService;
        this.awardService = awardService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        UserDto loggedUser = userService.findLoggedUser();
        List<MovieDto> moviesAddedByUser = userMovieRelationService.getMoviesAddedByUser(loggedUser);
        model.addAttribute("movies", moviesAddedByUser);
        return "user/profile";
    }
    @PostMapping("/rate/{movieId}")
    public String processMovieRatingRequest(@PathVariable Long movieId, @RequestParam("rating") int rating) {
        UserDto loggedUser = userService.findLoggedUser();

        boolean userCanGiveRating = ratingService.hasUserRatedMovie(movieId, loggedUser);
        if (!userCanGiveRating) {
            Movie movieById = movieService.getMovieById(movieId);
            ratingService.saveRating(movieById, rating, loggedUser);
        }

        return "redirect:/movie/" + movieId;
    }

    @GetMapping("/add-movie")
    public String moviePageForm(Model model) {
        model.addAttribute("movie", new MovieDto());
        model.addAttribute("categories", MovieDto.Category.values());
        return "user/add-movie";
    }

    @PostMapping("/add-movie-form")
    public String movieForm(@ModelAttribute MovieDto movieDto) {
        UserDto loggedUser = userService.findLoggedUser();

        Movie savedMovie = movieService.saveMovie(movieDto);
        userMovieRelationService.saveRelation(loggedUser, savedMovie);
        return "redirect:/add-award?movieId=" + savedMovie.getMovieId();
    }

    @GetMapping("/add-award")
    public String awardPageForm(@RequestParam("movieId") Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);

        List<AwardDto> awardsMovie = awardService.getAwards(movie);

        model.addAttribute("movie", movie);
        model.addAttribute("award", new AwardDto());
        model.addAttribute("awardsMovie", awardsMovie);
        return "user/add-award";
    }

    @PostMapping("/add-award-form/{movieId}")
    public String awardForm(@PathVariable Long movieId, @ModelAttribute AwardDto awardDto) {

        Movie movieById = movieService.getMovieById(movieId);
        awardDto.setMovie(movieById);
        awardService.saveAward(awardDto);

        return "redirect:/add-award?movieId=" + movieId;
    }
}
