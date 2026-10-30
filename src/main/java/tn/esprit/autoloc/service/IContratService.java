package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ContratRequestDTO;
import tn.esprit.autoloc.dto.ContratResponseDTO;

import java.util.List;

public interface IContratService {

    ContratResponseDTO create(ContratRequestDTO contrat);

    ContratResponseDTO findById(Long id);

    List<ContratResponseDTO> findAll();

    ContratResponseDTO update(Long id, ContratRequestDTO contrat);

    void deleteById(Long id);
}