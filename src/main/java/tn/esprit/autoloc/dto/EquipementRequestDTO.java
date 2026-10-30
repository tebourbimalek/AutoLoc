package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Données reçues en création/modification d'un équipement.
 */
public record EquipementRequestDTO(

        @NotBlank(message = "Le libellé de l'équipement est obligatoire")
        String libelle
) {
}