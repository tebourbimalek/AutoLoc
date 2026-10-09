# Qualite de code avec SonarQube for IDE (SonarLint)

Objectif : analyser la qualite du code **des la phase de developpement**, sans
attendre une integration continue, grâce au plugin **SonarQube for IDE**
(ex-*SonarLint*).

## 1. Installation (au choix)

| IDE | Extension |
|---|---|
| VS Code | `sonarsource.sonarlint-vscode` (recommande par `.vscode/extensions.json`) |
| IntelliJ IDEA | *SonarQube for IDE* (Marketplace) |
| Eclipse | *SonarQube for IDE* (Marketplace) |

Au premier lancement, accepter/deconnecter la telemetrie est libre ; le projet
contient deja `sonarlint.disableTelemetry: true` dans `.vscode/settings.json`.

## 2. Modes d'analyse

- **Standalone** (par defaut, aucun serveur) : SonarLint applique directement ses
  regles Java aux sources. C'est suffisant pour ce projet.
- **Connected mode** (optionnel) : relier l'IDE a un SonarQube/SonarCloud pour
  partager le meme profil de regles et l'etat des issues. Voir `sonar-project.properties`.

## 3. Fichiers de configuration ajoutes

| Fichier | Role |
|---|---|
| `sonar-project.properties` | cle projet, sources, binaires Java (`target/classes`), encodage, exclusions |
| `.vscode/settings.json` | indique `sonar.java.binaries=target/classes` a SonarLint |
| `.vscode/extensions.json` | recommande l'extension SonarQube for IDE |

> Pour l'analyse Java, compiler d'abord (`mvn -q compile`) afin que
> `target/classes` existe ; sinon SonarLint analyse les sources seules.

## 4. Procedure de verification

1. Compiler le projet (racine `atelier1-springboot`) :
   ```powershell
   mvn -q clean compile
   ```
2. Ouvrir le dossier du projet dans l'IDE : les issues apparaissent dans le
   panneau *Problems / SonarQube* (VS Code) ou l'onglet *SonarQube for IDE*
   (IntelliJ/Eclipse).
3. Etat attendu apres l'Atelier 3 : **aucune issue** sur les regles ci-dessous
   (elles ont ete corrigees).

## 5. Reproduire les issues de l'Atelier 3 (optionnel)

Ces regles ont ete rencontrees puis corrigees (`docs/repository-notes.md`, section 3).
Pour les voir signaler par SonarLint, annuler temporairement une correction :

```powershell
# dans la racine du projet
git stash          # met de cote les corrections en cours
# ... observer les issues dans l'IDE ...
git stash pop      # restaure
```

| Regle | Ce que SonarLint signale | Correction |
|---|---|---|
| `java:S114` | nom d'interface non conforme a la convention `I<Entity>Repository` | `VehiculeRepository` renommee `IVehiculeRepository` |
| `java:S1192` | litteral `"Contrat introuvable: "` duplique >= 3 fois | constante `CONTRAT_INTROUVABLE` |
| `java:S2057` | `ResourceNotFoundException` sans `serialVersionUID` | `@Serial private static final long serialVersionUID = 1L;` |

## 6. Bonnes pratiques a garder a l'esprit

- Corriger les issues **au fil de l'eau** (avant le commit), pas en fin de projet.
- Ne pas desactiver une regle sans raison : preferer la correction, ou marquer
  l'issue *False Positive* / *Won't Fix* de facon motivee.
- Verifier regulierement `@Data` non souhaite sur les entites, l'injection de
  champ (`@Autowired`), les `System.out`, les imports inutilises et le code mort.
