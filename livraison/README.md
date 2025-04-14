# Jeu de Mémoire - Projet Java

Un jeu de mémoire interactif développé en Java avec une interface graphique Swing.

## Description

Ce jeu permet aux joueurs de dessiner et reproduire des formes géométriques dans un temps limité. Il propose deux modes de jeu :
- Mode solo : Le joueur doit reproduire un ensemble de formes affichées 
- Mode deux joueurs : Les joueurs alternent entre le dessin et la reproduction des formes

## Fonctionnalités

- Interface graphique intuitive
- Différentes formes géométriques (cercle, rectangle, triangle, étoile, hexagone)
- Personnalisation des couleurs
- Système de score
- Timer pour chaque phase de jeu
- Mode deux joueurs avec alternance

## Prérequis

- Java JDK 11 ou supérieur
- Ant pour la compilation

## Installation

1. Clonez le dépôt :
```bash
git clone https://github.com/votre-username/MemoGame.git
```

2. Accédez au répertoire du projet :
```bash
cd MemoGame
```

3. Compilez le projet avec Ant :
```bash
ant
```

## Lancement

Pour lancer le jeu, exécutez le JAR généré :
```bash
java -jar dist/MemoGame.jar
```

## Structure du projet

```
MemoGame/
├── src/
│   ├── command/     # Commandes pour le pattern Command
│   ├── controller/  # Contrôleurs du jeu
│   ├── evaluation/  # Stratégies d'évaluation des scores
│   ├── main/        # Point d'entrée de l'application
│   ├── model/       # Classes du modèle
│   ├── plugin/      # Plugins (Look & Feel)
│   ├── state/       # pattern State
│   ├── strategy/    # Stratégies de génération de formes
│   ├── test/        # Tests unitaires
│   ├── ui/          # Composants UI réutilisables
│   └── view/        # Vues principales
├── build/           # Fichiers compilés
├── dist/            # Fichier executable (JAR)
├── doc/             # JavaDoc 
├── lib/             # Bibliothèques 
├── rapport/         # Rapport du projet
├── build.xml        # Configuration Ant
├── .gitignore       # Fichiers ignorés par Git
├── README.md        # Documentation principale
└── LICENSE          # Licence MIT
```


## Auteurs

- Imane Rachmaine
- Chaimaa Taghi