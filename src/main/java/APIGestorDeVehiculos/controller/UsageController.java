package APIGestorDeVehiculos.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import APIGestorDeVehiculos.domain.Usage;
import APIGestorDeVehiculos.service.UsageService;

@RestController
@RequestMapping("/api/uso")
public class UsageController {
    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping
    public ResponseEntity<Usage> checkIn(@RequestBody Usage usage) {
        Usage newUsage = this.usageService.checkIn(usage);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUsage.getId())
                .toUri();

        return ResponseEntity.created(location).body(newUsage);
    }

    @PatchMapping("/{usageId}/checkout")
    public ResponseEntity<Usage> checkOut(@PathVariable Long idUsage, @RequestBody Integer endKm){
        try {
            Usage finishUsage = this.usageService.checkOut(idUsage, endKm);
            return ResponseEntity.ok(finishUsage);
        } catch (IllegalStateException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
