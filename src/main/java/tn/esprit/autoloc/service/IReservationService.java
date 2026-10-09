package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

    Reservation add(Reservation reservation);

    Reservation update(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
