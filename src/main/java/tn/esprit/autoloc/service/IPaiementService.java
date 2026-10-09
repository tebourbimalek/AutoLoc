package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;
import java.util.Optional;

public interface IPaiementService {

    Paiement add(Paiement paiement);

    Paiement update(Paiement paiement);

    Optional<Paiement> findById(Long id);

    List<Paiement> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
