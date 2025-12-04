package APIGestorDeVehiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import APIGestorDeVehiculos.domain.Employee;
import APIGestorDeVehiculos.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public List<Employee> finAllEmployee() {
        return this.employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeById(Long idEmployee) {
        return this.employeeRepository.findById(idEmployee);
    }

    public Boolean deleteById(Long idEmployee) {
        if (this.employeeRepository.existsById(idEmployee)) {
            this.employeeRepository.deleteById(idEmployee);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
