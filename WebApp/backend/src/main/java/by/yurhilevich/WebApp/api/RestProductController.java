package by.yurhilevich.WebApp.api;

import by.yurhilevich.WebApp.dto.ProductDTO;
import by.yurhilevich.WebApp.models.Product;
import by.yurhilevich.WebApp.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
public class RestProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add_products")
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Received request to create product: {}", productDTO);
        
        // Сохраняем продукт в базе данных
        boolean flag = productService.saveProduct(productDTO.getName(),productDTO.getSort(),productDTO.getGroupName());

        if (!flag) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Product doesn't create");
        }

        log.info("Product '{}' created successfully", productDTO.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product created successfully");
    }

    @GetMapping("/get_products")
    public ResponseEntity<List<String>> getProducts() {
        try {
            List<String> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build(); // Возвращаем статус 204, если список пуст
            }
            return ResponseEntity.ok(products); // Возвращаем список групп со статусом 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500
        }
    }

    @PutMapping("updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        log.info("Received request to update product: {}", productDTO);
        boolean flag = productService.updateProduct(productDTO.getId(),productDTO.getName(),productDTO.getSort(),productDTO.getGroupName());
        if (!flag) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Product doesn't update");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product created successfully");
    }

    @GetMapping("/get_products_objects")
    public ResponseEntity<List<ProductDTO>> getProductsObjects() {
        try {
            List<Product> products = productService.getAll();
            List<ProductDTO> productDTOs = new ArrayList<>();
            for (Product product : products) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setName(product.getName());
                productDTO.setSort(product.getSort());
                productDTO.setGroupName(product.getGroup().getName());
                productDTO.setId(product.getProductId());
                productDTOs.add(productDTO);
            }
            System.out.println(productDTOs.size());
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build(); // Возвращаем статус 204, если список пуст
            }
            return ResponseEntity.ok(productDTOs); // Возвращаем список групп со статусом 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500
        }
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductDTO productDTO) {
        log.info("Received request to delete product: {}", productDTO);
        try {
            productService.deleteProduct(productDTO.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
