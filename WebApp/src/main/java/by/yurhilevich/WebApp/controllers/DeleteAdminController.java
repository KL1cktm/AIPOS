package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.service.EmployeeService;
import by.yurhilevich.WebApp.service.GroupService;
import by.yurhilevich.WebApp.service.PriceService;
import by.yurhilevich.WebApp.service.ProductService;
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
@RequestMapping("/admin")
public class DeleteAdminController {

    @Autowired
    ProductService productService;

    @Autowired
    GroupService groupService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PriceService priceService;

    @GetMapping("/admin_dashboard_delete")
    public String admin_dashboard_delete(Model model) {
        log.info("Accessing admin dashboard delete page.");
        addAttributes(model);
        return "admin/admin_dashboard_delete";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long productId, Model model) {
        log.info("Attempting to delete product with ID: {}", productId);
        try {
            productService.deleteProduct(productId);
            log.info("Product with ID {} deleted successfully.", productId);
        } catch (Exception e) {
            log.error("Failed to delete product with ID {}: {}", productId, e.getMessage());
        }
        return "redirect:/admin/admin_dashboard_delete";
    }

    @PostMapping("/deletePrices")
    public String deletePrice(@RequestParam("priceId") Long priceId, Model model) {
        log.info("Attempting to delete price with ID: {}", priceId);
        try {
            priceService.deletePrice(priceId);
            log.info("Price with ID {} deleted successfully.", priceId);
        } catch (Exception e) {
            log.error("Failed to delete price with ID {}: {}", priceId, e.getMessage());
        }
        return "redirect:/admin/admin_dashboard_delete";
    }

    public void addAttributes(Model model) {
        log.debug("Adding attributes to the model for the admin dashboard delete page.");
        model.addAttribute("prices", priceService.getAllPrices());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("products_str", productService.getAllProducts());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("groupTypes", groupService.getAllGroups());
    }
}
