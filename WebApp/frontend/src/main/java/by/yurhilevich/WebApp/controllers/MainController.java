package by.yurhilevich.WebApp.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
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
