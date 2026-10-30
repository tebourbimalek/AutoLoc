package tn.esprit.autoloc.dto;

import java.time.LocalDate;

/**
 * Données exposées au client pour une maintenance.
 */
public record MaintenanceResponseDTO(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId
) {
}