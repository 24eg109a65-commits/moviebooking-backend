package moviebooking.controller;

import moviebooking.entity.Screen;
import moviebooking.entity.Seat;
import moviebooking.repository.ScreenRepository;
import moviebooking.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@CrossOrigin
public class SeatController {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatController(SeatRepository seatRepository,
                          ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    // Get all seats
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    // Get seat by ID
    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    // Add seat to a screen
    @PostMapping("/screen/{screenId}")
    public Seat addSeat(@PathVariable Long screenId,
                        @RequestBody Seat seat) {

        Screen screen = screenRepository.findById(screenId).orElse(null);

        if (screen == null) {
            return null;
        }

        seat.setScreen(screen);
        seat.setBooked(false);

        return seatRepository.save(seat);
    }

    // Update seat
    @PutMapping("/{id}")
    public Seat updateSeat(@PathVariable Long id,
                           @RequestBody Seat seat) {

        Seat existingSeat = seatRepository.findById(id).orElse(null);

        if (existingSeat == null) {
            return null;
        }

        existingSeat.setSeatNumber(seat.getSeatNumber());
        existingSeat.setSeatType(seat.getSeatType());
        existingSeat.setBooked(seat.isBooked());

        return seatRepository.save(existingSeat);
    }

    // Delete seat
    @DeleteMapping("/{id}")
    public String deleteSeat(@PathVariable Long id) {

        seatRepository.deleteById(id);

        return "Seat deleted successfully";
    }
}