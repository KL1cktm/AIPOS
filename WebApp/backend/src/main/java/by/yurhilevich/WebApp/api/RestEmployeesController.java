package by.yurhilevich.WebApp.api;

import by.yurhilevich.WebApp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestEmployeesController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/get_employees")
    public ResponseEntity<List<String>> getEmployees() {
        try {
            List<String> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.noContent().build(); // Возвращаем статус 204, если список пуст
            }
            return ResponseEntity.ok(employees); // Возвращаем список групп со статусом 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500
        }
    }
}
