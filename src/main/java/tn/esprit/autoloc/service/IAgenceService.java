package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.AgenceRequestDTO;
import tn.esprit.autoloc.dto.AgenceResponseDTO;

import java.util.List;

public interface IAgenceService {

    AgenceResponseDTO create(AgenceRequestDTO agence);

    AgenceResponseDTO findById(Long id);

    List<AgenceResponseDTO> findAll();

    AgenceResponseDTO update(Long id, AgenceRequestDTO agence);

    void deleteById(Long id);
}
