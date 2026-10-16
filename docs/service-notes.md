# Atelier 4 - Couche Service, IoC et injection de dependances

Notes sur la couche Service du projet `autoloc-api`.

## A. Injection par constructeur vs injection par champ

| Critere | Injection par constructeur | Injection par champ (`@Autowired`) |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dependance visible dans la signature ? | Oui (parametre du constructeur) | Non (cachee dans le corps de la classe) |
| Utilisable avec `new` hors du conteneur sans dependance nulle ? | Oui : on est oblige de fournir la dependance, elle n'est jamais nulle | Non : le champ reste `null` jusqu'a l'injection, risque de NPE |
| Signale par SonarQube (regle S6813) ? | Non | Oui |

## B. Services et dependances injectees

Tous les services sont des singletons sans etat (aucun champ mutable), injectes par constructeur via `@RequiredArgsConstructor` et un champ `final`.

| Service | Dependances injectees | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Une seule dependance, champ `final`, testable hors conteneur |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Dependance obligatoire et immuable |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Pas d'etat partage, injection finale |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Dependance explicite dans la signature |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Champ `final`, aucune dependance cachee |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Une seule dependance, pas de setter |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Injection finale, singleton sur |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Dependance explicite et immuable |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule, aucune ecriture |

## C. Trois messages d'erreur du conteneur

| Message | Cause | Correction |
|---|---|---|
| `NoSuchBeanDefinitionException` | Bean absent du conteneur : `@Service` manquant ou classe hors du package scanne (`tn.esprit.autoloc`). | Ajouter `@Service` (ou un stereotype) ou deplacer la classe dans le package scanne. |
| `NoUniqueBeanDefinitionException` | Plusieurs beans candidats pour le meme type. | `@Primary` sur la classe voulue, ou `@Qualifier("emailNotificateur")` sur le parametre du constructeur. |
| Dependance circulaire (cycle A <-> B) | A depend de B et B depend de A ; l'injection par constructeur rend le cycle impossible a contourner. | Extraire la responsabilite partagee dans un troisieme service. |

## D. Anomalies SonarQube (a verifier dans le panneau de l'IDE)

| Anomalie | Regle | Remarque |
|---|---|---|
| Injection par champ (`@Autowired` sur un champ) | `java:S6813` | A eviter : preferer l'injection par constructeur. |
| Exception generique levee ou attrapee (`RuntimeException` / `catch (Exception)`) | `java:S112` | Lever des exceptions dediees (`ResourceNotFoundException`, `IllegalArgumentException`). |
| Import inutilise | `java:S1128` | Supprimer l'import (le code de l'atelier n'en contient pas). |
| Litteral de chaine duplique | `java:S1192` | Factoriser dans une constante. |

## E. Reponses aux 5 questions de comprehension

1. Le `new` est execute par le conteneur Spring au demarrage, grace a `@Service` et au component scanning.
2. Le controleur depend de l'interface pour rester decouple et pouvoir changer d'implementation sans le modifier.
3. Un singleton est partage par tous les threads : un etat mutable provoque des conditions de course (exemple : un champ "contrat courant" ecrase par des requetes concurrentes).
4. `update` recharge puis recopie pour que `save` n'ecrase pas toutes les colonnes ni ne vide la collection `paiements` (orphanRemoval).
5. `@Component` est un stereotype de classe detecte par le scan ; `@Bean` est une methode dans une `@Configuration` pour les classes qu'on ne peut pas annoter. `@Primary` est le candidat par defaut pose sur la classe ; `@Qualifier` est un choix explicite au point d'injection. `IPaiementService` est en lecture seule car les paiements sont geres via le `Contrat`.
