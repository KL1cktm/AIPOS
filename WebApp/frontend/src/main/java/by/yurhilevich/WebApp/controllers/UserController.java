package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.dto.UserDTO;
import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Slf4j
@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${backend.url}")
    private String backendUrl;
    @Autowired
    private UserService userService;

    @GetMapping("signup")
    public String signup() {
        log.info("signup get request");
        return "signup";
    }

    @PostMapping("signup")
    public String processSignup(User user) {
        log.info("processSignup post request");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        System.out.println(user.getPassword());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());
        userDTO.setFullname(user.getFullname());
        userDTO.setPhone(user.getPhone());
        String url = backendUrl + "/api/signup";
        System.out.println("start");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(userDTO), String.class);

        if (response.getStatusCode()== HttpStatus.CONFLICT) {
            return "redirect:/signup1?error=true";
        }
        log.info("User registered successfully");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loadAuthorizationPage(Model model) {
        log.info("loadAuthorizationPage get request");
        return "login";
    }

    @GetMapping("/signup1")
    public String loadAuthorizationPage1(Model model) {
        log.info("signup1 get request");
        return "signup1";
    }

    @PostMapping("/login_processing")
    public String processLogin(String username, String password) {
        userService.loadUserByUsername(username);

        log.info("login processing post request");
        return "redirect:/";
    }
}
