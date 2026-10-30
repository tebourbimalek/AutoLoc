package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.util.List;

/**
 * Données reçues en création/modification d'un véhicule.
 * Les relations sont reçues par identifiant (agenceId, equipementIds), jamais comme entités.
 */
public record VehiculeRequestDTO(

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
        String modele,

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,

        @Size(max = 30, message = "La couleur ne doit pas dépasser 30 caractères")
        String couleur,

        @Positive(message = "Le prix journalier doit être un nombre strictement positif")
        double prixJour,

        boolean disponible,

        @NotNull(message = "Le statut du véhicule est obligatoire")
        StatutVehicule statut,

        @NotNull(message = "La catégorie du véhicule est obligatoire")
        CategorieVehicule categorie,

        // Id de l'agence de rattachement (null accepté), pas l'entité Agence
        Long agenceId,

        // Ids des équipements désirés (peut être vide ou null), pas les entités Equipement
        List<Long> equipementIds
) {
}
