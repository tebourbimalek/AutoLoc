package tn.esprit.autoloc.dto;

import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Données exposées au client pour un paiement.
 */
public record PaiementResponseDTO(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement,
        Long contratId
) {
}