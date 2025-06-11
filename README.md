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
Cette API REST est testée à l'aide de tests d'intégration avec Spring Boot (@SpringBootTest) et MockMvc (@AutoConfigureMockMvc).

Le fichier ProductControllerTest.java couvre les cas suivants :

🔍 Récupération d’un produit par ID

Vérifie que l'endpoint GET /products/{id} retourne bien les informations du produit.

➕ Création d’un produit

Vérifie que l'endpoint POST /products crée un produit avec les bons champs (name, price) et un id généré.

📚 Récupération de tous les produits

Vérifie que l'endpoint GET /products retourne la liste des produits enregistrés.

✏️ Mise à jour d’un produit

Vérifie que l'endpoint PUT /products/{id} met bien à jour un produit existant.

📎 Duplication d’un produit

Vérifie que l'endpoint POST /products/{id}/duplicate crée une copie conforme du produit avec un nouvel id.

🎁 Création d’un bundle de produits

Vérifie que l'endpoint POST /products/bundle crée un nouveau produit représentant un bundle avec le nom combiné et la somme des prix des produits inclus.

Tous les tests utilisent une base de données en mémoire (H2) réinitialisée avant chaque test avec repository.deleteAll() pour garantir l'isolation.

Exécution des tests (si Maven est utilisé) :

bash
Copier
Modifier
mvn test
```

👨‍💻 Auteur
Projet réalisé dans le cadre d’un TP Java Spring Boot — Formation Développeur d’Applications
© 2025 — Tous droits réservés
