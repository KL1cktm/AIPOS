package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeService {
    List<String> getAllEmployees();

    List<Employee> getAll();

    boolean addEmployee(String fio, String position, String combine);

    boolean updateEmployee(Long id, String fio, String position, String combine);

    void deleteEmployee(Long id);
}
