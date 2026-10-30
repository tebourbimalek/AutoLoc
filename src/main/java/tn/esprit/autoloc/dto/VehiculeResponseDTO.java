package tn.esprit.autoloc.dto;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.util.List;

/**
 * Données exposées au client pour un véhicule.
 * L'agence et les équipements sont exposés sous forme de mini-DTO (pas d'entité JPA),
 * aucune référence circulaire possible.
 */
public record VehiculeResponseDTO(
        Long idVehicule,
        String marque,
        String modele,
        String immatriculation,
        String couleur,
        double prixJour,
        boolean disponible,
        StatutVehicule statut,
        CategorieVehicule categorie,
        AgenceMiniDTO agence,              // mini-DTO ou null
        List<EquipementMiniDTO> equipements
) {
}
