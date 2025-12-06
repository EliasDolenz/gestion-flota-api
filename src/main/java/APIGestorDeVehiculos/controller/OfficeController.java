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

import APIGestorDeVehiculos.domain.Office;
import APIGestorDeVehiculos.service.OfficeService;

@RestController
@RequestMapping("/api/sedes")
public class OfficeController {
    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<List<Office>> getAllOffice() {
        List<Office> offices = this.officeService.findAllOffice();
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{idOffice}")
    public ResponseEntity<Office> getOfficeById(@PathVariable Long idOffice) {
        Optional<Office> officeOpt = this.officeService.findOfficeById(idOffice);
        return officeOpt.map(
                ResponseEntity::ok).orElse(
                        ResponseEntity.notFound().build());

    }

    @PutMapping("/{idOffice}")
    public ResponseEntity<Office> updateOffice(@PathVariable Long idOffice,
            @RequestBody Office detailOffice) {
        Optional<Office> officeExistingOpt = this.officeService.findOfficeById(idOffice);

        return officeExistingOpt.map(officeExisting -> {
            officeExisting.setAddress(detailOffice.getAddress());
            officeExisting.setName(detailOffice.getName());

            Office officeUpdate = officeService.saveOffice(officeExisting);
            return ResponseEntity.ok(officeUpdate);
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{idOffice}")
    public ResponseEntity<Void> deleteOfficeById(@PathVariable Long idOffice) {
        Boolean deleted = this.officeService.deleteById(idOffice);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Office> createOffice(@RequestBody Office newOffice) {
        Office savedOffice = this.officeService.saveOffice(newOffice);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOffice.getId()).toUri();
        return ResponseEntity.created(location).body(savedOffice);
    }

}
