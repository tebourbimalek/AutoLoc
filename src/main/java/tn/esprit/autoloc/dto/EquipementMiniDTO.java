package tn.esprit.autoloc.dto;

/**
 * Mini-DTO : les DTOS d'équipement exposés aux réponses véhicule, sans les véhicules liés.
 */
public record EquipementMiniDTO(
        Long idEquipement,
        String libelle
) {
}
