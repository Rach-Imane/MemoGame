## MemoGame (Java Swing)

Application Java Swing pour créer et manipuler des formes, avec contrôleur, commandes, états d’interaction, stratégies de score et plugins d’apparence. Le projet est construit avec Ant et produit un exécutable `MemoGame.jar`.

### Fonctionnalités principales
- **Formes**: cercle, rectangle, triangle, étoile, hexagone
- **Interactions**: ajout, déplacement, sélection (via états d’UI)
- **Commandes**: ajout/déplacement (architecture Command + gestionnaire)
- **Scores** (stratégies): exact, strict, approximatif, pondéré par couleur
- **Apparence** (plugins Look & Feel): Nimbus, Metal, Dark/Nimbus personnalisés
- **Génération** (stratégies): aléatoire, depuis fichier

### Architecture (packages clés)
- `main.Launcher`: point d’entrée (fenêtre de configuration puis vue principale)
- `model`: état du jeu, formes (`CircleShape`, `RectangleShape`, `TriangleShape`, `StarShape`, `HexagonShape`), sessions
- `controller`: contrôleur principal et états d’interaction (`Idle`, `AddingCircle`, `AddingRectangle`, `MovingShape`, etc.)
- `command`: commandes (`AddShapeCommand`, `MoveShapeCommand`, `CommandManager`)
- `evaluation`: stratégies de score (`ExactMatchScore`, `StrictScore`, `ApproximateScore`, `ColorWeightedScore`)
- `strategy`: génération de formes (`RandomShapeStrategy`, `FileShapeStrategy`)
- `view`: vues Swing (`GameView`, `GameSessionView`, `DrawingPanel`)
- `view.factory`: plugins Look & Feel (`NimbusUIFactory`, `MetalUIFactory`, `DarkUIFactory`)

### Arborescence
```text
livraison/
  ├── build.xml        # Script Ant
  ├── lib/             # Dépendances (JUnit/Hamcrest incluses)
  ├── src/             # Code source MemoGame
  │   ├── main/Launcher.java
  │   ├── controller/...  model/...  view/...  command/...  evaluation/...  strategy/...  view/factory/...
  ├── build/           # sortie compilation (généré)
  ├── dist/            # jar distribuable (généré)
  └── doc/             # javadoc (généré)
```

### Prérequis
- Java 8+ (Java 11 recommandé)
- Apache Ant

### Construire et exécuter
Depuis le dossier `livraison/`:
```bash
ant all         # clean + compile + javadoc + dist
java -jar dist/MemoGame.jar
```

### Tests
Des libs JUnit/Hamcrest sont présentes sous `livraison/lib`. Si des tests sont définis sous `livraison/src/test`, adaptez `build.xml` pour une cible `test` si besoin.

### Licence
MIT — voir `LICENSE`.

### À propos
Auteur: Rachmaine Imane.


