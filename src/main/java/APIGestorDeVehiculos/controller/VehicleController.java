package APIGestorDeVehiculos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import APIGestorDeVehiculos.domain.Vehicle;
import APIGestorDeVehiculos.service.VehicleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/vehiculos")
public class VehicleController {

    private final VehicleService vehicleService;

    // @Autowired No es necesario ya que la clase solo tiene 1 constructor.
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = this.vehicleService.findAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{idVehicle}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long idVehicle) {
        Optional<Vehicle> vehicleOpt = this.vehicleService.findVehicleById(idVehicle);
        return vehicleOpt.map(
                ResponseEntity::ok).orElse(
                        ResponseEntity.notFound().build());
    }

    @PutMapping("/{idVehicle}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long idVehicle, @RequestBody Vehicle detailVehicle) {
        Optional<Vehicle> vehicleExistingOpt = this.vehicleService.findVehicleById(idVehicle);

        return vehicleExistingOpt.map(vehicleExisting -> {
            vehicleExisting.setEmployee(detailVehicle.getEmployee());
            vehicleExisting.setCurrentKm(detailVehicle.getCurrentKm());
            vehicleExisting.setDataLastService(detailVehicle.getDataLastService());
            vehicleExisting.setExpirationDateVtv(detailVehicle.getExpirationDateVtv());
            vehicleExisting.setLicensePlate(detailVehicle.getLicensePlate());

            Vehicle vehicleUpdate = vehicleService.saveVehicle(vehicleExisting);
            return ResponseEntity.ok(vehicleUpdate);
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });

    }

    @DeleteMapping("/{idVehicle}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long idVehicle) {
        Boolean deleted = this.vehicleService.deleteById(idVehicle);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle newVehicle) {
        Vehicle savedVehicle = this.vehicleService.saveVehicle(newVehicle);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVehicle.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedVehicle);
    }

    @GetMapping("/{idVehicle}/service-alert")
    public ResponseEntity<String> getServicealert(@PathVariable Long idVehicle) {
        String mesaggeAlert = vehicleService.checkServiceAlert(idVehicle);

        if (mesaggeAlert.contains("Alerta")) {
            return ResponseEntity.status(409).body(mesaggeAlert);
        }
        return ResponseEntity.ok(mesaggeAlert);
    }
}
