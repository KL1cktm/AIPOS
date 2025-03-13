package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.dto.GroupDTO;
import by.yurhilevich.WebApp.dto.PriceDTO;
import by.yurhilevich.WebApp.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AddInfoAdminController {

    @Autowired
    private RestTemplate restTemplate; // Используем RestTemplate для запросов к бэкенду

    @Value("${backend.url}") // URL вашего бэкенда
    private String backendUrl;

    @PostMapping("/createGroup")
    public String addGroup(@RequestParam("group_name") String name, Model model) {
        log.info("Attempting to create group with name: {}", name);

        String url = backendUrl + "/api/add_group";

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName(name);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(groupDTO), String.class);

            log.info("Received response: status = {}, body = {}", response.getStatusCode(), response.getBody());

            if (response.getStatusCode() == HttpStatus.CREATED) {
                log.info("Group with name '{}' added successfully.", name);
                return "redirect:/admin/admin_dashboard_add?group_added=true";
            } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                // Обработка ошибки с кодом CONFLICT
                log.warn("Group with name '{}' already exists.", name);
                String body = response.getBody();
                String message = body != null ? body : "Unknown error";
                log.error("Error creating group: {}", message);
                return "redirect:/admin/admin_dashboard_add?group_exist=true";
            }
        } catch (Exception e) {
            log.error("Error while creating group: {}", e.getMessage());
        }
        return "redirect:/admin/admin_dashboard_add?group_error=true";
    }





    @PostMapping("/createPrice")
    public String addPrice(@RequestParam("purchasePrice") Double purchasePrice, @RequestParam("sellingPrice") Double sellingPrice,
                           @RequestParam("products") String product, @RequestParam("employees") String employees,
                           @RequestParam(value = "priceDate", required = false) LocalDate date, Model model) {
        log.info("Attempting to create price for product: {} with purchase price: {} and selling price: {}", product, purchasePrice, sellingPrice);

        String url = backendUrl + "/api/add_prices";

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setPurchasePrice(purchasePrice);
        priceDTO.setSellingPrice(sellingPrice);
        priceDTO.setProduct(product);
        priceDTO.setEmployee(employees);
        priceDTO.setDate(date);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(priceDTO), String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("Price for product '{}' added successfully.", product);
            return "redirect:/admin/admin_dashboard_add?price_added=true";
        }
        log.error("Failed to add price for product '{}'.", product);
        return "redirect:/admin/admin_dashboard_add?price_exist=true";
    }

    @PostMapping("/createProduct")
    public String addProduct(@RequestParam("product_name") String name, @RequestParam("sort") String sort,
                             @RequestParam("groups") String groupName, Model model) {
        log.info("Attempting to create product: {} (Sort: {}, Group: {})", name, sort, groupName);

        // Подготовка запроса с полными данными
        String url = backendUrl + "/api/add_products";
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSort(sort);
        productDTO.setGroupName(groupName);  // Отправляем имя группы, а не объект

        // Отправка POST-запроса на бэкенд
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(productDTO), String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("Product '{}' created successfully.", name);
            return "redirect:/admin/admin_dashboard_add?create_product=true";
        } else {
            log.error("Failed to create product '{}' (Sort: {}, Group: {}).", name, sort, groupName);
            return "redirect:/admin/admin_dashboard_add?create_product_error=true";
        }
    }

    @GetMapping("/admin_dashboard_add")
    public String createDashboard(Model model) {
        log.info("Creating admin dashboard.");

        // Получаем данные через REST API
        model.addAttribute("groupTypes", getGroups());
        model.addAttribute("employees", getEmployees());
        model.addAttribute("products", getProducts());

        return "admin/admin_dashboard_add";
    }

    private List<String> getGroups() {
        System.out.println("error");
        String url = backendUrl + "/api/get_groups";
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

    // Метод для получения сотрудников
    private List<String> getEmployees() {
        String url = backendUrl + "/api/get_employees";
        // Используем ParameterizedTypeReference для правильной десериализации
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();  // Возвращаем пустой список в случае ошибки или пустого ответа
    }


    // Метод для получения продуктов
    private List<String> getProducts() {
        String url = backendUrl + "/api/get_products";
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return List.of();
    }

}
