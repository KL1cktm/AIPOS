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

import java.time.LocalDate;

@Log4j2
@Controller
@RequestMapping("/admin")
public class EditInfoAdminController {

    @Autowired
    ProductService productService;

    @Autowired
    GroupService groupService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PriceService priceService;

    @GetMapping("/admin_dashboard_edit")
    public String admin_dashboard_edit(Model model) {
        addAttributes(model);
        log.info("Accessed admin dashboard edit page.");
        return "admin/admin_dashboard_edit";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") Long productId,
                                @RequestParam("productName") String productName,
                                @RequestParam("sort") String sort,
                                @RequestParam("group") String group,
                                Model model) {
        log.info("Attempting to update product: id={}, name={}, sort={}, group={}",
                productId, productName, sort, group);
        if (productService.updateProduct(productId, productName, sort, group)) {
            log.info("Product updated successfully: id={}", productId);
            return "redirect:/admin/admin_dashboard_edit?success_edit_product=true";
        }
        log.error("Failed to update product: id={}", productId);
        return "redirect:/admin/admin_dashboard_edit?failed_edit_product=true";
    }

    @PostMapping("/updatePrices")
    public String updatePrice(@RequestParam("priceId") Long priceId,
                              @RequestParam("purchase_price") Double purchasePrice,
                              @RequestParam("selling_price") Double sellingPrice,
                              @RequestParam("selectedDate") LocalDate date,
                              @RequestParam("product") String productName,
                              @RequestParam("employee") String employeeName,
                              Model model) {
        log.info("Attempting to update price: id={}, purchasePrice={}, sellingPrice={}, date={}, product={}, employee={}",
                priceId, purchasePrice, sellingPrice, date, productName, employeeName);
        if (priceService.updatePrice(priceId, purchasePrice, sellingPrice, date, productName, employeeName)) {
            log.info("Price updated successfully: id={}", priceId);
            return "redirect:/admin/admin_dashboard_edit?success_edit_price=true";
        }
        log.error("Failed to update price: id={}", priceId);
        return "redirect:/admin/admin_dashboard_edit?failed_edit_price=true";
    }

    public void addAttributes(Model model) {
        log.debug("Adding attributes to model for admin dashboard.");
        model.addAttribute("prices", priceService.getAllPrices());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("products_str", productService.getAllProducts());
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("groupTypes", groupService.getAllGroups());
    }
}
