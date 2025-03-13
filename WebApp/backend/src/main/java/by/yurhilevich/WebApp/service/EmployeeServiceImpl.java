package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Employee;
import by.yurhilevich.WebApp.models.Group;
import by.yurhilevich.WebApp.repository.CombineRepository;
import by.yurhilevich.WebApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CombineRepository combineRepository;

    public List<String> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll( );
        List<String> employeesFio = employees.stream()
                .map(Employee::getFio)
                .collect(Collectors.toList());

        System.out.println(employeesFio);
        return employeesFio;
    }

    @Override
    public List<Employee> getAll(){
        return employeeRepository.findAll( );
    }

    @Override
    public boolean addEmployee(String fio, String position, String combineName) {
        if (!employeeRepository.existsByFio(fio) && combineRepository.existsByName(combineName)) {
            Employee employee = new Employee();
            employee.setFio(fio);
            employee.setPosition(position);
            employee.setCombine(combineRepository.findByName(combineName).get());
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEmployee(Long id, String fio, String position, String combine) {
        if (employeeRepository.existsById(id) && combineRepository.existsByName(combine)) {
            Employee employee = employeeRepository.findById(id).get( );
            employee.setFio(fio);
            employee.setPosition(position);
            employee.setCombine(combineRepository.findByName(combine).get());
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
