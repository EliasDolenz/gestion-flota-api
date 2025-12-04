package APIGestorDeVehiculos.controller;

import APIGestorDeVehiculos.domain.Employee;
import APIGestorDeVehiculos.service.EmployeeService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employees = this.employeeService.finAllEmployee();
        return ResponseEntity.ok(employees);
    }
}
