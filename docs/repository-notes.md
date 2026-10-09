# Atelier 3 - Couche Repository / Service (Spring Data JPA)

Notes sur les choix techniques de la couche d'acces aux donnees du projet `autoloc-api`.

## 1. Interfaces Repository

Toutes les interfaces etendent `JpaRepository<Entity, Long>` et sont declouees dans
`tn.esprit.autoloc.repository`.

| Interface | Extends | Justification |
|---|---|---|
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD + `List` + tri/pagination + requetes derivees |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | idem |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | idem |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | idem |
| `IClientRepository` | `JpaRepository<Client, Long>` | idem |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | idem |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | `JpaRepository` (et non `CrudRepository`) pour beneficier du flush / batch |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | idem |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | idem |

Aucune annotation `@Repository` : les interfaces Spring Data sont detectees
automatiquement par le scanning et l'annotation est redondante.

### Requetes derivees (query methods)

Toutes les interfaces exposent des methodes derivees du nommage Spring Data
(validation au demarrage du contexte) :

| Repository | Methodes derivees |
|---|---|
| `IAgenceRepository` | `findByVilleIgnoreCase`, `findByNomContainingIgnoreCase` |
| `IEmployeRepository` | `findByRole`, `findByAgenceIdAgence`, `findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase` |
| `IVehiculeRepository` | `findByImmatriculation`, `findByDisponibleTrue`, `findByMarqueIgnoreCase`, `findByStatut`, `findByCategorie`, `findByAgenceIdAgence` |
| `IEquipementRepository` | `findByLibelleContainingIgnoreCase` |
| `IClientRepository` | `findByEmailIgnoreCase`, `findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase` |
| `IReservationRepository` | `findByStatut`, `findByClientIdClient`, `findByVehiculeIdVehicule` |
| `IContratRepository` | `findByValideTrue`, `findByReservationIdReservation` |
| `IPaiementRepository` | `findByContratIdContrat`, `findByModePaiement` |
| `IMaintenanceRepository` | `findByVehiculeIdVehicule`, `findByDateFinIsNull` |

### Pourquoi `JpaRepository` plutot que `CrudRepository`

- `CrudRepository` ne renvoie que `Iterable`; `JpaRepository` renvoie `List`
  (`findAll()`) ce qui autorise l'acces indexe et `.size()` sans cast.
- `JpaRepository` apporte le tri (`findAll(Sort)`), la pagination
  (`findAll(Pageable)`), le `saveAndFlush` et les operations batch
  (`deleteAllInBatch`, `saveAllAndFlush`).
- `Iterable` vs `List` : `Iterable` n'expose pas `size()` ni `get(int)`; il faut
  iterer. `JpaRepository.findAll()` renvoie une `List`, plus pratique cote service.

### Spring Data 3 : `PagingAndSortingRepository`

Depuis Spring Data 3, `PagingAndSortingRepository` **n'etend plus**
`CrudRepository`. Pour disposer a la fois du CRUD et de la pagination, on utilise
donc `JpaRepository` (qui etend les deux).

### Suppression batch et cascade

Les methodes batch (`deleteAllInBatch`, `deleteAllByIdInBatch`) generent un
`DELETE ... WHERE` unique au niveau JDBC : elles **court-circuitent la cascade
JPA et `orphanRemoval`**, car les entites ne sont pas chargees en memoire.
Pour supprimer un `Contrat` en declenchant la suppression de ses `Paiement`, on
utilise `deleteById` / `delete` (chargement de l'entite puis `EntityManager.remove`).

## 2. Couche Service

- Interfaces `I<Entity>Service` dans `tn.esprit.autoloc.service`.
- Implementations `<Entity>ServiceImpl` dans `tn.esprit.autoloc.service.impl`,
  annotees `@Service`, injection par constructeur via
  `@RequiredArgsConstructor` (champs `final`, aucun `@Autowired` sur champ).
- `@Transactional` sur les ecritures, `@Transactional(readOnly = true)` sur les
  lectures.
- `update()` recharge l'entite par `findById`, leve `ResourceNotFoundException`
  si absente, applique les changements puis retourne l'objet renvoye par `save()`.
- `deleteById()` verifie `existsById` avant suppression (comportement strict).
- `Contrat.paiements` porte `cascade = CascadeType.ALL, orphanRemoval = true` :
  l'ajout/suppression d'un `Paiement` passe par le `Contrat`
  (`addPaiement` / `removePaiement`), jamais par un `deleteAllInBatch`.

## 3. SonarQube for IDE - problemes trouves et corriges

| Issue | Rule | Fix |
|---|---|---|
| Interface nommee `VehiculeRepository` (incoherente avec les 8 autres `I<Entity>Repository`) | `java:S114` - Interface names should comply with a naming convention | Renommee `IVehiculeRepository`, reference mise a jour dans `VehiculeController` |
| Litteral `"Contrat introuvable: "` duplique 4 fois dans `ContratServiceImpl` | `java:S1192` - String literals should not be duplicated | Extraire une constante `CONTRAT_INTROUVABLE` |
| `ResourceNotFoundException` sans `serialVersionUID` (classe `Serializable` via `Throwable`) | `java:S2057` - Enable serialization compatibility during class evolution | Ajouter `@Serial private static final long serialVersionUID = 1L;` |

Aucun `@Data` sur les entites, aucune injection de champ (`@Autowired`), aucun
`System.out` et aucun import inutilise n'a ete trouve dans le code.

Configuration SonarLint de l'IDE : voir `docs/sonarlint.md`
(`sonar-project.properties`, `.vscode/settings.json`).

## 4. Demonstration (profil `demo`)

`DemoCrudRunner` (`tn.esprit.autoloc`, `@Profile("demo")`) execute au demarrage :
CREATE -> READ -> UPDATE -> tri -> pagination -> `saveAndFlush` -> `existsById`
-> `count` -> cascade/orphanRemoval sur `Paiement` -> DELETE, avec logs SLF4J.

```bash
# dans la racine du projet autoloc-api
mvn spring-boot:run "-Dspring-boot.run.profiles=demo" "-Dspring-boot.run.arguments=--spring.datasource.password="
```
