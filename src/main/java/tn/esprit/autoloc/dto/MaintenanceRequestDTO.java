package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Données reçues en création/modification d'une maintenance.
 * Le véhicule est reçu par identifiant.
 */
public record MaintenanceRequestDTO(

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotBlank(message = "La description est obligatoire")
        String description,

        // Id du véhicule (null accepté)
        Long vehiculeId
) {
}