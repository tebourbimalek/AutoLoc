package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;

import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

/**
 * Données reçues en création/modification d'une réservation.
 * Le client et le véhicule sont reçus par identifiant.
 */
public record ReservationRequestDTO(

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut de la réservation est obligatoire")
        StatutReservation statut,

        // Id du client (null accepté)
        Long clientId,

        // Id du véhicule (null accepté)
        Long vehiculeId
) {
}