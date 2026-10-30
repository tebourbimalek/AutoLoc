package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceRequestDTO;
import tn.esprit.autoloc.dto.AgenceResponseDTO;

import java.util.List;

/**
 * Mapper manuel Agence <-> DTOs.
 */
@Component
public class AgenceMapper {

    /** Convertit les données reçues en entité (associé à un id null pour la création). */
    public Agence toEntity(AgenceRequestDTO dto) {
        Agence agence = new Agence();
        agence.setNom(dto.nom());
        agence.setVille(dto.ville());
        agence.setAdresse(dto.adresse());
        agence.setTelephone(dto.telephone());
        return agence;
    }

    /** Expose une agence en DTO de réponse (sans entité JPA). */
    public AgenceResponseDTO toResponseDTO(Agence agence) {
        return new AgenceResponseDTO(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone()
        );
    }

    public List<AgenceResponseDTO> toResponseDTOList(List<Agence> agences) {
        return agences.stream().map(this::toResponseDTO).toList();
    }

    /** Reporte les champs modifiables du DTO sur l'entité existante (mise à jour). */
    public void updateEntityFromDTO(Agence entity, AgenceRequestDTO dto) {
        entity.setNom(dto.nom());
        entity.setVille(dto.ville());
        entity.setAdresse(dto.adresse());
        entity.setTelephone(dto.telephone());
    }
}
