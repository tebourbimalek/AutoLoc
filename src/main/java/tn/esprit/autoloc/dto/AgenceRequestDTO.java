package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Données reçues en création/modification d'une agence.
 */
public record AgenceRequestDTO(

        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(max = 100, message = "Le nom de l'agence ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
        String ville,

        @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
        String adresse,

        @Pattern(regexp = "^[0-9+\\s-]{0,20}",
                message = "Le téléphone ne doit contenir que des chiffres (+, espaces et tirets acceptés, 20 caractères max)")
        String telephone
) {
}
