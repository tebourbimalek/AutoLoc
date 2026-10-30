package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ClientRequestDTO;
import tn.esprit.autoloc.dto.ClientResponseDTO;

import java.util.List;

public interface IClientService {

    ClientResponseDTO create(ClientRequestDTO client);

    ClientResponseDTO findById(Long id);

    List<ClientResponseDTO> findAll();

    ClientResponseDTO update(Long id, ClientRequestDTO client);

    void deleteById(Long id);
}
