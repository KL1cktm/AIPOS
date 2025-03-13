package by.yurhilevich.WebApp.controllers.directors;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/director")
public class DirectorController {

    @GetMapping("/director_dashboard")
    public String director_dashboard_add(Model model) {
        log.info("Request to access the director dashboard received.");
        return "director/director_dashboard";
    }
}
