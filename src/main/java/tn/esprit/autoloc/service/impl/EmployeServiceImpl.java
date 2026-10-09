package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    @Transactional
    public Employe add(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe update(Employe employe) {
        Employe existing = employeRepository.findById(employe.getIdEmploye())
                .orElseThrow(() -> new ResourceNotFoundException("Employe introuvable: " + employe.getIdEmploye()));
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        existing.setAgence(employe.getAgence());
        return employeRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employe> findById(Long id) {
        return employeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe introuvable: " + id);
        }
        employeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return employeRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return employeRepository.count();
    }
}
