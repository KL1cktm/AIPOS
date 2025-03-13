package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("signup")
    public String signup() {
        log.info("signup get request");
        return "signup";
    }

    @PostMapping("signup")
    public String processSignup(User user) {
        log.info("processSignup post request");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.isUserExists(user.getUsername(), user.getPhone())) {
            log.error("User already exists");
            return "redirect:/signup1?error=true";
        }
        userService.saveUser(user);
        log.info("User registered successfully");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loadAuthorizationPage(Model model) {
        log.info("loadAuthorizationPage get request");
        return "login";
    }

    @PostMapping("/login_processing")
    public String processLogin(String username, String password) {
        userService.loadUserByUsername(username);
        log.info("login processing post request");
        return "redirect:/";
    }

    @GetMapping("/signup1")
    public String loadAuthorizationPage1(Model model) {
        log.info("signup1 get request");
        return "signup1";
    }
}
