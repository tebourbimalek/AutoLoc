package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.EquipementMiniDTO;
import tn.esprit.autoloc.dto.AgenceMiniDTO;
import tn.esprit.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;

import java.util.List;

/**
 * Mapper manuel Vehicule <-> DTOs.
 * Résout les relations par identifiant (agenceId, equipementIds) via les repositories.
 */
@Component
public class VehiculeMapper {

    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeMapper(IAgenceRepository agenceRepository, IEquipementRepository equipementRepository) {
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
    }

    /** Convertit les données reçues en entité, en résolvant les relations par identifiant. */
    public Vehicule toEntity(VehiculeRequestDTO dto) {
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setCouleur(dto.couleur());
        vehicule.setPrixJour(dto.prixJour());
        vehicule.setDisponible(dto.disponible());
        vehicule.setStatut(dto.statut());
        vehicule.setCategorie(dto.categorie());
        appliquerRelations(vehicule, dto);
        return vehicule;
    }

    /** Expose un véhicule en DTO de réponse (mini-DTO pour les relations, pas d'entité). */
    public VehiculeResponseDTO toResponseDTO(Vehicule vehicule) {
        AgenceMiniDTO agence = null;
        Agence a = vehicule.getAgence();
        if (a != null) {
            agence = new AgenceMiniDTO(a.getIdAgence(), a.getNom(), a.getVille());
        }
        List<EquipementMiniDTO> equipements = vehicule.getEquipements() == null ? List.of()
                : vehicule.getEquipements().stream()
                        .map(e -> new EquipementMiniDTO(e.getIdEquipement(), e.getLibelle()))
                        .toList();
        return new VehiculeResponseDTO(
                vehicule.getIdVehicule(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getImmatriculation(),
                vehicule.getCouleur(),
                vehicule.getPrixJour(),
                vehicule.isDisponible(),
                vehicule.getStatut(),
                vehicule.getCategorie(),
                agence,
                equipements
        );
    }

    public List<VehiculeResponseDTO> toResponseDTOList(List<Vehicule> vehicules) {
        return vehicules.stream().map(this::toResponseDTO).toList();
    }

    /** Reporte les champs et les relations du DTO sur l'entité existante (mise à jour). */
    public void updateEntityFromDTO(Vehicule entity, VehiculeRequestDTO dto) {
        entity.setMarque(dto.marque());
        entity.setModele(dto.modele());
        entity.setImmatriculation(dto.immatriculation());
        entity.setCouleur(dto.couleur());
        entity.setPrixJour(dto.prixJour());
        entity.setDisponible(dto.disponible());
        entity.setStatut(dto.statut());
        entity.setCategorie(dto.categorie());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Vehicule vehicule, VehiculeRequestDTO dto) {
        // Relation agence : l'id 404 est géré comme pour un GET sur l'agence absente
        if (dto.agenceId() != null) {
            Agence agence = agenceRepository.findById(dto.agenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId()));
            vehicule.setAgence(agence);
        } else {
            vehicule.setAgence(null);
        }
        // Relation équipements : liste vide ou ids ; un id inconnu est signalé (400)
        if (dto.equipementIds() == null || dto.equipementIds().isEmpty()) {
            if (vehicule.getEquipements() != null) {
                vehicule.getEquipements().clear();
            }
        } else {
            List<Equipement> equipements = equipementRepository.findAllById(dto.equipementIds());
            if (equipements.size() != dto.equipementIds().size()) {
                throw new IllegalArgumentException("Certains équipements sont inconnus : " + dto.equipementIds());
            }
            if (vehicule.getEquipements() != null) {
                vehicule.getEquipements().clear();
                vehicule.getEquipements().addAll(equipements);
            } else {
                vehicule.setEquipements(equipements);
            }
        }
    }
}
