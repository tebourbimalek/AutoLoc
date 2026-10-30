package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceRequestDTO;
import tn.esprit.autoloc.dto.AgenceResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDTO create(AgenceRequestDTO agence) {
        Agence agenceToSave = agenceMapper.toEntity(agence);
        if (agenceToSave.getIdAgence() != null) {
            throw new IllegalArgumentException("L'identifiant de l'agence doit être null lors de la création");
        }
        valider(agence);
        return agenceMapper.toResponseDTO(agenceRepository.save(agenceToSave));
    }

    @Override
    public AgenceResponseDTO findById(Long id) {
        return agenceMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<AgenceResponseDTO> findAll() {
        return agenceMapper.toResponseDTOList(agenceRepository.findAll());
    }

    @Override
    public AgenceResponseDTO update(Long id, AgenceRequestDTO agence) {
        Agence existing = findEntity(id);
        valider(agence);
        agenceMapper.updateEntityFromDTO(existing, agence);
        return agenceMapper.toResponseDTO(agenceRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence findEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    private void valider(AgenceRequestDTO agence) {
        if (agence.nom() == null || agence.nom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
    }
}
