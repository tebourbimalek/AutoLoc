# Checklist des captures d'écran — Atelier 5 (Spring MVC et SpringDoc)

> Application démarrée sur le port **8082** (le port 8080 de cette machine est
> occupé par l'écouteur Oracle `TNSLSNR`). Si un jour 8080 se libère,
> remplacez simplement 8082 par 8080 dans les URL.
>
> Avant chaque capture : application lancée (`mvn spring-boot:run`),
> Postman ouvert avec la collection `postman/AutoLoc.postman_collection.json`
> et l'environnement `postman/local.postman_environment.json` **sélectionné**
> (menu déroulant en haut à droite de Postman).

## 1. Agence — http://localhost:8082/agences

| # | Screenshot attendu | Outil | Requête | Statut attendu | Body (POST/PUT uniquement) |
|---|---|---|---|---|---|
| 1 | Liste des agences | Postman | `GET {{baseUrl}}/agences` | 200 | — |
| 2 | Agence 404 | Postman | `GET {{baseUrl}}/agences/999999` | 404 | — |
| 3 | Création d'agence | Postman | `POST {{baseUrl}}/agences` | 201 | `{ "nom": "Agence Tunis-vile", "ville": "Tunis", "adresse": "12 avenue Habib Bourguiba", "telephone": "71123456" }` |
| 4 | Agence 400 (nom manquant) | Postman | `POST {{baseUrl}}/agences` | 400 | `{ "nom": "", "ville": "Tunis", "adresse": "12 avenue Habib Bourguiba", "telephone": "71123456" }` |
| 5 | Mise à jour d'agence (id créé au #3) | Postman | `PUT {{baseUrl}}/agences/{{agenceId}}` | 200 | `{ "nom": "Agence Tunis Centre", "ville": "Tunis", "adresse": "12 avenue Habib Bourguiba", "telephone": "71987654" }` |
| 6 | Consultation de l'agence (id #3) | Postman | `GET {{baseUrl}}/agences/{{agenceId}}` | 200 | — |
| 7 | Suppression de l'agence | Postman | `DELETE {{baseUrl}}/agences/{{agenceId}}` | 204 (corps vide) | — |

## 2. Vehicule — http://localhost:8082/vehicules

| # | Screenshot attendu | Outil | Requête | Statut attendu | Body (POST/PUT uniquement) |
|---|---|---|---|---|---|
| 8 | Liste des véhicules | Postman | `GET {{baseUrl}}/vehicules` | 200 | — |
| 9 | Véhicule 404 | Postman | `GET {{baseUrl}}/vehicules/999999` | 404 | — |
| 10 | Création de véhicule | Postman | `POST {{baseUrl}}/vehicules` | 201 | `{ "marque": "Dacia", "modele": "Sandero", "immatriculation": "100TU1500", "couleur": "Blanc", "prixJour": 90, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE", "agence": null, "equipements": [] }` |
| 11 | Véhicule 400 (prix négatif) | Postman | `POST {{baseUrl}}/vehicules` | 400 | `{ "marque": "Renault", "modele": "Clio", "immatriculation": "199RU8801", "prixJour": -50, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE", "agence": null, "equipements": [] }` |
| 12 | Mise à jour du véhicule (id #10) | Postman | `PUT {{baseUrl}}/vehicules/{{vehiculeId}}` | 200 | `{ "marque": "Dacia", "modele": "Sandero Stepway", "immatriculation": "100TU1500", "couleur": "Gris", "prixJour": 100, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE", "agence": null, "equipements": [] }` |
| 13 | Consultation du véhicule (id #10) | Postman | `GET {{baseUrl}}/vehicules/{{vehiculeId}}` | 200 | — |
| 14 | Suppression du véhicule | Postman | `DELETE {{baseUrl}}/vehicules/{{vehiculeId}}` | 204 (corps vide) | — |

## 3. Client — http://localhost:8082/clients

| # | Screenshot attendu | Outil | Requête | Statut attendu | Body (POST/PUT uniquement) |
|---|---|---|---|---|---|
| 15 | Liste des clients | Postman | `GET {{baseUrl}}/clients` | 200 | — |
| 16 | Client 404 | Postman | `GET {{baseUrl}}/clients/999999` | 404 | — |
| 17 | Création de client | Postman | `POST {{baseUrl}}/clients` | 201 | `{ "nom": "Ben Salah", "prenom": "Ahmed", "email": "ahmed.bensalah@example.tn", "telephone": "98123456", "numPermis": "12345678", "dateInscription": "2026-10-23", "reservations": [] }` |
| 18 | Client 400 (email manquant) | Postman | `POST {{baseUrl}}/clients` | 400 | `{ "nom": "Trabelsi", "prenom": "Leila", "email": "", "telephone": "98111222", "numPermis": "87654321", "dateInscription": "2026-10-23", "reservations": [] }` |
| 19 | Mise à jour du client (id #17) | Postman | `PUT {{baseUrl}}/clients/{{clientId}}` | 200 | `{ "nom": "Ben Salah", "prenom": "Ahmed", "email": "ahmed.bensalah@example.tn", "telephone": "98123457", "numPermis": "12345678", "dateInscription": "2026-10-23", "reservations": [] }` |
| 20 | Consultation du client (id #17) | Postman | `GET {{baseUrl}}/clients/{{clientId}}` | 200 | — |
| 21 | Suppression du client | Postman | `DELETE {{baseUrl}}/clients/{{clientId}}` | 204 (corps vide) | — |

## 4. Swagger UI

| # | Screenshot attendu | Outil | URL / action | Attendu |
|---|---|---|---|---|
| 22 | Page principale Swagger UI | Navigateur (Chrome/Edge/Firefox) | http://localhost:8082/swagger-ui.html | Titre "AutoLoc API", version 1.0.0, 3 tags : Agence, Vehicule, Client |
| 23 | Endpoint déplié avec documentation | Navigateur | Swagger UI → étiquette "Agence" → cliquer sur `POST /agences` | Voir le résumé @Operation "Créer une nouvelle agence", la description, 201/400 visibles dans la section Responses |
| 24 | (Bonus) Endpoint GET by id déplié | Navigateur | Swagger UI → "Vehicule" → `GET /vehicules/{id}` → "Try it out" → id = 999999 → Execute | 404 affiché dans la réponse |

## 5. Collection Runner

| # | Screenshot attendu | Outil | Action | Attendu |
|---|---|---|---|---|
| 25 | Résultat du Collection Runner | Postman | Ouvrir la collection "AutoLoc" → bouton "Runner" → sélectionner l'environnement "local" → "Run AutoLoc" | 21 requêtes, toutes vertes (0 failed). L'ordre top-to-bottom fonctionne sans intervention : chaque "create" enregistre l'id dans l'environnement, chaque "delete" le libère. |

## 6. Preuve du démarrage (bonus recommandé)

| # | Screenshot attendu | Outil | Action | Attendu |
|---|---|---|---|---|
| 26 | Console de démarrage | IntelliJ IDEA (terminal) | `mvn spring-boot:run` (inspecter les logs) | "Started AutolocApiApplication", "Tomcat started on port 8082", aucun ERROR |
