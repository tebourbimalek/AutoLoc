package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.PaiementResponseDTO;

import java.util.List;

public interface IPaiementService {

    PaiementResponseDTO findById(Long id);

    List<PaiementResponseDTO> findAll();
}