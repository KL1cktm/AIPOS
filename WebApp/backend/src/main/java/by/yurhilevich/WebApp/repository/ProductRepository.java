package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String name);
    boolean existsProductByProductId(Long id);
    Optional<Product> findProductByName(String name);
    Optional<Product> findProductByProductId(Long id);
}
