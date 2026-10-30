package tn.esprit.autoloc.dto;

import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

/**
 * Données exposées au client pour une réservation.
 */
public record ReservationResponseDTO(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long clientId,
        Long vehiculeId,
        Long contratId
) {
}