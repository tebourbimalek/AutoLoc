package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementRequestDTO;
import tn.esprit.autoloc.dto.EquipementResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementResponseDTO create(EquipementRequestDTO equipement) {
        Equipement equipementToSave = equipementMapper.toEntity(equipement);
        if (equipementToSave.getIdEquipement() != null) {
            throw new IllegalArgumentException("L'identifiant de l'équipement doit être null lors de la création");
        }
        return equipementMapper.toResponseDTO(equipementRepository.save(equipementToSave));
    }

    @Override
    public EquipementResponseDTO findById(Long id) {
        return equipementMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<EquipementResponseDTO> findAll() {
        return equipementMapper.toResponseDTOList(equipementRepository.findAll());
    }

    @Override
    public EquipementResponseDTO update(Long id, EquipementRequestDTO equipement) {
        Equipement existing = findEntity(id);
        equipementMapper.updateEntityFromDTO(existing, equipement);
        return equipementMapper.toResponseDTO(equipementRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement findEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}