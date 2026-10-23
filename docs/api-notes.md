# Notes API — AutoLoc (Atelier 5)

## Tableau des endpoints

| Contrôleur | Verbe + route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| `AgenceController` | `GET /agences` | `IAgenceService.findAll()` | 200 | — |
| `AgenceController` | `GET /agences/{id}` | `IAgenceService.findById(id)` | 200 | 404 |
| `AgenceController` | `POST /agences` | `IAgenceService.create(agence)` | 201 | 400 |
| `AgenceController` | `PUT /agences/{id}` | `IAgenceService.update(id, agence)` | 200 | 400, 404 |
| `AgenceController` | `DELETE /agences/{id}` | `IAgenceService.deleteById(id)` | 204 | 404 |
| `VehiculeController` | `GET /vehicules` | `IVehiculeService.findAll()` | 200 | — |
| `VehiculeController` | `GET /vehicules/{id}` | `IVehiculeService.findById(id)` | 200 | 404 |
| `VehiculeController` | `POST /vehicules` | `IVehiculeService.create(vehicule)` | 201 | 400 |
| `VehiculeController` | `PUT /vehicules/{id}` | `IVehiculeService.update(id, vehicule)` | 200 | 400, 404 |
| `VehiculeController` | `DELETE /vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 | 404 |
| `ClientController` | `GET /clients` | `IClientService.findAll()` | 200 | — |
| `ClientController` | `GET /clients/{id}` | `IClientService.findById(id)` | 200 | 404 |
| `ClientController` | `POST /clients` | `IClientService.create(client)` | 201 | 400 |
| `ClientController` | `PUT /clients/{id}` | `IClientService.update(id, client)` | 200 | 400, 404 |
| `ClientController` | `DELETE /clients/{id}` | `IClientService.deleteById(id)` | 204 | 404 |

Gestion centrale des erreurs : `GlobalExceptionHandler` (`@RestControllerAdvice`)
traduit `ResourceNotFoundException` → **404** et `IllegalArgumentException` → **400**.
Les contrôleurs restent « minces » : aucune logique métier, aucun repository injecté,
seulement l'interface du service via constructeur (`@RequiredArgsConstructor`).

## Annotations Jackson sur les entités

Les associations bidirectionnelles (`@OneToMany(mappedBy=...)` + `@ManyToOne`,
`@ManyToMany(mappedBy=...)`) créent des cycles en sérialisation JSON
(`Agence.vehicules` → `Vehicule.agence` → `Agence.vehicules` → …) et une erreur
`No serializer found` / `ByteBuddyInterceptor` sur les proxies LAZY. Pour casser
ces cycles **sans utiliser `@JsonBackReference`** sur `Reservation.client`
(celui-ci doit rester écrivable dans un futur POST /reservations), on a posé
`@JsonIgnore` côté **inverse uniquement**, c'est-à-dire sur toutes les listes
`mappedBy` : `Agence.vehicules`, `Agence.employes`, `Vehicule.reservations`,
`Vehicule.maintenances`, `Equipement.vehicules`, `Client.reservations`. Le JSON
garde ainsi côté propriétaire `Vehicule.agence`, `Vehicule.equipements` et
`Reservation.client` (lisible et écrivable), et la boucle infinie disparaît.
`application.properties` n'a pas eu besoin du contournement
`spring.jackson.serialization.fail-on-empty-beans=false`.

## Notes d'environnement

- Spring Boot 3.2.5 → SpringDoc `springdoc-openapi-starter-webmvc-ui` **2.5.0**
  (dernière version officiellement compatible Boot 3.2.x ; la branche 2.8.x
  exige Spring Boot ≥ 3.4).
- Pas de `server.servlet.context-path` configuré → Swagger UI :
  <http://localhost:8082/swagger-ui.html> · OpenAPI JSON :
  <http://localhost:8082/v3/api-docs>
- Port **8082** car le port 8080 de cette machine est occupé par l'écouteur
  Oracle `TNSLSNR` (cf. README). Lancement :
  `mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.datasource.password= --server.port=8082"`
