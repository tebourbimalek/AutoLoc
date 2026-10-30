package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.MaintenanceRequestDTO;
import tn.esprit.autoloc.dto.MaintenanceResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Component
public class MaintenanceMapper {

    private final IVehiculeRepository vehiculeRepository;

    public MaintenanceMapper(IVehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public Maintenance toEntity(MaintenanceRequestDTO dto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(dto.dateDebut());
        maintenance.setDateFin(dto.dateFin());
        maintenance.setDescription(dto.description());
        appliquerRelations(maintenance, dto);
        return maintenance;
    }

    public MaintenanceResponseDTO toResponseDTO(Maintenance maintenance) {
        Long vehiculeId = maintenance.getVehicule() == null ? null : maintenance.getVehicule().getIdVehicule();
        return new MaintenanceResponseDTO(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                vehiculeId
        );
    }

    public List<MaintenanceResponseDTO> toResponseDTOList(List<Maintenance> maintenances) {
        return maintenances.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Maintenance entity, MaintenanceRequestDTO dto) {
        entity.setDateDebut(dto.dateDebut());
        entity.setDateFin(dto.dateFin());
        entity.setDescription(dto.description());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Maintenance maintenance, MaintenanceRequestDTO dto) {
        if (dto.vehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.vehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId()));
            maintenance.setVehicule(vehicule);
        } else {
            maintenance.setVehicule(null);
        }
    }
}