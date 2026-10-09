package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private static final String CONTRAT_INTROUVABLE = "Contrat introuvable: ";

    private final IContratRepository contratRepository;

    @Override
    @Transactional
    public Contrat add(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional
    public Contrat update(Contrat contrat) {
        Contrat existing = contratRepository.findById(contrat.getIdContrat())
                .orElseThrow(() -> new ResourceNotFoundException(CONTRAT_INTROUVABLE + contrat.getIdContrat()));
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.isValide());
        existing.setReservation(contrat.getReservation());
        return contratRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> findById(Long id) {
        return contratRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat findWithPaiements(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CONTRAT_INTROUVABLE + id));
        contrat.getPaiements().size();
        return contrat;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException(CONTRAT_INTROUVABLE + id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return contratRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return contratRepository.count();
    }

    @Override
    @Transactional
    public Paiement addPaiement(Long idContrat, Paiement paiement) {
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException(CONTRAT_INTROUVABLE + idContrat));
        paiement.setContrat(contrat);
        contrat.getPaiements().add(paiement);
        return paiement;
    }

    @Override
    @Transactional
    public void removePaiement(Long idContrat, Long idPaiement) {
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException(CONTRAT_INTROUVABLE + idContrat));
        Paiement cible = contrat.getPaiements().stream()
                .filter(paiement -> idPaiement.equals(paiement.getIdPaiement()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable: " + idPaiement));
        cible.setContrat(null);
        contrat.getPaiements().remove(cible);
    }
}
