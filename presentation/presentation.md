# CQRS 
# Event Sourcing 
# DDD 
avec le framework Axon

Note:

Aujourd'hui je voudrais vous faire découvrir l'event sourcing au travers du framework Axon.

---
# Event sourcing ??

C'est quoi ?

---

Stocker les événements plutôt que les états

Note:

Souvent dans les applications on stocke les états: imaginer une table de base de données, par exemple
la base de données de DV dans résarail, ben y a tous les dv en l'état actuel des choses.

Le principe de l'event sourcing est de stocker non pas les états mais tous les changements (évenements) qui ont conduits
à l'état final.

---

Exemple d'état:

````json
{
    "numero": 123456,
    "propriétaire": "Jean Bon",
    "solde": { "valeur":15.0, "devise": "EUR" }
}
```` 

Note:

Exemple bateau. Ici on a un compte en banque. impossible de connaitre les opérations qui ont eu lieu dessus.
pour ce faire, il faut une autre table d'audit par exemple.

---

Version Event sourcing:

````json
    {
        "type_event": "ouverture_compte",
        "timestamp": 1528382470953,
        "numero": 123456,
        "propriétaire": "Jean Bon"        
    }
````
````json
    {
        "type_event": "compte_crédité",
        "timestamp": 1528382517707,
        "numero_compte": 123456,
        "montant": { "valeur":1515.0, "devise": "EUR" }
    }
````
````json
    {
        "type_event": "compte_débité",
        "timestamp": 1528382517900,
        "numero_compte": 123456,
        "montant": { "valeur":1500.0, "devise": "EUR" }
    }
````

Note:

On remarque tout de suite qu'on a beaucoup plus d'informations ici: par exemple on connait
la date de chaque opération, on voit l'évolution.

ça peut être utile. Par exemple: l'autre jour j'étais en conf avec des Gens de résarail qui cherchent à
retrouver quel canaux de ventes modifient des pnr en mode non sécurisé (cartes non pandorisées)
Et bien ils étaient incapable de savoir si l'accès via un mode non sécurisé avait eu lieu lors de la création du
pnr ou lors d'une opération d'après vente !

---

Les événements d'expriment toujours au passé. 

Ils sont immuable, car jusqu'à preuve du contraire on ne peut pas changer le passé.

Ils sont diffusés via un bus d'événéments

--- 

## Créer des événements

avec des commandes

---

Une commande est une *intention* de modification du système.

Une commande peut:

* être rejetée,
* générer un ou plusieurs événement, 
* n'avoir aucun effet. 

Note:

La commande génère des effets de bord: communication avec un partenaire extérieur, etc. 

---
## Résumé sur l'event sourcing

* Une Commande arrive                                          <!-- .element: class="fragment" data-fragment-index="0" -->
* Replay des events précédent pour retrouver l'état de l'objet <!-- .element: class="fragment" data-fragment-index="1" -->
* Exécution de la commande sur l'objet                         <!-- .element: class="fragment" data-fragment-index="2" -->
* Génération de nouveaux events.                               <!-- .element: class="fragment" data-fragment-index="3" -->

Lorsqu'une nouvelle commande arrive, on rééxecute cette chaine. <!-- .element: class="fragment" data-fragment-index="4" -->

---
# Quel rapport avec CQRS ?

Commands Queries Responsability Segregation

note: on voit bien les commandes, c'est quoi les queries ?

---

*Projection*: vue adaptée pour un use case d'une entité

Construite à partir des évents

Orientée en fonction des requêtes

On peut en ajouter autant qu'on veut en fonction de l'évolution des uses cases

note: donner des exemples

---
## Architecture ES / CQRS

![Archi Axon](axon-architecture-overview.png)

---
## Quel rapport avec le DDD ?

ça marche bien avec l'event sourcing.

note: L'event sourcing marche pas mal avec le DDD: en effet, l'application de ce pattern oblige à réfléchir en terme
métier et moins en terme "procédural": pas question ici de Api de Helper de Manager etc. On doit rester focus
sur les notions métiers (Compte, Crédit) et donc mettre en oeuvre le `language ubiquitaire` du DDD.

On peut associer les évenements de l'évent sourcing avec les Domain Events du DDD.

---
# Deux sortes de DDD

* Stratégique : recherche de "Bounded-Contexts" (domaines fonctionnels) au niveau du SI
 
* Tactique: au niveau d'un "Bounded-Context", fait apparaître le langage métier dans l'application 

note:
- le DDD stratégique: il s'applique à l'ensemble du SI. Il permet d'identifier des Bounded Context, qui sont des
sortes de modules fonctionnels au sein desquels un mot aura le même sens pour tous les acteurs que ce soit les dev
ou les experts métiers (on parle du langage ubiquitaire).

Exemple: Dans le cadre d'un parcours de ventes, la "Commande" n'aura pas le même sens si on est en cours de
 finalisation (auquel cas on souhaite avoir tout le détail) ou si on est sur le back office de paiement (auquel
 cas on aura juste l'identifiant de la commande).

- le DDD tactique: c'est une notion qui va s'appliquer sur un seul "Bounded Context", typiquement sur une application
et dans une seule équipe (c'est le plus simple). Le DDD tactique va nous aider à identifier les éléments de base de
l'application, et les définir en respectant le language commun.

Le DDD tactique fourni des briques de base pour la conception de notre application. En utilisant ces briques, on va
avoir un application orientée objets et non pas une application en couche avec des io et du procédural pour tout
tenir en glue.

.Important
****
L'idée du DDD tactique ici est de faire le lien entre le code applicatif et le langage et les concepts manipulés par
le métier.
****

---
## les éléments clés du DDD tactique

- Aggregat
- DomainEvent
- Entity
- Value Object
- Services ????

todo: faire le lien avec l'event sourcing là

---
# Mise en oeuvre

> Vous pouvez (devez) faire sans framework.

Lu sur un article de blog de Xebia sur l'ES

note: Ça c'est un gars qui n'a pas du faire beaucoup d'eventsourcing.
Sur son blog, il indique que les concepts derrière l'event sourcing sont tellement simple qu'il n'est pas
nécessaire d'utiliser un framework.

Dans mon expérience ce n'est pas le cas:

- Sur le *Hub de paiement*, nous avons utilisé l'ES mais avons pas mal galéré (pas de CQRS sur le hub,
  on rejoue pas les events...),
- Sur *hespéride*, le système d'ES est tellement compliqué qu'il a justifié la refonte complète de l'application.

D'où l'intérêt d'utiliser un framework.


---
# Axon framework

Démo

