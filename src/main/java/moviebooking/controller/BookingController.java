package moviebooking.controller;

import moviebooking.entity.Booking;
import moviebooking.entity.Show;
import moviebooking.entity.User;
import moviebooking.repository.BookingRepository;
import moviebooking.repository.ShowRepository;
import moviebooking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    public BookingController(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return List.of();
        }

        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .toList();
    }

    @PostMapping("/user/{userId}/show/{showId}")
    public Booking createBooking(@PathVariable Long userId,
                                  @PathVariable Long showId,
                                  @RequestBody Booking booking) {

        User user = userRepository.findById(userId).orElse(null);
        Show show = showRepository.findById(showId).orElse(null);

        if (user == null || show == null) {
            return null;
        }

        booking.setUser(user);
        booking.setShow(show);
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }

    @PutMapping("/{id}/cancel")
    public Booking cancelBooking(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return null;
        }

        booking.setStatus("CANCELLED");

        return bookingRepository.save(booking);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
}