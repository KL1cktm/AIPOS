package by.yurhilevich.WebApp.api;

import by.yurhilevich.WebApp.dto.UserDTO;
import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.repository.UserRepository;
import by.yurhilevich.WebApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestUserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createProduct(@RequestBody UserDTO userDTO) {
        log.info("Received request to create product: {}", userDTO);
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        user.setEmail(userDTO.getEmail());
        user.setFullname(userDTO.getFullname());
        user.setPhone(userDTO.getPhone());

        if (userService.isUserExists(user.getUsername(), user.getPhone())) {
            log.error("User already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        log.info("User registered successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        log.info("Received request to login: {}", userDTO);
        UserDetails user = userService.loadUserByUsername(userDTO.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        log.info("Received request to get users");
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFullname(user.getFullname());
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPhone(user.getPhone());
            userDTO.setRole(user.getRole().name());
            userDTO.setEmail(user.getEmail());
            userDTOs.add(userDTO);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PutMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        log.info("Received request to update user: {}", userDTO);
        Long id = userDTO.getId();
        log.info("Received request to update user with id: {}", id);
        User.Role role = User.Role.valueOf(userDTO.getRole());
        userService.changeUserRole(id,role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load_user")
    public ResponseEntity<UserDTO> loadUser(@RequestBody UserDTO userDTO) {
        log.info("Received request to load user: {}", userDTO);
        String username = userDTO.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserDTO userDTO1 = new UserDTO();
            userDTO1.setFullname(user.get().getFullname());
            userDTO1.setId(user.get().getId());
            userDTO1.setUsername(user.get().getUsername());
            userDTO1.setPhone(user.get().getPhone());
            userDTO1.setRole(user.get().getRole().name());
            userDTO1.setEmail(user.get().getEmail());
            userDTO1.setPassword(user.get().getPassword());
            return new ResponseEntity<>(userDTO1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
