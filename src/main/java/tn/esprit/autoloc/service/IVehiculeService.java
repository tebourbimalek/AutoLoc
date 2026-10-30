package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.VehiculeResponseDTO;

import java.util.List;

public interface IVehiculeService {

    VehiculeResponseDTO create(VehiculeRequestDTO vehicule);

    VehiculeResponseDTO findById(Long id);

    List<VehiculeResponseDTO> findAll();

    VehiculeResponseDTO update(Long id, VehiculeRequestDTO vehicule);

    void deleteById(Long id);
}
