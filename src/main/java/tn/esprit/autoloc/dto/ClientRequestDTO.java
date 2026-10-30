package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Données reçues en création/modification d'un client.
 */
public record ClientRequestDTO(

        @NotBlank(message = "Le nom du client est obligatoire")
        @Size(max = 100, message = "Le nom du client ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "Le prénom du client est obligatoire")
        @Size(max = 100, message = "Le prénom du client ne doit pas dépasser 100 caractères")
        String prenom,

        @NotBlank(message = "L'email du client est obligatoire")
        @Email(message = "L'email du client doit respecter le format adresse@email.com")
        @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
        String email,

        @Pattern(regexp = "^[0-9+\\s-]{0,20}",
                message = "Le téléphone ne doit contenir que des chiffres (+, espaces et tirets acceptés, 20 caractères max)")
        String telephone,

        @NotBlank(message = "Le numéro de permis est obligatoire")
        @Size(max = 20, message = "Le numéro de permis ne doit pas dépasser 20 caractères")
        @Pattern(regexp = "^[A-Za-z0-9]{5,20}",
                message = "Le numéro de permis doit contenir entre 5 et 20 caractères alphanumériques")
        String numPermis,

        @NotNull(message = "La date d'inscription est obligatoire")
        @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
        LocalDate dateInscription
) {
}
