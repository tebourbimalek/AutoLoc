package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponseDTO findById(Long id) {
        return paiementMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<PaiementResponseDTO> findAll() {
        return paiementMapper.toResponseDTOList(paiementRepository.findAll());
    }

    private Paiement findEntity(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }
}