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

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class DeleteAdminController {

    @Value("${backend.url}") // URL вашего бэкенда
    private String backendUrl;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/admin_dashboard_delete")
    public String admin_dashboard_delete(Model model) {
        log.info("Accessing admin dashboard delete page.");
        List<ProductDTO> products = getProductObject();
        List<PriceDTO> prices = getPriceObject();
        model.addAttribute("prices", prices);
        model.addAttribute("products", products);
        model.addAttribute("products_str", getProducts());
        model.addAttribute("employees", getEmployees());
        model.addAttribute("groupTypes", getGroups());
        return "admin/admin_dashboard_delete";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long productId, Model model) {
        log.info("Attempting to delete product with ID: {}", productId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        String url = backendUrl + "/api/deleteProduct";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
                new HttpEntity<>(productDTO), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Product with ID {} deleted successfully.", productId);
        } else {
            log.error("Failed to delete product with ID {}", productId);
        }
        return "redirect:/admin/admin_dashboard_delete";
    }

    @PostMapping("/deletePrices")
    public String deletePrice(@RequestParam("priceId") Long priceId, Model model) {
        log.info("Attempting to delete price with ID: {}", priceId);
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setPriceId(priceId);
        String url = backendUrl + "/api/deletePrice";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
                new HttpEntity<>(priceDTO), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Price with ID {} deleted successfully.", priceId);
        } else {
            log.error("Failed to delete price with ID {}", priceId);
        }
        return "redirect:/admin/admin_dashboard_delete";
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
