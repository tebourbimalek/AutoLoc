package tn.esprit.autoloc.web.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.autoloc.dto.PaiementResponseDTO;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Validated
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> findAll() {
        return ResponseEntity.ok(paiementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(paiementService.findById(id));
    }
}