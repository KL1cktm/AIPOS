package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Group;
import by.yurhilevich.WebApp.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductService {
    boolean saveProduct(String productName, String sort, String groupName);
    boolean productExists(String productName);
    List<String> getAllProducts();
    List<Product> getAll();
    boolean updateProduct(Long id, String productName, String sort, String groupName);
    void deleteProduct(Long id);
}
