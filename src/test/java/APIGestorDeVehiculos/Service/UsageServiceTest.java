package APIGestorDeVehiculos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import APIGestorDeVehiculos.domain.Usage;
import APIGestorDeVehiculos.domain.Vehicle;
import APIGestorDeVehiculos.repository.UsageRepository;
import APIGestorDeVehiculos.repository.VehicleRepository;
import APIGestorDeVehiculos.service.UsageService;
import APIGestorDeVehiculos.service.VehicleService;

@ExtendWith(MockitoExtension.class)
public class UsageServiceTest {

    @Mock
    private UsageRepository usageRepository;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private UsageService usageService;

    private Usage mockUsage;
    private Vehicle mockVehicle;

    @BeforeEach
    void setUp() {
        mockVehicle = new Vehicle();
        mockVehicle.setId(1L);
        mockVehicle.setCurrentKm(10000);

        mockUsage = new Usage();
        mockUsage.setId(5L);
        mockUsage.setVehicle(mockVehicle);
        mockUsage.setStartKm(10000);
        mockUsage.setStartTime(LocalDateTime.now().minusHours(1));
    }

    @Test
    void checkOut_ShouldThrowException_WhenEndKmIsLessThanStartKm() {
        Long usageId = 5L;
        Integer invalidEndKm = 9999;

        when(usageRepository.findById(usageId)).thenReturn(Optional.of(mockUsage));

        assertThrows(IllegalArgumentException.class,
                () -> usageService.checkOut(usageId, invalidEndKm),
                "Deberia lanzar IllegalArgumentException cuand endKm es menor que StartKm");

        verify(usageRepository, never()).save(any(Usage.class));
        verify(vehicleService, never()).saveVehicle(any(Vehicle.class));

    }

    @Test
    void ckeckOut_ShouldCompleteSuccefully_WhenDataIsValide() {
        Long usageId = 5L;
        Integer validEndKm = 10100;

        when(usageRepository.findById(usageId)).thenReturn(Optional.of(mockUsage));

        when(usageRepository.save(any(Usage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usage result = usageService.checkOut(usageId, validEndKm);

        assertEquals(validEndKm, result.getEndKm());
        assertEquals(validEndKm, mockVehicle.getCurrentKm());

        verify(usageRepository, times(1)).save(mockUsage);
        verify(vehicleService, times(1)).saveVehicle(mockVehicle);
    }

}
