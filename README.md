# MineralContest
Plugin pour minecraft 1.14.4 fortement inspiré du plugin "Mineral Contest" de Squeezie.

À renseigner dans la classe GlobalData.java :

Localisation du spawn de départ.

Localisation des spawns de chaque équipes.

Localisation de l'arène pour la téléportation.

Localisation du coffre d'arène.

Localisation des coffres de chaque équipes.

Une fois ces changements effectués, pour compiler un .jar ce plugin, n'oubliez d'y inclure le fichier plugin.yml.

4 équipes de 4 joueurs maximum.

Les commandes sont : 
```shell
/mcontest nbteam [nombre d'équipes à ajouter au jeu]
```

```shell
/mcontest addteam [numero de l'équipe] [nom du joueur]
```

```shell
/mcontest print
```
Cette commande donne un récapitulatif des commandes précédemment exécutées.

```shell
/mcontest start
```
Commence le jeu avec les paramètres précédemment entrés.

```shell
/mcontest reset
```
Arrête le jeu si il est commencé et remet les paramètres à zéro.

```shell
/arena
```
Permet de se téléporter à l'arène si un coffre d'arène est présent.



Pour utiliser le plugin, mettez MineralContest.jar dans le dossier plugin de votre serveur.


Pour modifier le code source, ajoutez la librairie spigot.jar de votre serveur dans le projet.


