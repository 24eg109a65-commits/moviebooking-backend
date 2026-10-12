package moviebooking.controller;

import moviebooking.entity.Movie;
import moviebooking.entity.Screen;
import moviebooking.entity.Show;
import moviebooking.repository.MovieRepository;
import moviebooking.repository.ScreenRepository;
import moviebooking.repository.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@CrossOrigin
public class ShowController {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowController(ShowRepository showRepository,
                           MovieRepository movieRepository,
                           ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    @GetMapping
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    @GetMapping("/{id}")
    public Show getShowById(@PathVariable Long id) {
        return showRepository.findById(id).orElse(null);
    }

    @PostMapping("/movie/{movieId}/screen/{screenId}")
    public Show addShow(@PathVariable Long movieId,
                        @PathVariable Long screenId,
                        @RequestBody Show show) {

        Movie movie = movieRepository.findById(movieId).orElse(null);
        Screen screen = screenRepository.findById(screenId).orElse(null);

        if (movie == null || screen == null) {
            return null;
        }

        show.setMovie(movie);
        show.setScreen(screen);

        return showRepository.save(show);
    }

    @PutMapping("/{id}")
    public Show updateShow(@PathVariable Long id,
                           @RequestBody Show show) {

        Show existingShow = showRepository.findById(id).orElse(null);

        if (existingShow == null) {
            return null;
        }

        existingShow.setShowDate(show.getShowDate());
        existingShow.setShowTime(show.getShowTime());
        existingShow.setPrice(show.getPrice());

        return showRepository.save(existingShow);
    }

    @DeleteMapping("/{id}")
    public String deleteShow(@PathVariable Long id) {
        showRepository.deleteById(id);
        return "Show deleted successfully";
    }
}