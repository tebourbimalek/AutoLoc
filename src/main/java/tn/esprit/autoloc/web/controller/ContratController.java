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

import tn.esprit.autoloc.dto.ContratRequestDTO;
import tn.esprit.autoloc.dto.ContratResponseDTO;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Validated
public class ContratController {

    private final IContratService contratService;

    @GetMapping
    public ResponseEntity<List<ContratResponseDTO>> findAll() {
        return ResponseEntity.ok(contratService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(contratService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ContratResponseDTO> create(@Valid @RequestBody ContratRequestDTO contrat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(contrat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratResponseDTO> update(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody ContratRequestDTO contrat) {
        return ResponseEntity.ok(contratService.update(id, contrat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        contratService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}