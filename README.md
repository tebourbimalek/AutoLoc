# AutoLoc API

AutoLoc — Atelier 1 (UP ASI, Esprit). Modele de persistance JPA du cas d'etude AutoLoc.

## Stack

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA / Hibernate 6
- MySQL (Connector/J) — developpe et verifie sur MariaDB 10.4 (XAMPP)
- Lombok
- Maven

## Structure

```
src/main/java/tn/esprit/autoloc/
├── AutolocApiApplication.java
├── domain/        # 9 entites JPA + 5 enums
├── repository/    # (Atelier 2)
├── service/       # (Atelier 2)
└── web/
    ├── controller/# (Atelier 2)
    └── dto/       # (Atelier 2)
```

## Entites

| Table | Classe | Colonnes |
|---|---|---|
| `vehicule` | `Vehicule` | idVehicule, marque, modele, immatriculation, couleur, prixJour, disponible, statut, categorie |
| `agence` | `Agence` | idAgence, nom, ville, adresse, telephone |
| `client` | `Client` | idClient, nom, prenom, email, telephone, numPermis, dateInscription |
| `employe` | `Employe` | idEmploye, nom, prenom, role |
| `equipement` | `Equipement` | idEquipement, libelle |
| `reservation` | `Reservation` | idReservation, dateDebut, dateFin, statut |
| `contrat` | `Contrat` | idContrat, dateSignature, montantTotal, valide |
| `paiement` | `Paiement` | idPaiement, montant, datePaiement, modePaiement |
| `maintenance` | `Maintenance` | idMaintenance, dateDebut, dateFin, description |

Les associations (`@OneToMany`, `@ManyToOne`, ...) seront ajoutees en Atelier 2.

## Configuration de la base de donnees

Le mot de passe MySQL n'est jamais versionne. Il est lu depuis la variable
d'environnement `DB_PASSWORD` (`spring.datasource.password=${DB_PASSWORD}`).

Definir `DB_PASSWORD` avant de lancer l'application :

```bash
# PowerShell
$env:DB_PASSWORD="votre_mot_de_passe"

# CMD
set DB_PASSWORD=votre_mot_de_passe

# Linux / macOS
export DB_PASSWORD="votre_mot_de_passe"
```

Si la variable n'est pas definie, le demarrage echoue avec
`Could not resolve placeholder 'DB_PASSWORD'`.

> Sous XAMPP, le compte `root` a un mot de passe vide par defaut. Soit on lui
> definit un mot de passe (via phpMyAdmin ou `ALTER USER`), soit on passe le
> mot de passe en argument au lancement :
> `mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.datasource.password="`.
> Une variable d'environnement ne peut pas representer une chaine vide sous
> Windows.

La base `autoloc_db` est creee automatiquement au premier demarrage
(`createDatabaseIfNotExist=true`) et les tables sont generees par Hibernate
(`spring.jpa.hibernate.ddl-auto=update`).

## Lancer l'application

```bash
mvn clean spring-boot:run
```

Ou construire puis executer le jar :

```bash
mvn clean package -DskipTests
java -jar target/autoloc-api-0.0.1-SNAPSHOT.jar
```

Verifier la base :

```sql
USE autoloc_db;
SHOW TABLES;
DESCRIBE vehicule;
```

> Si le demarrage echoue avec `Port 8080 was already in use`, un autre service
> occupe le port (par exemple l'ecouteur Oracle `TNSLSNR`). Lancer alors sur un
> autre port : `mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8082"`.
