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

import tn.esprit.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Validated
@Tag(name = "Vehicule", description = "Gestion des véhicules de location AutoLoc (CRUD complet)")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister tous les véhicules",
            description = "Retourne la liste complète des véhicules enregistrés")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Liste des véhicules"))
    public ResponseEntity<List<VehiculeResponseDTO>> findAll() {
        return ResponseEntity.ok(vehiculeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule par identifiant",
            description = "Retourne le véhicule correspondant ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "400", description = "Identifiant invalide", content = @Content),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    })
    public ResponseEntity<VehiculeResponseDTO> findById(
            @Parameter(description = "Identifiant du véhicule (idVehicule)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau véhicule",
            description = "Crée un véhicule et retourne l'entité persistée (idVehicule généré)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Véhicule créé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide (prix négatif, statut manquant...)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Immatriculation déjà utilisée", content = @Content)
    })
    public ResponseEntity<VehiculeResponseDTO> create(@Valid @RequestBody VehiculeRequestDTO vehicule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(vehicule));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule existant",
            description = "Met à jour les champs du véhicule ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule mis à jour"),
            @ApiResponse(responseCode = "400", description = "Requête invalide (validation des champs)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content),
            @ApiResponse(responseCode = "409", description = "Immatriculation déjà utilisée", content = @Content)
    })
    public ResponseEntity<VehiculeResponseDTO> update(
            @Parameter(description = "Identifiant du véhicule (idVehicule)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody VehiculeRequestDTO vehicule) {
        return ResponseEntity.ok(vehiculeService.update(id, vehicule));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule",
            description = "Supprime le véhicule ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Véhicule supprimé"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identifiant du véhicule (idVehicule)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        vehiculeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
