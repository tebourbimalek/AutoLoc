package tn.esprit.autoloc.dto;

/**
 * Données exposées au client pour un équipement.
 */
public record EquipementResponseDTO(
        Long idEquipement,
        String libelle
) {
}