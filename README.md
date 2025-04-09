# Application de Routage des Messages de Paiement

Cette application permet la gestion des messages de paiement reçus par le département de paiement de la banque via IBM MQ Series, ainsi que la configuration des partenaires MQ.

## Fonctionnalités

- Lecture et stockage des messages depuis IBM MQ Series
- Affichage de la liste des messages dans une IHM
- Consultation détaillée d'un message via une pop-up
- API REST pour la consultation des messages
- API REST pour l'ajout de partenaires
- Gestion des partenaires MQ (ajout, suppression)
- Interface utilisateur avec menu de navigation

## Stack technique

### Backend
- Java 11
- Spring Boot 2.7
- Spring JMS (IBM MQ)
- Spring Data JPA
- Maven

### Frontend
- Angular 17
- Angular Material
- TypeScript
- HTML5/CSS3

### Base de données
- MySQL 8.0

### Autres
- Docker & Docker Compose
- IBM MQ Series
- Swagger UI pour la documentation API

## Instructions de lancement avec Docker

1. Cloner le dépôt:
   ```bash
   git clone https://github.com/username/payment-router.git
   cd payment-router
   docker-compose up -d
   ```

2. Accéder à l'application:

- Frontend: http://localhost:80
- API Backend: http://localhost:8080/api
- Swagger UI: http://localhost:8080/swagger-ui.html



## URLs utiles

- Interface utilisateur: http://localhost:80
- API REST: http://localhost:8080/api
- Documentation Swagger: http://localhost:8080/swagger-ui.html
- Console IBM MQ: https://localhost:9443/ibmmq/console (user: admin, password: passw0rd)

## Tests 
- Lancer les tests unitaires:
   ```bash
   mvn test
   ```

## Simulation de messages MQ

Pour simuler l'envoi de messages sur la file MQ, vous pouvez utiliser la Console IBM MQ : https://localhost:9443/ibmmq/console  (user: admin, password: passw0rd)

ou executer cette commande 
```bash
# Se connecter au container IBM MQ
docker exec -it cacibproject_ibmmq_1 bash

# Envoyer un message de test
/opt/mqm/samp/bin/amqsput DEV.QUEUE.1 QM1
```

## Gestion des performances
Pour gérer une volumétrie importante de messages, l'application implémente:

- Traitement multi-threadé: Les messages MQ sont traités par plusieurs consommateurs concurrents (3-10 selon la charge)
- Pagination: Toutes les données sont paginées côté serveur pour optimiser les performances
- Indexation: Les colonnes fréquemment utilisées dans les requêtes sont indexées dans la base de données
- Pooling de connexions: Des pools de connexions sont utilisés pour la base de données et JMS

En cas de volumétrie plus importante, on pourrait envisager:

- Le scaling horizontal des consommateurs MQ
- La mise en place d'un système de traitement par batch
- L'ajout d'une couche de cache (Redis, Hazelcast)
- La mise en place d'un système de sharding pour la base de données

Auteur
- KHOUADRI RADOUANE - redonekh84@gmail.com