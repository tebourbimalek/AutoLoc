package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;

import java.util.List;
import java.util.Optional;

public interface IContratService {

    Contrat add(Contrat contrat);

    Contrat update(Contrat contrat);

    Optional<Contrat> findById(Long id);

    /**
     * Charge un contrat en initialisant sa collection de paiements (utile hors transaction web).
     */
    Contrat findWithPaiements(Long id);

    List<Contrat> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();

    /**
     * Ajoute un paiement au contrat (cascade PERSIST) et renvoie le paiement persiste.
     */
    Paiement addPaiement(Long idContrat, Paiement paiement);

    /**
     * retire un paiement du contrat (orphanRemoval) ce qui le supprime.
     */
    void removePaiement(Long idContrat, Long idPaiement);
}
