# GIT COMMIT

Nommer les messages de commits Le message d’un commit doit être clair et concis, il devra suivre la syntaxe suivante :  
- [**type de commit**] *Résumé du commit*  
 
il doit indiquer ce qui a été modifié et la raison de cette modification.

Voici les différents **types de commit** à utiliser :
- ADD: Ajout d’une nouvelle fonctionnalité;  
    Exemple : `[ADD] Un nouveau fichier`  
- SUB: Supression d’une fonctionnalité;  
    Exemple : `[SUB] fichier toto.txt inutile`  
- UPDT : Mise à jour d'une fonctionnalité;  
    Exemple : `[UPDT] Ajout du paramètre *truc* à la fonction *test*`  
- FIX: Correction d’un bug;  
    Exemple : `[FIX] Prises en compte des excetpions`  
- DOCS: Ajout ou modification de documentation;  
    Exemple : `[DOCS] Ajout de commentaires`  
- REFACTOR : Modification n’ajoutant pas de fonctionnalités ni de correction de
  bug (renommage d’une variable, suppression de code redondant, simplification
  du code);  
    Exemple : `[REFACTOR] variable *bidulle* en *machin*`

# GIT BRANCH

Les noms de branches devront suivre la syntaxe suivante :  
- **Caractéristiques de la branche**-suite-du-message

La *suite du message* doit indiquer le sujet de la branche.

Voici les différents **Caractéristiques de la branche** possible :
- init: Initialisation d'un nouveau composant pour le projet;  
    Exemple : `init-bases-projet`   
- feat: Ajout d’une nouvelle fonctionnalité;  
    Exemple : `feat-construction-de-la-classe-principale`   
- fix : correction de bugs;  
    Exemple : `fix-bugs-importants`

# MERGE REQUEST

Lors de la création d'une merge request, le nom du merge devra suivre la syntaxe suivante :  
- **Fonctionnalité principale du merge** : *Titre de la merge request*

La *Titre de la merge request* doit décrire la raison du merge ou les principales modifications apportées.

Voici les différents **Fonctionnalité principale du merge** possible :
- Init: Initialisation d'un nouveau composant pour le projet;  
    Exemple : `Init : Bases du projet`   
- feat: Ajout d’une nouvelle fonctionnalité;  
    Exemple : `Feat : Construction de la classe principale`   
- fix : correction de bugs;  
    Exemple : `Fix  : Bugs importants sur les fonctions de tests`