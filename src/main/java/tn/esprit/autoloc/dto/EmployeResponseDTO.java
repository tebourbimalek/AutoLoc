package tn.esprit.autoloc.dto;

import tn.esprit.autoloc.domain.RoleEmploye;

/**
 * Données exposées au client pour un employé.
 */
public record EmployeResponseDTO(
        Long idEmploye,
        String nom,
        String prenom,
        RoleEmploye role,
        Long agenceId
) {
}