package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratRequestDTO;
import tn.esprit.autoloc.dto.ContratResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratResponseDTO create(ContratRequestDTO contrat) {
        Contrat contratToSave = contratMapper.toEntity(contrat);
        if (contratToSave.getIdContrat() != null) {
            throw new IllegalArgumentException("L'identifiant du contrat doit être null lors de la création");
        }
        return contratMapper.toResponseDTO(contratRepository.save(contratToSave));
    }

    @Override
    public ContratResponseDTO findById(Long id) {
        return contratMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<ContratResponseDTO> findAll() {
        return contratMapper.toResponseDTOList(contratRepository.findAll());
    }

    @Override
    public ContratResponseDTO update(Long id, ContratRequestDTO contrat) {
        Contrat existing = findEntity(id);
        contratMapper.updateEntityFromDTO(existing, contrat);
        return contratMapper.toResponseDTO(contratRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private Contrat findEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }
}