package pl.milosz.moviedatabase.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.service.MovieService;
import pl.milosz.moviedatabase.service.RatingService;
import java.util.List;


@Controller
public class MovieController {
    private final MovieService movieService;

    private final RatingService ratingService;

    public MovieController(MovieService movieService,RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }


    @GetMapping("/")
    public String homePage(Model model) {
        List<MovieDto> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("categories", MovieDto.Category.values());
        return "guest/home";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("keyword") String keyword, Model model) {
        List<MovieDto> searchResults = movieService.searchMovies(keyword);
        model.addAttribute("movies", searchResults);
        return "guest/home";
    }

    @GetMapping("/selectCategory")
    public String getFilteredMovies(@RequestParam(name = "category", required = false) String selectedCategory, Model model) {
        List<MovieDto> filteredMovies;

        if (selectedCategory != null && !selectedCategory.isEmpty() && !selectedCategory.equals("ALL_CATEGORIES")) {
            Movie.Category  category = Movie.Category.valueOf(selectedCategory);
            filteredMovies = movieService.getMoviesByCategory(category);
        } else {
            filteredMovies = movieService.getAllMovies();
        }

        model.addAttribute("movies", filteredMovies);
        model.addAttribute("categories", Movie.Category.values());

        return "guest/home";
    }

    @GetMapping("/movie/{movieId}")
    public String moviePage(@PathVariable Long movieId, Model model) {
        Movie movieById = movieService.getMovieById(movieId);
        model.addAttribute("movie", movieById);

        String ratingMovie = ratingService.getOverallRatingForMovieById(movieId);
        model.addAttribute("ratingOverall", ratingMovie);
        return "guest/movie";
    }

}
