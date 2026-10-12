package moviebooking.controller;

import moviebooking.entity.Theatre;
import moviebooking.repository.TheatreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
@CrossOrigin
public class TheatreController {

    private final TheatreRepository theatreRepository;

    public TheatreController(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    // Get all theatres
    @GetMapping
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    // Get theatre by ID
    @GetMapping("/{id}")
    public Theatre getTheatreById(@PathVariable Long id) {
        return theatreRepository.findById(id).orElse(null);
    }

    // Add theatre
    @PostMapping
    public Theatre addTheatre(@RequestBody Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    // Update theatre
    @PutMapping("/{id}")
    public Theatre updateTheatre(@PathVariable Long id, @RequestBody Theatre theatre) {

        Theatre existingTheatre = theatreRepository.findById(id).orElse(null);

        if (existingTheatre == null) {
            return null;
        }

        existingTheatre.setName(theatre.getName());
        existingTheatre.setLocation(theatre.getLocation());
        existingTheatre.setCity(theatre.getCity());
        existingTheatre.setTotalScreens(theatre.getTotalScreens());

        return theatreRepository.save(existingTheatre);
    }

    // Delete theatre
    @DeleteMapping("/{id}")
    public String deleteTheatre(@PathVariable Long id) {

        theatreRepository.deleteById(id);

        return "Theatre deleted successfully";
    }
}