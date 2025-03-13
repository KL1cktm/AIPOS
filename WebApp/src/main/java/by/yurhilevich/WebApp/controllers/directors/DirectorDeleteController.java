package by.yurhilevich.WebApp.controllers.directors;

import by.yurhilevich.WebApp.service.CombineService;
import by.yurhilevich.WebApp.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/director")
public class DirectorDeleteController {

    @Autowired
    CombineService combineService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/director_dashboard_delete")
    public String director_dashboard_delete(Model model) {
        log.info("Request to display the director dashboard for delete initiated.");
        addAttributes(model);
        return "/director/director_dashboard_delete";
    }

    @PostMapping("/deleteCombine")
    public String deleteCombine(@RequestParam("combineId") Long id, Model model) {
        log.info("Deleting Combine with ID: {}", id);
        combineService.deleteCombine(id);
        log.info("Combine with ID: {} has been deleted successfully.", id);
        return "redirect:/director/director_dashboard_delete";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") Long id, Model model) {
        log.info("Deleting Employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        log.info("Employee with ID: {} has been deleted successfully.", id);
        return "redirect:/director/director_dashboard_delete";
    }

    public void addAttributes(Model model) {
        log.info("Adding attributes for combines and employees.");
        model.addAttribute("combines", combineService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        log.info("Attributes added successfully.");
    }
}
