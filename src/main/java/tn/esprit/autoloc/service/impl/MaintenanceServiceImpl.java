package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceResponseDTO create(MaintenanceRequestDTO maintenance) {
        Maintenance maintenanceToSave = maintenanceMapper.toEntity(maintenance);
        if (maintenanceToSave.getIdMaintenance() != null) {
            throw new IllegalArgumentException("L'identifiant de la maintenance doit être null lors de la création");
        }
        return maintenanceMapper.toResponseDTO(maintenanceRepository.save(maintenanceToSave));
    }

    @Override
    public MaintenanceResponseDTO findById(Long id) {
        return maintenanceMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<MaintenanceResponseDTO> findAll() {
        return maintenanceMapper.toResponseDTOList(maintenanceRepository.findAll());
    }

    @Override
    public MaintenanceResponseDTO update(Long id, MaintenanceRequestDTO maintenance) {
        Maintenance existing = findEntity(id);
        maintenanceMapper.updateEntityFromDTO(existing, maintenance);
        return maintenanceMapper.toResponseDTO(maintenanceRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private Maintenance findEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }
}