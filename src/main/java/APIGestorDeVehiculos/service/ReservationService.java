package APIGestorDeVehiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import APIGestorDeVehiculos.domain.Reservation;
import APIGestorDeVehiculos.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservation() {
        return this.reservationRepository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(
                reservation.getVehicle().getId(),
                reservation.getStartTime(),
                reservation.getEndTime());

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("El vehiculo ya está reservado");
        }
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> findReservationById(Long idReservation) {
        return this.reservationRepository.findById(idReservation);
    }

    public Boolean deleteById(Long idReservation) {
        if (this.reservationRepository.existsById(idReservation)) {
            this.reservationRepository.deleteById(idReservation);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
