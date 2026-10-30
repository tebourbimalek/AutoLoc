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

import tn.esprit.autoloc.dto.EquipementRequestDTO;
import tn.esprit.autoloc.dto.EquipementResponseDTO;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Validated
public class EquipementController {

    private final IEquipementService equipementService;

    @GetMapping
    public ResponseEntity<List<EquipementResponseDTO>> findAll() {
        return ResponseEntity.ok(equipementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipementResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(equipementService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EquipementResponseDTO> create(@Valid @RequestBody EquipementRequestDTO equipement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementService.create(equipement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipementResponseDTO> update(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody EquipementRequestDTO equipement) {
        return ResponseEntity.ok(equipementService.update(id, equipement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        equipementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}