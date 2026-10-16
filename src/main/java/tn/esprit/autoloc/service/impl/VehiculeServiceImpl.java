package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("L'identifiant du véhicule doit être null lors de la création");
        }
        valider(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existing = findById(id);
        valider(vehicule);
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
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void valider(Vehicule vehicule) {
        if (vehicule.getPrixJour() < 0) {
            throw new IllegalArgumentException("Le prix journalier du véhicule ne peut pas être négatif");
        }
    }
}
