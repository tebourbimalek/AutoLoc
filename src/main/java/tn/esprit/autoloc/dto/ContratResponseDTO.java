package tn.esprit.autoloc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Données exposées au client pour un contrat.
 */
public record ContratResponseDTO(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        boolean valide,
        Long reservationId,
        List<Long> paiementIds
) {
}