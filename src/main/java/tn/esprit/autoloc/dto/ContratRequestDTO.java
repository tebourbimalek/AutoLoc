package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Données reçues en création/modification d'un contrat.
 * La réservation est reçue par identifiant.
 */
public record ContratRequestDTO(

        @NotNull(message = "La date de signature est obligatoire")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant total doit être strictement positif")
        BigDecimal montantTotal,

        boolean valide,

        // Id de la réservation (null accepté)
        Long reservationId
) {
}