package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.autoloc.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Validated
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> findAll() {
        return ResponseEntity.ok(maintenanceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(maintenanceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> create(@Valid @RequestBody MaintenanceRequestDTO maintenance) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.create(maintenance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> update(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody MaintenanceRequestDTO maintenance) {
        return ResponseEntity.ok(maintenanceService.update(id, maintenance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        maintenanceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}