# Projet_BD

Dans ce document vous trouverez toutes les informations nécessaires pour comprendre comment s'organise notre dossier et comment le prendre en main

## Structure du répertoire

- Afin de conserver une cohérence et de garder un projet homogène, l'ensemble des conventions d'écritures on était décrite dans le fichier **CONVENTION.md**.  

- Pour compiler plus facilement l'application un **Makefile** est disponible. Des explications concernant les commandes automatisées sont disponibles ci-dessous.  

- Un fichier **ojdbc6.jar** doit être ajouter au dossier pour effectuer des requêtes SQl avec l'API jdbc.  
Il se trouve à l'adresse suivante : https://chamilo.grenoble-inp.fr/main/document/document.php?cidReq=ENSIMAG4MMPBD

- Le **code de l'application** se trouve dans le répertoire **src** dans lequel on trouve :

    - Un répertoire **app** contenant les différentes pages lorsqu'on utilise l'application. C'est le fichier **MainApp.java** qui constitue le point d'entrée du programme
    -  Un répertoire **sql/Requetes** contenant les différents fichiers pour effectuer des requêtes SQL en utilisant l'API jdbc
    - Un répertoire **sql/ExemplesJava** contenant les exemples de fichiers .java pour effectuer des requêtes SQL en l'utilisant l'API jdbc. Ces fichiers sont fournis par les enseignants et ne sont pas utilisés dans notre application finale.

- Un répertoire **SricptSQL** contient les fichiers **.sql** utile pour gérer notre base de données sur le serveur *oracle1*, on y trouve :
    - **Tables.sql** pour créer nos tables dans la base de donnnées
    - **Insert.sql** pour ajouter des valeurs pertinentes dans nos tableaux
    - **DropTables.sql** pour supprimer les tables dans la base de données
    - Un répertoire **TestRequetes** où l'on trouve des tests de requêtes SQL, du pseudocode commenté pour parvenir à une requêtes SQL qui fonctionne et que l'on peut implémenter en Java

## Compilation exécution

### Avec un makefile
Un fichier Makefile est présent pour compiler et exécuter  facilement les fichier de tests

### IDE Idea Intellij
- créer un nouveau projet:
    - menu *File/New Project*
    - si le répertoire distribué est dans "~/Ensimag/2A/Projet_BD/", alors paramétrer les champs *Name* avec "Projet_BD" et *Location* avec "~/Ensimag/2A/"
- configurer l'utilisation de la librairie
    - menu *File/Project Structure* puis *Projet setting/Modules*
    - clicker sur *Add* puis "JARs & Directories" et sélectionner ~/Projet_BD/ojdbc6.jar

### IDE VS Code
- dans "~/Ensimag/2A/Projet_BD", lancer *code ."
- si vous avez installé les bonnes extensions java (exécution, debogage...) il est possible que tout fonctionne sans rien faire de spécial.
- s'il ne trouve pas la librairie, vous devez alors créer un vrai "projet" et configurer l'import du .jar.
- Pour configurer correctement l'import du .jar, dans la rubrique ```JAVA PROJECTS``` de VS Code il faudra ajouter le .jar dans dans la section ```Referenced Librairies```.


### Exécution : Accès à la Base de données *Oracle1*

Dans ce projet nous utilisons l'API *JDBC*  qui permet d'accès à des SGBD. Par conséquent, pour que l'exécution fonctionne correctement, c'est à dire qu'aucune exception n'est générée. Il fauda changer les valeurs de variables ```USER``` et ```PASSWD``` dans le fichier **"src/sql/Requetes/Authentification.java"**
par un login ensimag. Il sera également nécessaire d'éxécuter les **Tables.sql** et **Insert.sql** avec le compte choisi pour avoir sur le serveur les tables nécessaires au bon fonctionnement de l'application.

## Mode d'emploi du démonstrateur

Etant donnée les documents présents pour lancer l'application dans votre terminal placer vous tout en haut du répertoire du projet (là où se situe le Makefile) puis exécuter la commande :
```
make
```
Cela devrait compiler et exécuter le code source de l'application. Ainsi, la commande va automatiquement lancer le démonstrateur dans votre terminal.  

Par la suite laisser vous guider par les indications afficher par le démonstrateur dans le terminal.

Si vous souhaitez procéder par étape, vous pouvez exécuter :
```
make compileJavaProject
```
Pour compiler l'application

Après avoir compiler, la commande :
```
make run
```
Permet de lancer le démonstrateur dans votre terminal

Pour supprimer l'ensemble des fichiers générées à la compilation exécuter :
```
make clean
```

## Création d'une branche git
Pour créer une nouvelle branche :
```
    git checkout out -b <NOM_DE_VOTRE_BRANCHE>
```
Note : Pour le nom de la branche veuillez vous référeé au convention d'écriture dans le fichier **CONVENTION.md**.

Une fois créé il faudra publier votre branche sur le répertoire git, pour cela exécuter la commande suivante :
```
    git push --set-upstream origin <NOM_DE_VOTRE_BRANCHE>
```
