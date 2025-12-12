package APIGestorDeVehiculos.repository;

import java.util.List;
import java.lang.Long;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import APIGestorDeVehiculos.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r" +
            "WHERE r.vehicle.id = :vehicleId "
            + "AND r.endTime > :newStartTime "
            + "AND r.startTime < :newEndTime")
    List<Reservation> findOverlappingReservations(
            @Param("vehicleId") Long vehicleId,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime);
}
