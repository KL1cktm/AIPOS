package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.dto.PriceDTO;
import by.yurhilevich.WebApp.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class EditInfoAdminController {
    @Value("${backend.url}") // URL вашего бэкенда
    private String backendUrl;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/admin_dashboard_edit")
    public String admin_dashboard_edit(Model model) {
        log.info("Accessed admin dashboard edit page.");
        List<ProductDTO> products = getProductObject();
        List<PriceDTO> prices = getPriceObject();
        model.addAttribute("prices", prices);
        model.addAttribute("products", products);
        model.addAttribute("products_str", getProducts());
        model.addAttribute("employees", getEmployees());
        model.addAttribute("groupTypes", getGroups());
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

        String url = backendUrl + "/api/updateProduct";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName(productName);
        productDTO.setSort(sort);
        productDTO.setGroupName(group);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(productDTO), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
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
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setPriceId(priceId);
        priceDTO.setPurchasePrice(purchasePrice);
        priceDTO.setSellingPrice(sellingPrice);
        priceDTO.setDate(date);
        priceDTO.setProduct(productName);
        priceDTO.setEmployee(employeeName);

        String url = backendUrl + "/api/updatePrices";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(priceDTO), String.class);


        log.info("Attempting to update price: id={}, purchasePrice={}, sellingPrice={}, date={}, product={}, employee={}",
                priceId, purchasePrice, sellingPrice, date, productName, employeeName);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Price updated successfully: id={}", priceId);
            return "redirect:/admin/admin_dashboard_edit?success_edit_price=true";
        }
        log.error("Failed to update price: id={}", priceId);
        return "redirect:/admin/admin_dashboard_edit?failed_edit_price=true";
    }

    private List<String> getGroups() {
        String url = backendUrl + "/api/get_groups";
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

    private List<String> getProducts() {
        String url = backendUrl + "/api/get_products";
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

    private List<String> getEmployees() {
        String url = backendUrl + "/api/get_employees";
        // Используем ParameterizedTypeReference для правильной десериализации
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();  // Возвращаем пустой список в случае ошибки или пустого ответа
    }

    private List<ProductDTO> getProductObject() {
        String url = backendUrl + "/api/get_products_objects";
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

    private List<PriceDTO> getPriceObject() {
        String url = backendUrl + "/api/get_prices_objects";
        ResponseEntity<List<PriceDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PriceDTO>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

}
