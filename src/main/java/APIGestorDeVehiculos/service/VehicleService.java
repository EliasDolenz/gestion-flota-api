package APIGestorDeVehiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import APIGestorDeVehiculos.domain.Vehicle;
import APIGestorDeVehiculos.repository.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private static final Integer serviceIntervalKm = 10000;
    private static final Integer alertService = 1000;

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

    public String checkServiceAlert(Long vehicleId) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);

        if (vehicleOpt.isEmpty()) {
            return "Vehiculo no encontrado";
        }

        Vehicle vehicle = vehicleOpt.get();

        if (vehicle.getKmLastService() == null) {
            return "Ok. Falta registrar el kilometraje del último service";
        }

        Integer kmSinceLastService = vehicle.getCurrentKm() - vehicle.getKmLastService();

        Integer kmRemaining = serviceIntervalKm - (kmSinceLastService % serviceIntervalKm);

        if (kmRemaining <= alertService) {
            return "!Alerta! Faltan solo +" + kmRemaining + " km para el proximo service.";
        } else {
            return "Ok. Faltan " + kmRemaining + " km para el proximo Service";
        }
    }
}
