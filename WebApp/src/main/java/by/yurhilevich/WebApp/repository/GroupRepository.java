package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Group;
import by.yurhilevich.WebApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByName(String name);
    Optional<Group> findGroupByName(String name);
}
