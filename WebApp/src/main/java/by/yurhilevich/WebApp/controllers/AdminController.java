package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.GroupService;
import by.yurhilevich.WebApp.service.ProductService;
import by.yurhilevich.WebApp.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin_panel")
    public String createAdminPanel(Model model) {
        log.info("Accessing the admin panel.");
        List<User> users = userService.getAllUsers();
        List<String> roles = Arrays.asList("ROLE_ANALYST", "ROLE_ADMIN", "ROLE_DIRECTOR");
        model.addAttribute("users", users);
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
            userService.changeUserRole(userId, newRole);
            log.info("Role for user with ID: {} successfully changed to {}", userId, newRole);
        } catch (IllegalArgumentException e) {
            log.error("Invalid role provided: {}", role, e);
            return "redirect:/admin/admin_panel?error=true";
        }
        return "redirect:/admin/admin_panel";
    }
}
