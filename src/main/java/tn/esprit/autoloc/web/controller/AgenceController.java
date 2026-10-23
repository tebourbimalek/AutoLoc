package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agence", description = "Gestion des agences de location AutoLoc (CRUD complet)")
public class AgenceController {

    private final IAgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister toutes les agences",
            description = "Retourne la liste complète des agences enregistrées")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Liste des agences"))
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence par identifiant",
            description = "Retourne l'agence correspondante ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable",
                    content = @Content)
    })
    public Agence findById(
            @Parameter(description = "Identifiant de l'agence (idAgence)", required = true)
            @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une nouvelle agence",
            description = "Crée une agence et retourne l'entité persistée (idAgence généré)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Requête invalide (nom manquant...)",
                    content = @Content)
    })
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une agence existante",
            description = "Met à jour les champs de l'agence ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence mise à jour"),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "404", description = "Agence introuvable", content = @Content)
    })
    public Agence update(
            @Parameter(description = "Identifiant de l'agence (idAgence)", required = true)
            @PathVariable Long id,
            @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une agence",
            description = "Supprime l'agence ou 404 si introuvable")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable", content = @Content)
    })
    public void deleteById(
            @Parameter(description = "Identifiant de l'agence (idAgence)", required = true)
            @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
