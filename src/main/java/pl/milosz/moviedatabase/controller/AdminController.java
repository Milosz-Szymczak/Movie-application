package pl.milosz.moviedatabase.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.milosz.moviedatabase.dto.AwardDto;
import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.service.AwardService;
import pl.milosz.moviedatabase.service.MovieService;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MovieService movieService;
    private final AwardService awardService;

    public AdminController(MovieService movieService, AwardService awardService) {
        this.movieService = movieService;
        this.awardService = awardService;
    }

    @GetMapping("/manage-movie")
    public String managePage(Model model) {
        List<MovieDto> allMovies = movieService.getAllMovies();
        model.addAttribute("movies", allMovies);

        return "admin/manage-movie";
    }

    @GetMapping("/update-movie/{movieId}")
    public String updateMoviePage(@PathVariable Long movieId, Model model) {
        Movie movieById = movieService.getMovieById(movieId);
        model.addAttribute("movie", movieById);

        List<AwardDto> awardsMovie = awardService.getAwards(movieById);

        model.addAttribute("award", new AwardDto());
        model.addAttribute("awardsMovie", awardsMovie);
        model.addAttribute("categories", MovieDto.Category.values());
        return "admin/update-movie";
    }

    @PatchMapping("/update-movie-form")
    public String updateMovieForm(@ModelAttribute MovieDto movieDto, HttpServletRequest request) {
        movieService.updateMovie(movieDto);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @PatchMapping("/update-award-form/{awardId}")
    public String updateAwardForm(@PathVariable Long awardId, @RequestParam(name = "updateAward") String updatedAwardName, HttpServletRequest request) {
        awardService.updateAward(awardId, updatedAwardName);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @DeleteMapping("/delete-award-form/{awardId}")
    public String deleteAward(@PathVariable Long awardId, HttpServletRequest request) {
        awardService.deleteAward(awardId);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }

    @PostMapping("/add-award-admin/{movieId}")
    public String awardForm(@PathVariable Long movieId, @ModelAttribute AwardDto awardDto, HttpServletRequest request) {
        Movie movieById = movieService.getMovieById(movieId);
        awardDto.setMovie(movieById);
        awardService.saveAward(awardDto);
        String referrer = request.getHeader("referer");
        return "redirect:" + referrer;
    }
}
