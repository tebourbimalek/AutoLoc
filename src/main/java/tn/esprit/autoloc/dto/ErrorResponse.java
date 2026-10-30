package tn.esprit.autoloc.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Format d'erreur standard renvoyé par GlobalExceptionHandler pour TOUTES les erreurs.
 * {@code errors} est utilisé uniquement pour les erreurs de validation (champ -> message).
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> errors
) {
}
