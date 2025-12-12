package APIGestorDeVehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import APIGestorDeVehiculos.domain.Usage;

public interface UsageRepository extends JpaRepository<Usage, Long> {

}
