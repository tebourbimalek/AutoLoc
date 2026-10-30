package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import tn.esprit.autoloc.domain.RoleEmploye;

/**
 * Données reçues en création/modification d'un employé.
 * L'agence de rattachement est reçue par identifiant.
 */
public record EmployeRequestDTO(

        @NotBlank(message = "Le nom de l'employé est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom de l'employé est obligatoire")
        String prenom,

        @NotNull(message = "Le rôle de l'employé est obligatoire")
        RoleEmploye role,

        // Id de l'agence de rattachement (null accepté)
        Long agenceId
) {
}