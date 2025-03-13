package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.service.DAOService;
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
import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/analyst")
public class AnalystDynamicViewController {

    @Autowired
    DAOService daoService;

    @Autowired
    ProductService productService;

    @GetMapping("/analyst_dynamic")
    public String analyst_dynamic(Model model) {
        log.info("Accessing dynamic view page.");
        addAttributes(model);
        return "analyst/analyst_dynamic";
    }

    @PostMapping("/viewDynamic")
    public String write(@RequestParam("selectedDate") LocalDate date1,
                        @RequestParam("selectedDateStart") LocalDate date2,
                        @RequestParam("products") String products,
                        Model model) {
        log.info("Fetching dynamic prices for products: {} between dates: {} and {}", products, date2, date1);
        try {
            List<Object[]> dynamicPrices = daoService.getDynamicPrices(date1, date2, products);
            List<String> columns = Arrays.asList("Название товара", "Дата", "Закупочная цена", "Отпускная цена", "Сотрудник", "Регион");
            List<String> currentParams = Arrays.asList(date1.toString(), date2.toString(), products);

            model.addAttribute("tablePrint", true);
            model.addAttribute("tableName", "Динамика цен на товары");
            model.addAttribute("columns", columns);
            model.addAttribute("values", dynamicPrices);
            model.addAttribute("currentParams", currentParams);
            addAttributes(model);

            log.info("Dynamic prices successfully retrieved for products: {} between dates: {} and {}", products, date2, date1);
        } catch (Exception e) {
            log.error("Error fetching dynamic prices for products: {} between dates: {} and {}. Error: {}", products, date2, date1, e.getMessage());
        }
        return "/analyst/analyst_dynamic";
    }

    public void addAttributes(Model model) {
        log.info("Adding product attributes to the model.");
        model.addAttribute("products", productService.getAllProducts());
    }
}
