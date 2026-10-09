package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Vehicule add(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule update(Vehicule vehicule) {
        Vehicule existing = vehiculeRepository.findById(vehicule.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule introuvable: " + vehicule.getIdVehicule()));
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setCouleur(vehicule.getCouleur());
        existing.setPrixJour(vehicule.getPrixJour());
        existing.setDisponible(vehicule.isDisponible());
        existing.setStatut(vehicule.getStatut());
        existing.setCategorie(vehicule.getCategorie());
        existing.setAgence(vehicule.getAgence());
        return vehiculeRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule introuvable: " + id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return vehiculeRepository.count();
    }
}
