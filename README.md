# 📱 Application Mobile - Aidant (Frontend Kotlin)

Ce dépôt contient le code source de l’application Android destinée aux **aidants**. Elle est développée en **Kotlin** avec **Jetpack Compose** en suivant l’architecture **MVVM (Model - ViewModel - ViewModel)**.

---

## 🧩 Fonctionnalités principales

### 🔐 Authentification sécurisée

- Connexion avec email et mot de passe
- Réinitialisation et changement de mot de passe

### 🏠  (HomeScreen)

- Nombre total de personnes malvoyantes suivies
- Notifications récentes
- Voir le status de toutes les malvoyantes
- Bouton « Voir détails » pour chaque demande

### 🆘 Gestion des Demandes d’Aide

- Visualisation des demandes d’aide des malvoyants associés

### 📍 Suivi de Localisation

- Suivi **en temps réel** de la position GPS des malvoyants associés (si autorisé)
- Affichage de la carte intégrée avec marqueurs en direct
- Notification en cas de sortie de zone prédéfinie (optionnel)

### 🔔 Notifications Push 

- Réception des notifications instantanées en cas de :
    - Nouvelle demande d’aide

### 👤 Gestion de Profil

- Visualisation et modification des informations personnelles
- Déconnexion accessible depuis toutes les pages

---

## 🏗️ Architecture du projet

Le projet suit une organisation claire en couches **MVVM** :

---

## 🔧 Configuration

### 🔗 Vérification de l’URL de base de l’API

Dans le fichier `api/RetrofitClient.kt`, modifiez la constante suivante selon votre environnement (local ou production) :

```kotlin
private const val BASE_URL = "http://<votre_ip_ou_nom_de_domaine>:<port>/api/"
private const val BASE_URL_ASSISTANCE = "http://<votre_ip_ou_nom_de_domaine>:<port>/api/"


