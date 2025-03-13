package by.yurhilevich.WebApp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
@Controller
@RequestMapping("/admin")
public class ActivityController {

    @GetMapping("/choose_activity")
    public String chooseActivity(Model model) {
        log.info("Accessed choose_activity page.");
        return "admin/choose_activity";
    }
}
