package tn.esprit.autoloc.dto;

import java.time.LocalDate;

/**
 * Données exposées au client pour un client (sans la liste des réservations).
 */
public record ClientResponseDTO(
        Long idClient,
        String nom,
        String prenom,
        String email,
        String telephone,
        String numPermis,
        LocalDate dateInscription
) {
}
