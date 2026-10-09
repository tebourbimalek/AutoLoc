package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;

import java.util.List;
import java.util.Optional;

public interface IEquipementService {

    Equipement add(Equipement equipement);

    Equipement update(Equipement equipement);

    Optional<Equipement> findById(Long id);

    List<Equipement> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
