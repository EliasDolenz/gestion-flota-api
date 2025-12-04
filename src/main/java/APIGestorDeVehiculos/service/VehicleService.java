package APIGestorDeVehiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import APIGestorDeVehiculos.domain.Vehicle;
import APIGestorDeVehiculos.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    // @Autowired No es necesario ya que la clase solo tiene 1 constructor.
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return this.vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findAllVehicles() {
        return this.vehicleRepository.findAll();
    }

    public Optional<Vehicle> findVehicleById(Long idVehicle) {
        return this.vehicleRepository.findById(idVehicle);
    }

    public Boolean deleteById(Long idVehicle) {
        if (this.vehicleRepository.existsById(idVehicle)) {
            this.vehicleRepository.deleteById(idVehicle);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
