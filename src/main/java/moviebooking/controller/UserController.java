package moviebooking.controller;

import moviebooking.entity.User;
import moviebooking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Register user
    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Login user
    @PostMapping("/login")
    public String loginUser(@RequestBody User loginUser) {

        User user = userRepository.findByEmail(loginUser.getEmail()).orElse(null);

        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword().equals(loginUser.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);

        return "User deleted successfully";
    }
}