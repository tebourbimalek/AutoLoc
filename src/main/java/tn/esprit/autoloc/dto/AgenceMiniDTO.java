package tn.esprit.autoloc.dto;

/**
 * Mini-DTO : les DTOS d'agence exposés aux autres réponses, sans les collections.
 */
public record AgenceMiniDTO(
        Long idAgence,
        String nom,
        String ville
) {
}
