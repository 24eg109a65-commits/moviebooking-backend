package moviebooking.controller;

import moviebooking.entity.Screen;
import moviebooking.entity.Theatre;
import moviebooking.repository.ScreenRepository;
import moviebooking.repository.TheatreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
@CrossOrigin
public class ScreenController {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    public ScreenController(ScreenRepository screenRepository,
                            TheatreRepository theatreRepository) {
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
    }

    // Get all screens
    @GetMapping
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    // Get screen by ID
    @GetMapping("/{id}")
    public Screen getScreenById(@PathVariable Long id) {
        return screenRepository.findById(id).orElse(null);
    }

    // Add screen
    @PostMapping("/theatre/{theatreId}")
    public Screen addScreen(@PathVariable Long theatreId,
                            @RequestBody Screen screen) {

        Theatre theatre = theatreRepository.findById(theatreId).orElse(null);

        if (theatre == null) {
            return null;
        }

        screen.setTheatre(theatre);

        return screenRepository.save(screen);
    }

    // Update screen
    @PutMapping("/{id}")
    public Screen updateScreen(@PathVariable Long id,
                               @RequestBody Screen screen) {

        Screen existingScreen = screenRepository.findById(id).orElse(null);

        if (existingScreen == null) {
            return null;
        }

        existingScreen.setName(screen.getName());
        existingScreen.setTotalSeats(screen.getTotalSeats());

        return screenRepository.save(existingScreen);
    }

    // Delete screen
    @DeleteMapping("/{id}")
    public String deleteScreen(@PathVariable Long id) {

        screenRepository.deleteById(id);

        return "Screen deleted successfully";
    }
}