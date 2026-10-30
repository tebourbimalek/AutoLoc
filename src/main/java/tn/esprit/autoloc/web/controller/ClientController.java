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

import tn.esprit.autoloc.dto.ClientRequestDTO;
import tn.esprit.autoloc.dto.ClientResponseDTO;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Validated
@Tag(name = "Client", description = "Gestion des clients de location AutoLoc (CRUD complet)")
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    @Operation(summary = "Lister tous les clients",
            description = "Retourne la liste complète des clients enregistrés")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Liste des clients"))
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un client par identifiant",
            description = "Retourne le client correspondant ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "400", description = "Identifiant invalide", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client introuvable", content = @Content)
    })
    public ResponseEntity<ClientResponseDTO> findById(
            @Parameter(description = "Identifiant du client (idClient)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client",
            description = "Crée un client et retourne l'entité persistée (idClient généré)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide (validation des champs)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email ou numéro de permis déjà utilisé", content = @Content)
    })
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un client existant",
            description = "Met à jour les champs du client ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client mis à jour"),
            @ApiResponse(responseCode = "400", description = "Requête invalide (validation des champs)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client introuvable", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email ou numéro de permis déjà utilisé", content = @Content)
    })
    public ResponseEntity<ClientResponseDTO> update(
            @Parameter(description = "Identifiant du client (idClient)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id,
            @Valid @RequestBody ClientRequestDTO client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client",
            description = "Supprime le client ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client introuvable", content = @Content)
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identifiant du client (idClient)", required = true)
            @PathVariable @Positive(message = "L'identifiant doit être un nombre positif") Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
