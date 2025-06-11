# 📦 Product API — Spring Boot

Ce projet est une **API REST CRUD** développée en **Java avec Spring Boot**. Il permet de gérer une collection de produits (nom, prix) et introduit deux fonctionnalités avancées :

- 📋 duplication d’un produit
- 📦 création de bundle de produits

---

## 📁 Structure du projet

product-api/
├── src/
│ ├── main/
│ │ ├── java/com/example/product_api/
│ │ │ ├── ProductApiApplication.java # Classe principale
│ │ │ ├── controller/ProductController.java # Contrôleur REST
│ │ │ ├── model/Product.java # Entité JPA
│ │ │ ├── repository/ProductRepository.java # DAO JPA
│ │ │ └── service/ProductService.java # (optionnel) Logique métier
│ └── resources/
│ └── application.properties # Configuration
├── src/test/java/... # Tests unitaires & intégration
├── pom.xml # Dépendances Maven
└── README.md # Documentation du projet

markdown
Copier
Modifier

---

## 🧰 Choix techniques

- **Spring Boot** 3.2 — Simplifie la configuration
- **Spring Web** — Création de l’API REST (`@RestController`)
- **Spring Data JPA** — Accès aux données via repository
- **H2 Database** — Base en mémoire, simple et légère pour les tests
- **Tests** :
  - `MockMvc` pour les tests de contrôleurs
  - `@DataJpaTest` pour les tests d’intégration
- (Optionnel) **Swagger / OpenAPI** pour la documentation visuelle

---

## 🧠 Règles métiers implémentées

### 🔁 `Duplicate` (dupliquer un produit)

Route : `POST /products/{id}/duplicate`

- Crée un nouveau produit avec les mêmes attributs (`name`, `price`)
- Ne copie pas l’ID
- Retourne l’objet cloné

### 📦 `Bundle` (produit composite)

Route : `POST /products/bundle`

- Crée un **produit bundle** à partir de plusieurs produits existants
- Nom = concaténation des noms
- Prix = somme des prix
- Enregistre une relation `@ManyToMany` vers les produits sources
- ⚠️ Vérifie l’absence de **boucle de dépendance**

---

## 🚀 Lancer le projet

```bash
./mvnw spring-boot:run
L'application démarre sur : http://localhost:8080

🧪 Tests
Les tests sont réalisés avec JUnit 5 et MockMvc, et couvrent l’ensemble des fonctionnalités exposées par l’API REST. Chaque test est exécuté dans une base H2 en mémoire.

🔍 Tests de lecture (GET)
✅ GET /products/{id} : retourne un produit existant

❌ GET /products/{id} : retourne 404 Not Found si l’ID n’existe pas

✅ GET /products : retourne la liste de tous les produits

➕ Tests de création (POST)
✅ POST /products : crée un produit avec un nom et un prix valides

❌ POST /products : retourne 400 Bad Request pour une requête invalide (ex. : produit vide)

♻️ Tests de mise à jour (PUT)
✅ PUT /products/{id} : met à jour un produit existant

❌ PUT /products/{id} : retourne 404 Not Found si le produit n’existe pas

🗑️ Tests de suppression (DELETE)
✅ DELETE /products/{id} : supprime un produit existant

❌ Lecture après suppression : retourne 404 Not Found si on tente d’accéder à un produit supprimé

🔁 Tests de duplication
✅ POST /products/{id}/duplicate : crée une copie d’un produit avec un nouvel ID

❌ POST /products/{id}/duplicate : retourne 404 Not Found si l’ID source n’existe pas

📦 Tests de bundle
✅ POST /products/bundle : crée un bundle à partir d’une liste de produits (somme des prix, concaténation des noms)

❌ POST /products/bundle : retourne 400 Bad Request si la liste est vide

📦 Pour lancer tous les tests :

bash
Copier
Modifier
./mvnw test
Ils sont tous localisés dans le fichier :

swift
Copier
Modifier
src/test/java/com/example/product_api/controller/ProductControllerTest.java

```

👨‍💻 Auteur
Projet réalisé dans le cadre d’un TP Java Spring Boot — Formation Développeur d’Applications
© 2025 — Tous droits réservés
