package by.yurhilevich.WebApp.controllers.directors;

import by.yurhilevich.WebApp.service.CombineService;
import by.yurhilevich.WebApp.service.EmployeeService;
import by.yurhilevich.WebApp.service.RegionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
@Controller
@RequestMapping("/director")
public class DirectorEditController {

    @Autowired
    CombineService combineService;

    @Autowired
    RegionService regionService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/director_dashboard_edit")
    public String director_dashboard_edit(Model model) {
        log.info("Accessed director dashboard edit page.");
        addAttributes(model);
        return "/director/director_dashboard_edit";
    }

    @PostMapping("/updateCombine")
    public String updateCombine(@RequestParam("combineId") Long id,
                                @RequestParam("combineName") String combineName,
                                @RequestParam("address") String address,
                                @RequestParam("phone") String phone,
                                @RequestParam("regions") String regionName,
                                Model model) {
        log.info("Attempting to update combine with ID: {}", id);
        boolean success = combineService.updateCombine(id, combineName, address, phone, regionName);
        if (success) {
            log.info("Successfully updated combine with ID: {}", id);
            return "redirect:/director/director_dashboard_edit?success_edit_product=true";
        } else {
            log.error("Failed to update combine with ID: {}", id);
            return "redirect:/director/director_dashboard_edit?failed_edit_product=true";
        }
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("employeeId") Long id,
                                 @RequestParam("fio") String fio,
                                 @RequestParam("rang") String position,
                                 @RequestParam("combines") String combines,
                                 Model model) {
        log.info("Attempting to update employee with ID: {}", id);
        boolean success = employeeService.updateEmployee(id, fio, position, combines);
        if (success) {
            log.info("Successfully updated employee with ID: {}", id);
            return "redirect:/director/director_dashboard_edit?success_edit_employee=true";
        } else {
            log.error("Failed to update employee with ID: {}", id);
            return "redirect:/director/director_dashboard_edit?failed_edit_employee=true";
        }
    }

    public void addAttributes(Model model) {
        log.info("Adding attributes to model for director dashboard edit page.");
        model.addAttribute("combines", combineService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("combines_str", combineService.getAllCombines());
        model.addAttribute("regions", regionService.getAllRegions());
    }
}
