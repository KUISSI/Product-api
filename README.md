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
bash
Copier
Modifier
./mvnw test
📂 Console H2
Interface web pour consulter la base de données en mémoire

Accès : http://localhost:8080/h2-console

JDBC URL : jdbc:h2:mem:testdb

Utilisateur : sa — Mot de passe vide

🧪 Requêtes cURL utiles
➕ Créer un produit
bash
Copier
Modifier
curl -X POST http://localhost:8080/products \
-H "Content-Type: application/json" \
-d '{"name":"Clavier","price":49.99}'
📄 Lister tous les produits
bash
Copier
Modifier
curl http://localhost:8080/products
📦 Créer un bundle
bash
Copier
Modifier
curl -X POST http://localhost:8080/products/bundle \
-H "Content-Type: application/json" \
-d '[1,2,3]'
🔁 Dupliquer un produit
bash
Copier
Modifier
curl -X POST http://localhost:8080/products/1/duplicate
```

👨‍💻 Auteur
Projet réalisé dans le cadre d’un TP Java Spring Boot — Formation Développeur d’Applications
© 2025 — Tous droits réservés
