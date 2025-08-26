## Simulateur générique du Jeu de la Vie

Projet Java modulaire permettant de simuler des automates cellulaires génériques (Jeu de la Vie de Conway et variantes), avec support de différentes règles (classique, Covid, Margolus, neurale) et moteurs de calcul (naïf et HashLife).

### Fonctionnalités
- **Règles configurables**: classique, Covid, Margolus, neurale (réseau simple)
- **Taille de grille modifiable** et **réinitialisation**
- **Moteur HashLife** pour l'accélération des générations
- **Architecture propre** avec patrons: Stratégie, État, Composite

### Arborescence prévue
```text
src/
└── jeuDeLaVie/
    ├── modele/
    ├── regles/
    │   ├── classique/
    │   ├── covid/
    │   └── neuronal/
    ├── moteur/
    ├── hashlife/
    ├── vue/
    │   ├── vue2D/
    │   └── vue3D/
    ├── controleur/
    └── application/
```

### Lancement local (à adapter selon votre outillage)
1. Java 17+ recommandé
2. Compiler et exécuter `jeuDeLaVie.application.Lancement`

### Captures/DEMO
Ajoutez ici un GIF ou des captures d'écran du simulateur.

### Licence
Ce projet est sous licence MIT (voir `LICENSE`).

### À propos
Auteur: Rachmaine Imane. 


