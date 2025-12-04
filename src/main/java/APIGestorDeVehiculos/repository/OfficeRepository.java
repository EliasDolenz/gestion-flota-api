package APIGestorDeVehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import APIGestorDeVehiculos.domain.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {

}
