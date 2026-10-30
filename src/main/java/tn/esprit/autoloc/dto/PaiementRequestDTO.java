package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Données reçues en création/modification d'un paiement.
 * Le contrat est reçu par identifiant.
 */
public record PaiementRequestDTO(

        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être strictement positif")
        BigDecimal montant,

        @NotNull(message = "La date de paiement est obligatoire")
        LocalDate datePaiement,

        @NotNull(message = "Le mode de paiement est obligatoire")
        ModePaiement modePaiement,

        // Id du contrat (null accepté)
        Long contratId
) {
}