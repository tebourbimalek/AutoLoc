package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.autoloc.dto.VehiculeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDTO create(VehiculeRequestDTO vehicule) {
        Vehicule vehiculeToSave = vehiculeMapper.toEntity(vehicule);
        if (vehiculeToSave.getIdVehicule() != null) {
            throw new IllegalArgumentException("L'identifiant du véhicule doit être null lors de la création");
        }
        valider(vehicule);
        return vehiculeMapper.toResponseDTO(vehiculeRepository.save(vehiculeToSave));
    }

    @Override
    public VehiculeResponseDTO findById(Long id) {
        return vehiculeMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<VehiculeResponseDTO> findAll() {
        return vehiculeMapper.toResponseDTOList(vehiculeRepository.findAll());
    }

    @Override
    public VehiculeResponseDTO update(Long id, VehiculeRequestDTO vehicule) {
        Vehicule existing = findEntity(id);
        valider(vehicule);
        vehiculeMapper.updateEntityFromDTO(existing, vehicule);
        return vehiculeMapper.toResponseDTO(vehiculeRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private Vehicule findEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private void valider(VehiculeRequestDTO vehicule) {
        if (vehicule.prixJour() < 0) {
            throw new IllegalArgumentException("Le prix journalier du véhicule ne peut pas être négatif");
        }
    }
}
