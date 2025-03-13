package by.yurhilevich.WebApp.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class ActivityController {

    @GetMapping("/choose_activity")
    public String chooseActivity(Model model) {
        log.info("Accessed choose_activity page.");
        return "admin/choose_activity";
    }
}
