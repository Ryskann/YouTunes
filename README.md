# Youtunes
## Description du projet
Ce projet android est développé en Kotlin et permet aux utilisateurs de trouver les concerts et événements de leurs artistes préférés. Cette application utilise l'API de spotify pour récupérer les 10 artistes les plus écouté dans le mois et l'API de ticketmaster pour afficher les détails de chaques concerts ainsi que de rendre accessible le lien de vente des billets.
## Fonctionalités 
- **Authentification Spotify** : L'utilisateur doit ce connecté à Spotify pour accéder à ses données d'écoutes.  
- **Liste des artistes** : L'application android récupère les artistes les plus écoutés par l'utilisateur pendant le dernier mois.  
- **Recherches des concerts** : Pour chaque artiste l'application affichera les données de concerts  et événements prévus.  
## Prérequis
- Un téléphone android sous android avec le niveau d'API 33
- Un compte Spotify Developer 
- Un compte Ticketmaster Developer 
## Installation de l'application sur android studio
- Obtenir les clés api des différentes application
- Dans le fichier local.properties écrire ceci
```
Spotify_client_id=CLIENT_ID
Spotify_client_secret=CLIENT_SECRET
Tiketmaster_consumer_key=CONSUMER_KEY
```
- Synchroniser et exécuter le projet
## Bibliothèques Utilisées
- okhttp3
- Glide
- Lifecycle
