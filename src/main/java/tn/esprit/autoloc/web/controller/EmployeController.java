package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import tn.esprit.autoloc.dto.EmployeRequestDTO;
import tn.esprit.autoloc.dto.EmployeResponseDTO;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Employe", description = "Gestion des employés AutoLoc (CRUD complet)")
public class EmployeController {

    private final IEmployeService employeService;

    @GetMapping
    @Operation(summary = "Lister tous les employés")
    public ResponseEntity<List<EmployeResponseDTO>> findAll() {
        return ResponseEntity.ok(employeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé par identifiant")
    public ResponseEntity<EmployeResponseDTO> findById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(employeService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel employé")
    public ResponseEntity<EmployeResponseDTO> create(@Valid @RequestBody EmployeRequestDTO employe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(employe));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé existant")
    public ResponseEntity<EmployeResponseDTO> update(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody EmployeRequestDTO employe) {
        return ResponseEntity.ok(employeService.update(id, employe));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        employeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}