package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    Optional<Vehicule> findByImmatriculation(String immatriculation);

    List<Vehicule> findByDisponibleTrue();

    List<Vehicule> findByMarqueIgnoreCase(String marque);
}
