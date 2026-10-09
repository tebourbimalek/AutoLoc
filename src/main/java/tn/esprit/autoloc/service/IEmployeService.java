package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;

import java.util.List;
import java.util.Optional;

public interface IEmployeService {

    Employe add(Employe employe);

    Employe update(Employe employe);

    Optional<Employe> findById(Long id);

    List<Employe> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
