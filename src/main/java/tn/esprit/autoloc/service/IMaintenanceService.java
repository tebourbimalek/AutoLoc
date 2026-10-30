package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.MaintenanceResponseDTO;

import java.util.List;

public interface IMaintenanceService {

    MaintenanceResponseDTO create(MaintenanceRequestDTO maintenance);

    MaintenanceResponseDTO findById(Long id);

    List<MaintenanceResponseDTO> findAll();

    MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO maintenance);

    void deleteById(Long id);
}