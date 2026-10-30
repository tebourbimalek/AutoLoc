package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementRequestDTO;
import tn.esprit.autoloc.dto.EquipementResponseDTO;

import java.util.List;

@Component
public class EquipementMapper {

    public Equipement toEntity(EquipementRequestDTO dto) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(dto.libelle());
        return equipement;
    }

    public EquipementResponseDTO toResponseDTO(Equipement equipement) {
        return new EquipementResponseDTO(
                equipement.getIdEquipement(),
                equipement.getLibelle()
        );
    }

    public List<EquipementResponseDTO> toResponseDTOList(List<Equipement> equipements) {
        return equipements.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Equipement entity, EquipementRequestDTO dto) {
        entity.setLibelle(dto.libelle());
    }
}