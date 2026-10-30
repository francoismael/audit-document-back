# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Projet

Backend Spring Boot de l'application de génération de documents d'audit interne du MEF (Direction de l'Audit Interne). Il expose une API REST sous `/api`, consommée par le front Angular `../audit-document-front`.

- Spring Boot 2.7, **Java 8** (pas de `var`, de `record` ni de `List.of`), JPA/Hibernate (`javax.persistence`), PostgreSQL.
- Apache POI (`poi-ooxml`) pour lire et remplir les modèles Word (`.docx`).
- Conversion du document rempli en PDF : moteur choisi par `app.pdf.engine` dans `application.properties`. `word` (par défaut ici) : **Microsoft Word** piloté par PowerShell, rendu exactement identique à Word ; Windows et Word doivent être installés sur la machine du backend. `libreoffice` : LibreOffice en mode headless (`app.pdf.soffice-path`), fonctionne partout mais le rendu peut différer un peu de Word (polices, images, justification).
- Le schéma de la base est mis à jour par `spring.jpa.hibernate.ddl-auto=update`.

## Commandes (PowerShell)

- `.\mvnw spring-boot:run` : lance l'API sur http://localhost:8080.
- `.\mvnw compile` : vérifie la compilation (ajouter `-o` pour travailler hors ligne).

## Architecture

```
api/controller/          contrôleurs REST (+ exeption/GlobalExceptionHandler)
application/dto/         requêtes et réponses de l'API
application/usecases/    interfaces des cas d'usage
application/service/     implémentations des cas d'usage
application/port/        interfaces vers les outils externes (PdfConverter)
domain/entity/           entités JPA et enums métier (MissionRole)
domain/repository/       interfaces de repository du domaine
infrastructure/jpa/      interfaces Spring Data JPA
infrastructure/adapter/  implémentations des repositories du domaine
infrastructure/config/   configuration (CORS global)
infrastructure/pdf/      conversion DOCX → PDF : WordPdfConverter ou LibreOfficePdfConverter (PdfConversion : partie commune)
```

- Un contrôleur dépend uniquement des interfaces de `usecases/`. Un service dépend uniquement des interfaces de `domain/repository/`, jamais directement de `infrastructure/jpa/`.
- Le CORS est global (`CorsConfig`) : ne pas ajouter `@CrossOrigin` sur les contrôleurs.
- Les fichiers (modèles et documents générés) se lisent et s'écrivent uniquement via `DocumentStorage`, jamais avec le chemin absolu enregistré en base.
- Les modèles Word utilisent la syntaxe `{{nomDuChamp}}`. Le nom du champ doit correspondre à `DocumentField.nom`.
- La génération produit un **PDF** : le modèle `.docx` est rempli avec POI, puis converti par `PdfConverter` (Word ou LibreOffice). Les conversions sont faites une par une. Avec Word : environ 8 s au premier appel, puis 6 s.
- Les documents de l'historique générés avant le passage au PDF sont des `.docx` : `GeneratedDocument.contentTypeOf()` donne le bon type au téléchargement.
- Les rôles de mission sont fixes (`MissionRole`) : `DIRECTEUR_AUDIT_INTERNE`, `CHEF_MISSION`, `SUPERVISEUR`, `AUDITEUR`, `POINT_FOCAL`.

## Erreurs

`GlobalExceptionHandler` convertit les exceptions en JSON `{ status, message, timestamp }`. Lever l'exception qui correspond au sens de l'erreur, avec un message en français destiné à l'utilisateur :

| Exception | Code | Quand l'utiliser |
|---|---|---|
| `ResourceNotFoundException` | 404 | L'élément demandé n'existe pas (« … introuvable ») |
| `IllegalArgumentException` | 400 | Validation ou règle métier non respectée |
| `ConflictException` | 409 | Doublon (« … existe déjà ») |
| `RuntimeException` avec une cause | 500 | Erreur technique (lecture ou écriture de fichier, POI…) |

Les deux exceptions métier se trouvent dans `domain/exception/`.

## Langue du code

- **Les noms de fonctions et de méthodes sont toujours en anglais** et décrivent l'action réalisée, sous la forme verbe + complément : `findMissionPersonne()`, `addParticipants()`, `buildFileName()`. Ne jamais écrire `trouverMission()`, `ajouterLignes()`, etc.
- **Tous les commentaires sont en anglais.**
- Restent en français :
  - les messages d'erreur renvoyés à l'utilisateur (`throw new IllegalArgumentException("La mission est obligatoire.")`) ;
  - les noms des entités métier, des DTO, des tables et des champs du contrat d'API (`Constat`, `RapportAudit`, `motifs`, `dateEmission`…). Ils ne sont pas traduits, y compris quand ils apparaissent dans un nom de méthode : `addRisques()`, `findConstat()`.
