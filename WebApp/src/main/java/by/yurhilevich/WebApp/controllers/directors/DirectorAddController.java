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

@Log4j2
@Controller
@RequestMapping("/director")
public class DirectorAddController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CombineService combineService;

    @Autowired
    RegionService regionService;

    @GetMapping("/director_dashboard_add")
    public String directorAdd(Model model) {
        log.info("Accessing the Director Dashboard Add page.");
        addAttributes(model);
        return "director/director_dashboard_add";
    }

    @PostMapping("/createEmployee")
    public String creatEmployee(@RequestParam("employee_name") String fio,
                                @RequestParam("rang") String position,
                                @RequestParam("combines") String combineName,
                                Model model) {
        log.info("Attempting to create employee with name: {}, position: {}, combine: {}", fio, position, combineName);
        if (employeeService.addEmployee(fio, position, combineName)) {
            log.info("Employee created successfully: {}", fio);
            return "redirect:/director/director_dashboard_add?create_employee=true";
        } else {
            log.error("Error creating employee: {}", fio);
            return "redirect:/director/director_dashboard_add?create_employee_error=true";
        }
    }

    @PostMapping("/createCombine")
    public String createCombine(@RequestParam("name") String combineName,
                                @RequestParam("address") String address,
                                @RequestParam("phone") String phone,
                                @RequestParam("regions") String regionName,
                                Model model) {
        log.info("Attempting to create combine with name: {}, address: {}, phone: {}, region: {}", combineName, address, phone, regionName);
        if (combineService.addCombine(combineName, address, phone, regionName)) {
            log.info("Combine created successfully: {}", combineName);
            return "redirect:/director/director_dashboard_add?combine_added=true";
        }
        log.error("Combine already exists: {}", combineName);
        return "redirect:/director/director_dashboard_add?combine_exist=true";
    }

    public void addAttributes(Model model) {
        log.info("Adding attributes for combines and regions.");
        model.addAttribute("combines", combineService.getAllCombines());
        model.addAttribute("regions", regionService.getAllRegions());
        log.info("Attributes added: combines and regions.");
    }
}
