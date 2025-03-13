package by.yurhilevich.WebApp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Добавляем роль в модель
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("role", role);

        // Логирование
        log.info("Accessing main page with role: {}", role);

        return "home";
    }
}
