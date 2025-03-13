package by.yurhilevich.WebApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/analyst")
public class AnalystController {

    @GetMapping("/analyst_dashboard")
    public String analystDashboard(Model model) {
        log.info("Accessing analyst dashboard.");
        return "analyst/analyst_dashboard";
    }
}
