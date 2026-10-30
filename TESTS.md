# TESTS — AutoLoc (Atelier 6 : DTO + Validation + GlobalExceptionHandler)

> Application démarrée sur le port **8082** (le 8080 est occupé par l'écouteur Oracle
> `TNSLSNR`). Remplacez `8082` par `8080` si le port est libre chez vous.
> Tous les corps sont en JSON (`Content-Type: application/json`).
> Les identifiants (`idAgence`, `idVehicule`, `idClient`) sont renvoyés par le POST
> et réutilisés pour le GET/PUT/DELETE correspondant.

## 1. Agence — `/agences`

### 1.1 Création valide (201)
```bash
curl -X POST http://localhost:8082/agences \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Agence Ariana", "ville": "Ariana", "adresse": "25 rue de la Republique", "telephone": "70111222" }'
```
**Réponse attendue (201)** :
```json
{ "idAgence": 5, "nom": "Agence Ariana", "ville": "Ariana", "adresse": "25 rue de la Republique", "telephone": "70111222" }
```

### 1.2 Création invalide — nom vide (400, erreurs par champ)
```bash
curl -X POST http://localhost:8082/agences \
  -H "Content-Type: application/json" \
  -d '{ "nom": "", "ville": "Ariana" }'
```
**Réponse attendue (400)** :
```json
{
  "timestamp": "...", "status": 400, "error": "Bad Request",
  "message": "La requête contient des champs invalides",
  "path": "/agences",
  "errors": { "nom": "Le nom de l'agence est obligatoire" }
}
```

### 1.3 Liste (200)
```bash
curl -X GET http://localhost:8082/agences
```

### 1.4 Consultation par id (200) / inconnu (404)
```bash
curl -X GET http://localhost:8082/agences/5
curl -X GET http://localhost:8082/agences/999999   # -> 404
```

### 1.5 id non numérique (400 — type mismatch)
```bash
curl -X GET http://localhost:8082/agences/abc
```
**Réponse attendue (400)** : `"Le paramètre 'id' doit être de type Long"`.

### 1.6 Mise à jour (200)
```bash
curl -X PUT http://localhost:8082/agences/5 \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Agence Ariana Centre", "ville": "Ariana", "adresse": "25 rue de la Republique", "telephone": "70111222" }'
```

### 1.7 Suppression (204) puis re-lecture (404)
```bash
curl -X DELETE http://localhost:8082/agences/5
curl -X GET http://localhost:8082/agences/5   # -> 404
```

## 2. Vehicule — `/vehicules`

### 2.1 Création valide (201)
```bash
curl -X POST http://localhost:8082/vehicules \
  -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto", "immatriculation": "180TUN770", "couleur": "Bleu", "prixJour": 85, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE", "agenceId": null, "equipementIds": [] }'
```

### 2.2 Prix négatif (400)
```bash
curl -X POST http://localhost:8082/vehicules \
  -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto", "immatriculation": "181TUN770", "prixJour": -85, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE" }'
```
**Réponse attendue (400)** : `errors: { "prixJour": "Le prix journalier doit être un nombre strictement positif" }`.

### 2.3 Statut manquant (400)
```bash
curl -X POST http://localhost:8082/vehicules \
  -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto", "immatriculation": "182TUN770", "prixJour": 85, "disponible": true, "categorie": "CITADINE" }'
```
**Réponse attendue (400)** : `errors: { "statut": "Le statut du véhicule est obligatoire" }`.

### 2.4 JSON malformé (400 — HttpMessageNotReadable)
```bash
curl -X POST http://localhost:8082/vehicules \
  -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto", "prixJour": 85 }'
```
*(JSON incomplet mais valide syntaxiquement → 400 uniquement si un champ requis manque ; pour un JSON syntaxiquement invalide, le handler `HttpMessageNotReadableException` répond 400 "Corps de la requête JSON invalide".)*

### 2.5 Immatriculation déjà utilisée (409 — DataIntegrityViolation)
```bash
curl -X POST http://localhost:8082/vehicules \
  -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto", "immatriculation": "180TUN770", "prixJour": 85, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE" }'
```
**Réponse attendue (409)** : `"Violation d'intégrité des données (contrainte d'unicité ou clé étrangère)"`.

### 2.6 Consultation / mise à jour / suppression
```bash
curl -X GET http://localhost:8082/vehicules/6
curl -X PUT http://localhost:8082/vehicules/6 -H "Content-Type: application/json" \
  -d '{ "marque": "Kia", "modele": "Picanto Stepway", "immatriculation": "180TUN770", "couleur": "Gris", "prixJour": 90, "disponible": true, "statut": "DISPONIBLE", "categorie": "CITADINE" }'
curl -X DELETE http://localhost:8082/vehicules/6
```

## 3. Client — `/clients`

### 3.1 Création valide (201)
```bash
curl -X POST http://localhost:8082/clients \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Ben Salah", "prenom": "Ahmed", "email": "ahmed.bensalah@example.tn", "telephone": "98111222", "numPermis": "99887741", "dateInscription": "2026-10-30" }'
```

### 3.2 Email vide (400)
```bash
curl -X POST http://localhost:8082/clients \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Trabelsi", "prenom": "Leila", "email": "", "numPermis": "99887742", "dateInscription": "2026-10-30" }'
```

### 3.3 Email mal formé (400)
```bash
curl -X POST http://localhost:8082/clients \
  -H "Content-Type: application/json" \
  -d '{ "nom": "X", "prenom": "Y", "email": "pas-un-email", "numPermis": "99887744", "dateInscription": "2026-10-30" }'
```

### 3.4 Date d'inscription dans le futur (400 — @PastOrPresent)
```bash
curl -X POST http://localhost:8082/clients \
  -H "Content-Type: application/json" \
  -d '{ "nom": "X", "prenom": "Y", "email": "x@y.tn", "numPermis": "99887743", "dateInscription": "2099-12-31" }'
```

### 3.5 Email déjà utilisé (409)
```bash
curl -X POST http://localhost:8082/clients \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Doublon", "prenom": "Test", "email": "ahmed.bensalah@example.tn", "numPermis": "99887799", "dateInscription": "2026-10-30" }'
```

### 3.6 Consultation / mise à jour / suppression
```bash
curl -X GET http://localhost:8082/clients/3
curl -X PUT http://localhost:8082/clients/3 -H "Content-Type: application/json" \
  -d '{ "nom": "Ben Salah", "prenom": "Ahmed", "email": "ahmed.bensalah@example.tn", "telephone": "98111223", "numPermis": "99887741", "dateInscription": "2026-10-30" }'
curl -X DELETE http://localhost:8082/clients/3
```

## 4. Swagger UI

- Page principale : **http://localhost:8082/swagger-ui.html**
- OpenAPI JSON    : **http://localhost:8082/v3/api-docs**
- Les schémas `AgenceRequestDTO`, `VehiculeRequestDTO`, `ClientRequestDTO` (avec les contraintes de validation) sont visibles dans la section "Schemas" en bas de la page.

## 5. Format d'erreur standard

Toutes les erreurs renvoient un `ErrorResponse` :
```json
{
  "timestamp": "2026-10-30T16:36:09.738",
  "status": 400,
  "error": "Bad Request",
  "message": "La requête contient des champs invalides",
  "path": "/agences",
  "errors": { "nom": "Le nom de l'agence est obligatoire" }
}
```
- `errors` est `null` pour les erreurs sans champ (404, 409, 400 de type mismatch, 500).
- `errors` est une map `champ -> message` pour les erreurs de validation.
