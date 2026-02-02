# Map Without Stopping - Project Guidelines

## Project Overview

Mod client-side qui permet de continuer à se déplacer quand un écran de carte plein écran est ouvert (JourneyMap, Xaero's World Map).

## Branch Strategy

- **main** : Version 1.21.1 (Fabric + NeoForge)
- **1.18.2** : Version 1.18.2 (Fabric + Forge)

## Architecture

Structure Architectury multi-plateforme :
- **common/** : Code partagé (mixins, logique principale)
- **fabric/** : Implémentation Fabric
- **forge/** : Implémentation Forge (branche 1.18.2)
- **neoforge/** : Implémentation NeoForge (branche main)

## Key Components

### Mixins (common/src/main/java/.../mixin/)

- **MinecraftMixin** : Détecte l'ouverture/fermeture des écrans de carte
- **KeyboardHandlerMixin** : Capture les touches de mouvement même quand un GUI est ouvert
- **KeyboardInputMixin** : Met à jour les inputs de mouvement du joueur
- **BoatMixin** : Support du mouvement en bateau

### Core Classes (common/src/main/java/.../)

- **MapScreenDetector** : Identifie si un Screen est une carte (JourneyMap, Xaero)
- **MovementStateHolder** : Gère l'état du mouvement et vérifie les touches physiquement pressées

## API Differences Between Versions

### 1.21.1 (main branch)
- `KeyboardInput.tick(boolean isSneaking, float sneakSpeed)`
- Java 21

### 1.18.2 (1.18.2 branch)
- `KeyboardInput.tick(boolean slowDown)`
- Java 17

## Build Commands

```bash
./gradlew build              # Build tous les modules
./gradlew :forge:build       # Build uniquement Forge
./gradlew :fabric:build      # Build uniquement Fabric
./gradlew clean              # Nettoyer
```

## Testing

```bash
./gradlew :fabric:runClient  # Lancer le client Fabric
./gradlew :forge:runClient   # Lancer le client Forge
```

Pour tester, ajouter JourneyMap ou Xaero's World Map dans le dossier `run/mods/`.

## Publishing

Voir `.github/workflows/publish.yml` pour le workflow de publication automatique sur Modrinth et CurseForge.

## Supported Map Mods

Les mods de carte supportés sont définis dans `MapScreenDetector.java` :
- JourneyMap (`journeymap.`)
- Xaero's World Map (`xaero.map.`)

Pour ajouter un nouveau mod de carte, ajouter son package prefix dans `ALLOWED_PACKAGES`.
