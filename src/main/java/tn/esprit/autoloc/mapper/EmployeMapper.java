package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeRequestDTO;
import tn.esprit.autoloc.dto.EmployeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Component
public class EmployeMapper {

    private final IAgenceRepository agenceRepository;

    public EmployeMapper(IAgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public Employe toEntity(EmployeRequestDTO dto) {
        Employe employe = new Employe();
        employe.setNom(dto.nom());
        employe.setPrenom(dto.prenom());
        employe.setRole(dto.role());
        appliquerRelations(employe, dto);
        return employe;
    }

    public EmployeResponseDTO toResponseDTO(Employe employe) {
        Long agenceId = employe.getAgence() == null ? null : employe.getAgence().getIdAgence();
        return new EmployeResponseDTO(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                agenceId
        );
    }

    public List<EmployeResponseDTO> toResponseDTOList(List<Employe> employes) {
        return employes.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Employe entity, EmployeRequestDTO dto) {
        entity.setNom(dto.nom());
        entity.setPrenom(dto.prenom());
        entity.setRole(dto.role());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Employe employe, EmployeRequestDTO dto) {
        if (dto.agenceId() != null) {
            Agence agence = agenceRepository.findById(dto.agenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId()));
            employe.setAgence(agence);
        } else {
            employe.setAgence(null);
        }
    }
}