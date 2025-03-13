package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    boolean existsByName(String name);
    Optional<Region> findByName(String name);
}
