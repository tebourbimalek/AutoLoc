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

import tn.esprit.autoloc.dto.ReservationRequestDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Validated
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationRequestDTO reservation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> update(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody ReservationRequestDTO reservation) {
        return ResponseEntity.ok(reservationService.update(id, reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}