package moviebooking.controller;

import moviebooking.entity.Movie;
import moviebooking.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // Add movie
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    // Update movie
    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {

        Movie existingMovie = movieRepository.findById(id).orElse(null);

        if (existingMovie == null) {
            return null;
        }

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setLanguage(movie.getLanguage());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setRating(movie.getRating());

        return movieRepository.save(existingMovie);
    }

    // Delete movie
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {

        movieRepository.deleteById(id);

        return "Movie deleted successfully";
    }
}