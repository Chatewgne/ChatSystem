# ChatSystem

# Documentation UML de notre projet 
https://drive.google.com/open?id=1RIkG7aqpk1eCk_-1dJgchqiFzMqz4rWc

# Utilisation

Notre code s'exécute en lançant la classe Main. Pour tester les fonctionnalités, lancer le programme sur au moins deux ordinateurs connectés au même réseau local.

# Fonctionnalités implémentées

 - découverte du réseau et mise à jour en temps réel de la présence des utilisateurs (sans reprise de perte car UDP)

 - ouverture d'une conversation avec un autre utilisateur connecté

 - communication avec un autre utilisateur

 - changement de nickname

 - interfaces graphiques

 - consultation d'historiques de conversations précédentes

# Fonctionnalités à implémenter
 La réouverture de session n'est pas encore tout à fait fonctionnelle. 
 Il reste aussi à implémenter une mise à jour régulière de l'état du réseau pour compenser les éventuelles pertes du paquet (ex : un paquet de connexion perdue => il manque un utilisateur).
