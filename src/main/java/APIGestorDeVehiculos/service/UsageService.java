package APIGestorDeVehiculos.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import APIGestorDeVehiculos.domain.Usage;
import APIGestorDeVehiculos.domain.Vehicle;
import APIGestorDeVehiculos.repository.UsageRepository;

@Service
public class UsageService {
    private final UsageRepository usageRepository;
    private final VehicleService vehicleService;

    public UsageService(UsageRepository usageRepository, VehicleService vehicleService) {
        this.usageRepository = usageRepository;
        this.vehicleService = vehicleService;
    }

    public Usage checkIn(Usage newUsage) {
        if (newUsage.getVehicle() == null) {
            throw new IllegalArgumentException("Vehiculo no valido para el check-in.");
        }
        newUsage.setStartTime(LocalDateTime.now());

        return this.usageRepository.save(newUsage);
    }

    @Transactional
    public Usage checkOut(Long usageId, Integer endKm) {
        Usage usage = this.usageRepository.findById(usageId)
                .orElseThrow(() -> new IllegalStateException("Uso no encontrado o ya finalizado."));

        if (usage.getEndTime() != null) {
            throw new IllegalStateException("El uso ya ha sido finalizado");
        }
        if (endKm <= usage.getStartKm()) {
            throw new IllegalArgumentException("El kilometraje final debe ser mayor que el inicial");
        }

        usage.setEndKm(endKm);
        usage.setEndTime(LocalDateTime.now());
        Usage finishedUsage = usageRepository.save(usage);

        Vehicle vehicle = finishedUsage.getVehicle();
        vehicle.setCurrentKm(endKm);
        vehicleService.saveVehicle(vehicle);

        return finishedUsage;
    }
}
