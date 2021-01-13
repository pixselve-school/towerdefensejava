# Utilisation d'un fichier `.tdl` pour la création d'un niveau

Un fichier `.tdl` doit se trouver dans le dossier `levels` du jeux et doit suivre la spécification suivante.

## Choix de la musique

Votre musique au format WAVE `.wav` doit se trouver dans le dossier `music`. Ce champ est optionnel, la musique par
default sera Glorious Morning.

Ajoutez à votre fichier `.tdl` : `MUSIC_PATH=music/nom_de_la_musique.wav`

## Nombre de vie

Un nombre entre 1 (pour les plus sadiques) et 9999 (pour les plus généreux) qui correspondra au nombre de vies du
joueur.

Ajoutez à votre fichier `.tdl` : `LIFE=9999`

## Argent au démarrage

Un nombre entre 50 et 999999 qui correspondra à la somme d'argent que le joueur va recevoir au lancement de la partie.

Ajoutez à votre fichier `.tdl` : `MONEY=9999`

## Chemin

Une liste d'instructions composée d'une longueur et d'une direction. Le chemin suivra cette liste d'instructions. Les
directions sont :

* `U`: Haut
* `D`: Bas
* `L`: Gauche
* `R`: Droite

Ajoutez à votre fichier `.tdl` : `PATH=5D2L`

## Ajout de tuiles vides sur les quatre côtés

Une suite de nombres entre 0 et 999999 qui ajoutera ce nombre de tuiles vide sur le côté qui correspond. Ce champ est
optionnel, la valeur par défaut est calculée de façon à que chaque tuile soit carrée.

Ajoutez à votre fichier `.tdl` : `PADDING=HAUT,BAS,GAUCHE,DROITE`. Par exemple `PADDING=5,5,9,9` pour 5 tuiles vides en
haut et en bas et 9 tuiles vides à droite et à gauche.

## Ajouter de l'eau

Un nombre prenant comme valeur :

* 1 : De l'eau sera présente autours de la carte
* 0 : De l'eau ne sera pas présente

Ce champ est optionnel, la valeur par défaut sera `1`.

Ajoutez à votre fichier `.tdl` : `WATER=0` ou `WATER=1`

## Vagues

Une liste de vagues sous la forme `[VAGUE]` séparées par des points-virgules. Une vague est composée d'une liste de
monstres associés à un delai d'apparition (en seconde) sous la forme `(ID_DU_MONSTRE,DELAI)` séparés par des
underscores (`_`). Le delai est décompté lorsque le monstre précédent est apparu.

Ajoutez à votre fichier `.tdl` : `WAVES=[(1,1)_(5,1)];[(5,1)]`

## Controle de la végétation

Un nombre prenant comme valeur :

* 0 : La végétation sera absente
* 1 : Des petits buissons et fleurs seront présents
* 2 : Des petits buissons, fleurs et arbres seront présents

Ce champ est optionnel, la valeur par défaut sera `2`.

Ajoutez à votre fichier `.tdl` : `PLANTS=0` ou `PLANTS=1` ou `PLANTS=2`