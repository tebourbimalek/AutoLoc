package tn.esprit.autoloc.dto;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.util.List;

/**
 * Données exposées au client pour un véhicule.
 * Les relations sont exposées par identifiant (agenceId, equipementIds),
 * jamais comme entités JPA, pour rester simple et éviter les références circulaires.
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
        Long agenceId,
        List<Long> equipementIds
) {
}