package tn.esprit.autoloc.dto;

/**
 * Données exposées au client pour une agence (sans collections ni entité JPA).
 */
public record AgenceResponseDTO(
        Long idAgence,
        String nom,
        String ville,
        String adresse,
        String telephone
) {
}
