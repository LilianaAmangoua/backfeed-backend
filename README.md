# BackFeed

## 📋 Présentation

BackFeed est une API REST développée avec Spring Boot, Maven et Spring Security, conçue pour aider les Product Owners à collecter, organiser et gérer efficacement les retours de leurs clients.

Ce projet vise à répondre à un besoin courant chez les clients grands comptes : la structuration de la gestion des retours utilisateurs (bugs, suggestions, demandes d'évolution) pour les équipes Produit et IT.

## 🎯 Fonctionnalités principales

L'application permet aux Product Owners de :

- Centraliser les feedbacks clients (problèmes, suggestions, besoins)
- Qualifier et prioriser ces retours
- Transformer les feedbacks en tickets exploitables par les développeurs *(fonctionnalité à venir)*
- Assigner ces tickets aux membres des équipes techniques *(fonctionnalité à venir)*

## 🔐 Gestion des droits

BackFeed implémente un système de contrôle d'accès basé sur les rôles (RBAC) avec Spring Security.

### Super Admin
- Toutes les autorisations des PO et des Clients
- Création d'invitations pour les PO
- Création de sociétés de type PO

### Product Owner (PO)
- Visualisation des feedbacks par client ou par projet
- Modification du statut d'un feedback
- Ajout de commentaires aux feedbacks
- Suppression de feedbacks
- Création de clients placeholders
- Création de projets
- Création de sociétés de type CLIENT uniquement

### Client
- Soumission de feedbacks (bug, amélioration, nouvelle fonctionnalité)
- Visualisation des feedbacks soumis par projet
- Suivi du statut des feedbacks ("à traiter", "pris en charge", "terminé")
- Modification des feedbacks tant qu'ils ne sont pas "pris en charge"
- Filtrage et tri des feedbacks

## 🚀 Installation

### Prérequis
- Java 17+
- Maven 3.6+
- Une base de données MySQL

### Configuration

1. Cloner le dépôt
```bash
git clone https://github.com/votre-organisation/backfeed.git
cd backfeed
```

2. Configurer les variables d'environnement

Un fichier `.env.sample` est fourni comme modèle. Copiez-le et renommez-le en `.env` :
```bash
cp .env.sample .env
```

Modifiez les valeurs dans le fichier `.env` :
```properties
# Configuration de la base de données
DB_URL=jdbc:mysql://localhost:3306/backfeed
DB_USERNAME=votre_username
DB_PASSWORD=votre_mot_de_passe

# Configuration JWT
JWT_SECRET=votre_secret_jwt_très_sécurisé
JWT_EXP=86400000

# Configuration SMTP pour les emails
SMTP_HOST=smtp.votreserveur.com
SMTP_PORT=587
SMTP_USERNAME=votre_email@domaine.com
SMTP_PASSWORD=votre_mot_de_passe_smtp

# Compte Super Admin initial
SUPER_ADMIN_EMAIL=votre_mail_super_admin
SUPER_ADMIN_PASSWORD=mot_de_passe_super_admin
```

3. Compilation et exécution
```bash
mvn clean install
mvn spring-boot:run
```

## 📚 Documentation API

Une documentation Swagger de l'API sera disponible à l'adresse suivante après démarrage de l'application:
```
http://localhost:8080/swagger-ui/index.html
```

## 🔒 Sécurité

BackFeed utilise Spring Security pour la gestion de l'authentification et des autorisations.

L'API implémente :
- Authentification JWT avec gestion des tokens
- Gestion des rôles et des permissions
- Protection CSRF
- Validation des entrées
- Chiffrement des mots de passe

La sécurité des données est renforcée par l'utilisation de variables d'environnement pour stocker les informations sensibles comme les identifiants de base de données et les secrets JWT.

## 🛣️ Roadmap

Fonctionnalités à venir :
- Transformation des feedbacks en tickets pour les développeurs
- Ajout de refresh token pour améliorer la sécurité de l'authentification
- Assignation automatique des tickets
- Envoi d'emails automatisé pour la gestion des tickets
- Dockerisation du projet pour faciliter le déploiement



