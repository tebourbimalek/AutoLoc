package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;
import java.util.Optional;

public interface IVehiculeService {

    Vehicule add(Vehicule vehicule);

    Vehicule update(Vehicule vehicule);

    Optional<Vehicule> findById(Long id);

    List<Vehicule> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
