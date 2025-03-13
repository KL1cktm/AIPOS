package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByFio(String fio);
    Optional<Employee> findByFio(String fio);
}
