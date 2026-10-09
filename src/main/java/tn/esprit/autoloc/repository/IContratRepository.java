package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;
import java.util.Optional;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    List<Contrat> findByValideTrue();

    Optional<Contrat> findByReservationIdReservation(Long idReservation);
}
