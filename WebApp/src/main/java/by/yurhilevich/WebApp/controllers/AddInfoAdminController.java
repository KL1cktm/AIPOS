package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.service.EmployeeService;
import by.yurhilevich.WebApp.service.GroupService;
import by.yurhilevich.WebApp.service.PriceService;
import by.yurhilevich.WebApp.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AddInfoAdminController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ProductService productService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PriceService priceService;

    @GetMapping("/admin_dashboard_add")
    public String createDashboard(Model model) {
        log.info("Creating admin dashboard.");
        addAttributes(model);
        return "admin/admin_dashboard_add";
    }

    @PostMapping("/createGroup")
    public String addGroup(@RequestParam("group_name") String name, Model model) {
        log.info("Attempting to create group with name: {}", name);
        if (!groupService.isGroupExists(name)) {
            groupService.saveGroup(name);
            log.info("Group with name '{}' added successfully.", name);
            return "redirect:/admin/admin_dashboard_add?group_added=true";
        } else {
            log.warn("Group with name '{}' already exists.", name);
            return "redirect:/admin/admin_dashboard_add?group_exist=true";
        }
    }

    @PostMapping("/createProduct")
    public String addProduct(@RequestParam("product_name") String name, @RequestParam("sort") String sort, @RequestParam("groups") String group, Model model) {
        log.info("Attempting to create product: {} (Sort: {}, Group: {})", name, sort, group);
        if (productService.saveProduct(name, sort, group)) {
            log.info("Product '{}' created successfully.", name);
            return "redirect:/admin/admin_dashboard_add?create_product=true";
        }
        log.error("Failed to create product '{}' (Sort: {}, Group: {}).", name, sort, group);
        return "redirect:/admin/admin_dashboard_add?create_product_error=true";
    }

    @PostMapping("/createPrice")
    public String addPrice(@RequestParam("purchasePrice") Double purchasePrice, @RequestParam("sellingPrice") Double sellingPrice,
                           @RequestParam("products") String product, @RequestParam("employees") String employees,
                           @RequestParam(value = "priceDate", required = false) LocalDate date, Model model) {
        log.info("Attempting to create price for product: {} with purchase price: {} and selling price: {}", product, purchasePrice, sellingPrice);
        if (priceService.addPrice(purchasePrice, sellingPrice, product, employees, date)) {
            log.info("Price for product '{}' added successfully.", product);
            return "redirect:/admin/admin_dashboard_add?price_added=true";
        }
        log.error("Failed to add price for product '{}'.", product);
        return "redirect:/admin/admin_dashboard_add?price_exist=true";
    }

    public void addAttributes(Model model) {
        log.debug("Adding attributes to the model.");
        model.addAttribute("groupTypes", groupService.getAllGroups());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("products", productService.getAllProducts());
    }
}
