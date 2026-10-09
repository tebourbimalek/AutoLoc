package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.RoleEmploye;

import java.util.List;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByRole(RoleEmploye role);

    List<Employe> findByAgenceIdAgence(Long idAgence);

    List<Employe> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
