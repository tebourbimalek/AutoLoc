package tn.esprit.autoloc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Demonstration de la couche Repository/Service (CRUD, tri, pagination, cascade).
 * Active uniquement avec le profil "demo".
 */
@Component
@Profile("demo")
@RequiredArgsConstructor
@Slf4j
public class DemoCrudRunner implements CommandLineRunner {

    private final IVehiculeService vehiculeService;
    private final IClientService clientService;
    private final IContratService contratService;
    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;

    @Override
    public void run(String... args) {
        log.info("===== DEMO CRUD : DEBUT =====");

        Vehicule savedVehicule = vehiculeService.add(buildVehicule(
                "Renault", "Clio", "TU-123-AB", 120.0, StatutVehicule.DISPONIBLE, CategorieVehicule.CITADINE));
        log.info("CREATE Vehicule -> id={} immatriculation={}", savedVehicule.getIdVehicule(), savedVehicule.getImmatriculation());

        Client savedClient = clientService.add(buildClient(
                "Ben Ali", "Sami", "sami.benali@autoloc.tn", "PERM-001"));
        log.info("CREATE Client -> id={} email={}", savedClient.getIdClient(), savedClient.getEmail());

        vehiculeService.findById(savedVehicule.getIdVehicule())
                .ifPresent(v -> log.info("READ Vehicule -> id={} marque={} modele={}", v.getIdVehicule(), v.getMarque(), v.getModele()));
        clientService.findById(savedClient.getIdClient())
                .ifPresent(c -> log.info("READ Client -> id={} nom={} prenom={}", c.getIdClient(), c.getNom(), c.getPrenom()));
        log.info("READ ALL Vehicules -> {} enregistrement(s)", vehiculeService.findAll().size());

        savedVehicule.setPrixJour(150.0);
        savedVehicule.setStatut(StatutVehicule.LOUE);
        savedVehicule.setDisponible(false);
        Vehicule updatedVehicule = vehiculeService.update(savedVehicule);
        log.info("UPDATE Vehicule -> id={} prixJour={} statut={}", updatedVehicule.getIdVehicule(), updatedVehicule.getPrixJour(), updatedVehicule.getStatut());

        savedClient.setTelephone("20654321");
        Client updatedClient = clientService.update(savedClient);
        log.info("UPDATE Client -> id={} telephone={}", updatedClient.getIdClient(), updatedClient.getTelephone());

        Vehicule vehicule2 = vehiculeService.add(buildVehicule(
                "Peugeot", "208", "TU-456-CD", 200.0, StatutVehicule.DISPONIBLE, CategorieVehicule.BERLINE));
        Vehicule vehicule3 = vehiculeService.add(buildVehicule(
                "Volkswagen", "Golf", "TU-789-EF", 180.0, StatutVehicule.DISPONIBLE, CategorieVehicule.SUV));

        List<Vehicule> triees = vehiculeRepository.findAll(Sort.by("prixJour").ascending());
        log.info("FIND ALL (Sort by prixJour asc) -> {}", triees.stream().map(Vehicule::getImmatriculation).toList());

        Page<Vehicule> page = vehiculeRepository.findAll(PageRequest.of(0, 2, Sort.by("prixJour").descending()));
        log.info("PAGE 0 (size=2, prixJour desc) -> totalElements={} totalPages={} contenu={}",
                page.getTotalElements(), page.getTotalPages(),
                page.map(Vehicule::getImmatriculation).getContent());

        Vehicule flushedVehicule = vehiculeRepository.saveAndFlush(buildVehicule(
                "Dacia", "Duster", "TU-999-ZZ", 220.0, StatutVehicule.DISPONIBLE, CategorieVehicule.SUV));
        log.info("saveAndFlush -> id={} immatriculation={}", flushedVehicule.getIdVehicule(), flushedVehicule.getImmatriculation());

        log.info("existsById({}) -> {}", updatedVehicule.getIdVehicule(), vehiculeRepository.existsById(updatedVehicule.getIdVehicule()));
        log.info("count() Vehicules -> {}", vehiculeRepository.count());
        log.info("count() Clients -> {}", clientRepository.count());

        Contrat savedContrat = contratService.add(buildContrat(new BigDecimal("300.00")));
        log.info("CREATE Contrat -> id={} montantTotal={}", savedContrat.getIdContrat(), savedContrat.getMontantTotal());

        Paiement paiement = buildPaiement(new BigDecimal("100.00"));
        contratService.addPaiement(savedContrat.getIdContrat(), paiement);
        log.info("CASCADE addPaiement -> paiementId={} contratId={}", paiement.getIdPaiement(), savedContrat.getIdContrat());
        log.info("Contrat {} possede {} paiement(s)", savedContrat.getIdContrat(),
                contratService.findWithPaiements(savedContrat.getIdContrat()).getPaiements().size());

        contratService.removePaiement(savedContrat.getIdContrat(), paiement.getIdPaiement());
        log.info("ORPHAN REMOVE -> {} paiement(s) restant(s)",
                contratService.findWithPaiements(savedContrat.getIdContrat()).getPaiements().size());
        contratService.deleteById(savedContrat.getIdContrat());

        vehiculeService.deleteById(updatedVehicule.getIdVehicule());
        vehiculeService.deleteById(vehicule2.getIdVehicule());
        vehiculeService.deleteById(vehicule3.getIdVehicule());
        vehiculeService.deleteById(flushedVehicule.getIdVehicule());
        clientService.deleteById(updatedClient.getIdClient());
        log.info("DELETE Vehicule et Client -> count Vehicules={} count Clients={}", vehiculeRepository.count(), clientRepository.count());

        log.info("===== DEMO CRUD : FIN =====");
    }

    private Vehicule buildVehicule(String marque, String modele, String immatriculation, double prixJour,
                                   StatutVehicule statut, CategorieVehicule categorie) {
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(marque);
        vehicule.setModele(modele);
        vehicule.setImmatriculation(immatriculation);
        vehicule.setCouleur("Blanc");
        vehicule.setPrixJour(prixJour);
        vehicule.setDisponible(statut == StatutVehicule.DISPONIBLE);
        vehicule.setStatut(statut);
        vehicule.setCategorie(categorie);
        return vehicule;
    }

    private Client buildClient(String nom, String prenom, String email, String numPermis) {
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setTelephone("20000000");
        client.setNumPermis(numPermis);
        client.setDateInscription(LocalDate.now(ZoneId.systemDefault()));
        return client;
    }

    private Contrat buildContrat(BigDecimal montantTotal) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now(ZoneId.systemDefault()));
        contrat.setMontantTotal(montantTotal);
        contrat.setValide(true);
        return contrat;
    }

    private Paiement buildPaiement(BigDecimal montant) {
        Paiement paiement = new Paiement();
        paiement.setMontant(montant);
        paiement.setDatePaiement(LocalDate.now(ZoneId.systemDefault()));
        paiement.setModePaiement(ModePaiement.CARTE);
        return paiement;
    }
}
