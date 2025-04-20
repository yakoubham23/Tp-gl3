# Système de Feu de Signalisation

Ce projet implémente un système de gestion de feux de signalisation avec Java et JUnit.

## Description

Le système simule le fonctionnement d'un feu de signalisation avec les composants suivants :
- Feu : Gère les états (rouge, vert, jaune) et les transitions
- Véhicule : Représente un véhicule qui attend et avance selon l'état du feu
- Piéton : Représente un piéton qui attend et traverse selon l'état du feu
- Contrôleur : Coordonne les feux et gère les interactions avec les usagers

## Structure du Projet

```
src/
├── main/
│   └── java/
│       └── com/
│           └── traffic/
│               ├── Feu.java
│               ├── Vehicule.java
│               ├── Pieton.java
│               └── Controleur.java
└── test/
    └── java/
        └── com/
            └── traffic/
                ├── FeuTest.java
                ├── VehiculeTest.java
                ├── PietonTest.java
                └── ControleurTest.java
```

## Fonctionnalités

- Gestion des états des feux (rouge, vert, jaune)
- Simulation des durées d'attente pour chaque état
- Interaction des véhicules et des piétons avec les feux
- Coordination des feux par le contrôleur
- Tests unitaires complets pour chaque composant

## Prérequis

- Java 11 ou supérieur
- Maven 3.6 ou supérieur
- JUnit 5

## Installation

1. Clonez le dépôt
2. Exécutez `mvn clean install` pour compiler et exécuter les tests

## Utilisation

Pour exécuter le système :

```java
Controleur controleur = new Controleur();
Feu feu = new Feu("Feu1", 30, 30, 5);
Vehicule vehicule = new Vehicule("V1", feu);
Pieton pieton = new Pieton("P1", feu);

controleur.ajouterFeu(feu);
controleur.ajouterVehicule(vehicule);
controleur.ajouterPieton(pieton);

controleur.coordonnerFeux();
controleur.surveillerUsagers();
```

## Tests

Les tests unitaires couvrent :
- Les transitions d'état des feux
- Le comportement des véhicules
- Le comportement des piétons
- La coordination des feux par le contrôleur

Exécutez les tests avec :
```bash
mvn test
```

## Documentation

La documentation technique est incluse dans le code source sous forme de commentaires JavaDoc.

## Licence

Ce projet est sous licence MIT. 