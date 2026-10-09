package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;
import java.util.Optional;

public interface IAgenceService {

    Agence add(Agence agence);

    Agence update(Agence agence);

    Optional<Agence> findById(Long id);

    List<Agence> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
