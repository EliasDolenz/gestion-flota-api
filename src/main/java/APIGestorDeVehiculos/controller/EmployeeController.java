package APIGestorDeVehiculos.controller;

import APIGestorDeVehiculos.domain.Employee;
import APIGestorDeVehiculos.service.EmployeeService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/empleados")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employees = this.employeeService.findAllEmployee();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long idEmployee) {
        Optional<Employee> employeeOpt = this.employeeService.findEmployeeById(idEmployee);
        return employeeOpt.map(
                ResponseEntity::ok).orElse(
                        ResponseEntity.notFound().build());

    }

    @PutMapping("/{idEmployee}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long idEmployee,
            @RequestBody Employee detailEmployee) {
        Optional<Employee> employeeExistingOpt = this.employeeService.findEmployeeById(idEmployee);

        return employeeExistingOpt.map(employeeExisting -> {
            employeeExisting.setFirstname(detailEmployee.getFirstname());
            employeeExisting.setSurname(detailEmployee.getSurname());
            employeeExisting.setOffice(detailEmployee.getOffice());

            Employee employeeUpdate = employeeService.saveEmployee(employeeExisting);
            return ResponseEntity.ok(employeeUpdate);
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long idEmployee) {
        Boolean deleted = this.employeeService.deleteById(idEmployee);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
        Employee savedEmployee = this.employeeService.saveEmployee(newEmployee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmployee.getId()).toUri();
        return ResponseEntity.created(location).body(savedEmployee);
    }
}
