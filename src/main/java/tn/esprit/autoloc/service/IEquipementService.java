package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.EquipementRequestDTO;
import tn.esprit.autoloc.dto.EquipementResponseDTO;

import java.util.List;

public interface IEquipementService {

    EquipementResponseDTO create(EquipementRequestDTO equipement);

    EquipementResponseDTO findById(Long id);

    List<EquipementResponseDTO> findAll();

    EquipementResponseDTO update(Long id, EquipementRequestDTO equipement);

    void deleteById(Long id);
}