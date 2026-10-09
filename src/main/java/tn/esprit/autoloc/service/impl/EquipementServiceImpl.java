package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    @Transactional
    public Equipement add(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    @Transactional
    public Equipement update(Equipement equipement) {
        Equipement existing = equipementRepository.findById(equipement.getIdEquipement())
                .orElseThrow(() -> new ResourceNotFoundException("Equipement introuvable: " + equipement.getIdEquipement()));
        existing.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipement> findById(Long id) {
        return equipementRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement introuvable: " + id);
        }
        equipementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return equipementRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return equipementRepository.count();
    }
}
