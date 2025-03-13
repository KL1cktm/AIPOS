package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.service.DAOService;
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
public class AnalystPricesViewController {

    @Autowired
    DAOService daoService;

    @GetMapping("/analyst_prices_view")
    public String analystPricesView(Model model) {
        log.info("Accessing analyst prices view page.");
        return "analyst/analyst_prices_view";
    }

    @PostMapping("/viewPrices")
    public String viewPrices(@RequestParam("selectedDate") LocalDate date, Model model) {
        log.info("Fetching price dynamics for date: {}", date);
        try {
            List<Object[]> dynamicPrices = daoService.getPriceWithCombines(date);
            List<String> columns = Arrays.asList("Название товара", "Дата", "Закупочная цена", "Отпускная цена", "Комбинат");
            List<String> currentParams = Arrays.asList(date.toString());

            model.addAttribute("tablePrint", true);
            model.addAttribute("tableName", "Динамика цен на товары");
            model.addAttribute("columns", columns);
            model.addAttribute("values", dynamicPrices);
            model.addAttribute("currentParams", currentParams);

            log.info("Price dynamics successfully retrieved for date: {}", date);
        } catch (Exception e) {
            log.error("Error retrieving price dynamics for date {}: {}", date, e.getMessage());
        }
        return "/analyst/analyst_prices_view";
    }
}
