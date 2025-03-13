package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Combine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CombineRepository extends JpaRepository<Combine, Long> {
    boolean existsByName(String name);
    Optional<Combine> findByName(String name);
}
