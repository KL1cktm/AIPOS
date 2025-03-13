package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Product;
import by.yurhilevich.WebApp.repository.GroupRepository;
import by.yurhilevich.WebApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;

    public boolean saveProduct(String productName, String sort, String groupName) {
        if (!productRepository.existsProductByName(productName) && groupRepository.existsByName(groupName)) {
            Product product = new Product();
            product.setName(productName);
            product.setSort(sort);
            product.setGroup(groupRepository.findGroupByName(groupName).get());
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean productExists(String productName) {
        return productRepository.existsProductByName(productName);
    }

    @Override
    public List<String> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        return productNames;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public boolean updateProduct(Long id, String productName, String sort, String groupName) {
        if (productRepository.existsProductByProductId(id) && groupRepository.existsByName(groupName)) {
            Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Продукт с id " + id + " не найден."));
            product.setName(productName);
            product.setSort(sort);
            product.setGroup(groupRepository.findGroupByName(groupName).get());
            productRepository.save(product);
            return true;
        }
        return false;
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
