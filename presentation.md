# Présentation: grandes lignes

Application Bank

## Setup: appli spring boot

* découpage en couche

## Premiers events et commandes

* avec un aggregat: Compte
* avec une commande: OuvrirCompte
* avec une commande: CréditerCompte
* avec un event bus
* tout en mémoire
* création d'une projection du compte

## Persistence

* Ajouter un stockage en bdd (mysql ou autre)

## Création d'une nouvelle projection

* liste des comptes
* mise à jour en temps réél ? comment faire ?

## limites de l'aggregat

* Virer de l'argent d'un compte à un autre
* Saga ? 

## Clustering 

* Faire deux instances avec load balancing ?
* partage de l'eventbus

