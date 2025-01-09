# Rapport Labo 8 - Jeu d'échecs
Victor Nicolet, Léon Surbeck

## **1. Classe `ChessController`**

### **Responsabilités**

La classe `ChessController` joue le rôle principal dans la gestion des interactions utilisateur et la coordination des différents composants du jeu d'échecs. Elle assure la liaison entre la logique du jeu (modèle) et l'interface utilisateur (vue).

### **Structure**

- **Méthodes principales** :
    - `startGame()`: initialise et lance une nouvelle partie.
    - `handleMove(int startX, int startY, int endX, int endY)`: gère les mouvements des pièces sur l'échiquier.
    - `resetGame()`: réinitialise le jeu.

### **Décisions Architecturales**

- **Modularité** : Sépare la logique du jeu de l'interface utilisateur pour une meilleure maintenabilité.
- **Clarté** : Les interactions utilisateur sont centralisées dans une seule classe, évitant la duplication de logique.

---

## **2. Classe `GBoard` et `GCell`**

### **Responsabilités**

Ces classes représentent respectivement l'échiquier et ses cases. Elles sont responsables de la gestion de l'état visuel et logique du plateau.

### **Structure**

- **GBoard** :
    - Attributs :
        - `cells`: une matrice contenant les cases de l'échiquier.
    - Méthodes :
        - `initialize()`: configure l'état initial de l'échiquier.
        - `getCell(int x, int y)`: retourne une case spécifique.

- **GCell** :
    - Attributs :
        - `piece`: la pièce actuelle sur cette case.
    - Méthodes :
        - `setPiece(Piece piece)`: place une pièce sur la case.
        - `isEmpty()`: vérifie si la case est vide.

### **Décisions Architecturales**

- **Encapsulation** : Ces classes encapsulent la logique du plateau et des cases, facilitant la modification de leur comportement sans impacter les autres composants.
- **Séparation des responsabilités** : Les cases et l'échiquier ont des responsabilités distinctes mais interconnectées.

---

## **3. Hiérarchie des pièces**

### **Responsabilités**

Les classes `Piece` et ses sous-classes (comme `Pawn`, `Rook`, `Queen`, etc.) modélisent les pièces d’échecs avec leurs comportements spécifiques.

### **Structure**

- **Classe de base :**
    - `Piece` :
        - Attributs :
            - `position`: position actuelle sur l’échiquier.
            - `color`: couleur de la pièce.
        - Méthodes abstraites :
            - `isValidMove(int startX, int startY, int endX, int endY)`: vérifie la validité d’un mouvement.

- **Sous-classes concrètes :**
    - `Pawn`, `Knight`, `Bishop`, `Rook`, `Queen`, `King` : implémentent les règles de mouvement propres à chaque pièce.

### **Décisions Architecturales**

- **Héritage** : Permet de partager une logique commune entre les pièces tout en spécifiant les comportements individuels.
- **Extensibilité** : Ajouter une nouvelle pièce ou modifier les règles d'une pièce existante peut se faire sans affecter les autres.

---

## **4. Vue et Interactions**

### **Responsabilités**

Les classes de la vue (comme `ChessView`) gèrent l’affichage du jeu et les interactions utilisateur.

### **Structure**

- `ChessView` :
    - Gère l’affichage graphique de l’échiquier et des pièces.
    - Fournit une interface pour recevoir les clics et autres actions de l’utilisateur.

### **Décisions Architecturales**

- **Séparation modèle-vue** : La logique du jeu est indépendante de l’interface utilisateur, ce qui permet de changer la vue sans affecter le jeu.
- **Modularité** : La classe `ChessView` peut être remplacée ou améliorée sans toucher aux autres composants.

---

## **Tests Effectués**

### **1. Cas d’utilisation testés**

#### **Tests sur les pièces**

1. **Validation des mouvements** :
    - Chaque pièce est testée pour ses règles de mouvement (e.g., le cavalier se déplace en “L”, le pion avance droit).
    - Résultat attendu : les mouvements respectent les règles du jeu.

2. **Mouvements invalides** :
    - Tester des mouvements impossibles.
    - Résultat attendu : les mouvements invalides sont rejetés.

#### **Tests sur le plateau**

1. **Gestion des cases** :
    - Tester l’ajout et la suppression de pièces.
    - Résultat attendu : les états des cases sont correctement mis à jour.

2. **Vérification des collisions** :
    - Tester les blocages par d’autres pièces (e.g., une tour ne peut pas sauter par-dessus une autre pièce).
    - Résultat attendu : les collisions sont correctement détectées.

#### **Tests d’intégration**

- Interaction entre les classes `ChessController`, `GBoard` et les pièces.
- Résultat attendu : les actions utilisateur modifient l’état du jeu de manière cohérente.

### **2. Résultats obtenus**

Tous les tests unitaires ont été exécutés avec succès, validant la robustesse et la fiabilité du système. Les tests sont disponibles dans le répertoire `src/test`.

---

Ce rapport présente une vue d’ensemble du système, en mettant en avant la modularité et l’extensibilité de l’architecture. Le projet est bien structuré, ce qui facilite les évolutions futures.

