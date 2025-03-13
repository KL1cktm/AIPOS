package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.dto.UserDTO;
import by.yurhilevich.WebApp.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate; // Используем RestTemplate для запросов к бэкенду

    @Value("${backend.url}") // URL вашего бэкенда
    private String backendUrl;

    @GetMapping("/admin_panel")
    public String createAdminPanel(Model model) {
        log.info("Accessing the admin panel.");
        String url = backendUrl + "/api/get_users";
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<UserDTO>>() {});
        List<String> roles = Arrays.asList("ROLE_ANALYST", "ROLE_ADMIN", "ROLE_DIRECTOR");
        model.addAttribute("users", response.getBody());
        model.addAttribute("roles", roles);
        return "admin/admin_panel";
    }

    @PostMapping("/changeRole")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String role, @RequestParam String userLogin, Model model) {
        log.info("Attempting to change role for user with ID: {} and new role: {}", userId, role);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            log.debug("Authenticated user: {}", login);
            if (login.equals(userLogin)) {
                log.warn("User tried to change their own role. Action aborted.");
                return "redirect:/admin/admin_panel?error_login=true";
            }
        }

        try {
            User.Role newRole = User.Role.valueOf(role);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            userDTO.setRole(role);
            String url = backendUrl + "/api/update_user";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
                    new HttpEntity<>(userDTO), String.class);
            if (response.getStatusCode()== HttpStatus.OK) {
                log.info("User tried to change their own role. Action aborted.");
            }
            log.info("Role for user with ID: {} successfully changed to {}", userId, newRole);
        } catch (IllegalArgumentException e) {
            log.error("Invalid role provided: {}", role, e);
            return "redirect:/admin/admin_panel?error=true";
        }
        return "redirect:/admin/admin_panel";
    }
}
