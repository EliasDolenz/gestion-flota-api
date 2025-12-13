package APIGestorDeVehiculos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import APIGestorDeVehiculos.domain.Reservation;
import APIGestorDeVehiculos.service.ReservationService;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservation() {
        List<Reservation> reservations = this.reservationService.findAllReservation();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{idReservation}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long idReservation) {
        Optional<Reservation> reservationOpt = this.reservationService.findReservationById(idReservation);
        return reservationOpt.map(
                ResponseEntity::ok).orElse(
                        ResponseEntity.notFound().build());

    }

    @PutMapping("/{idReservation}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long idReservation,
            @RequestBody Reservation detailReservation) {
        Optional<Reservation> reservationExistingOpt = this.reservationService.findReservationById(idReservation);

        return reservationExistingOpt.map(reservationExisting -> {
            reservationExisting.setEmployee(detailReservation.getEmployee());
            reservationExisting.setEndTime(detailReservation.getEndTime());
            reservationExisting.setStartTime(detailReservation.getStartTime());
            reservationExisting.setVehicle(detailReservation.getVehicle());

            Reservation reservationUpdate = this.reservationService.saveReservation(reservationExisting);
            return ResponseEntity.ok(reservationUpdate);
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{idReservation}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long idReservation) {
        Boolean deleted = this.reservationService.deleteById(idReservation);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation newReservation) {
        Reservation savedReservation = this.reservationService.saveReservation(newReservation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedReservation.getIdReservation())
                .toUri();
        return ResponseEntity.created(location).body(savedReservation);
    }
}
