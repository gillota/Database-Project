# Fonctionnalités

#### Ce document a pour objectif de reprendre toutes les requêtes SQL qui ont permis d'implanter les fonctionnalités

## Création d'un compte *Membre* et *Adhésion*

Pour voir si un utilisateur s'identifie correctement avec le bon **Email** et **Mot de passe** pour utiliser les différents services, on utilise la requête suivante : 

```sql
SELECT IDUTILISATEUR, EMAILMEM, MDPMEM FROM MEMBRE WHERE EMAILMEM = ? AND MDPMEM = ?
```

Pour qu'un utilisateur puisse créer son compte via notre applicationn on utilise successivement deux requêtes :

```sql
INSERT INTO UTILISATEURS (CoutResrefuge,CoutResFormation,SommeDue,SommeRemboursee) VALUES (?, ?, ?, ?)
```

Dans cette requête, on notera que l'**IdUtilisateur** est générée automatiquement par notre base de données. Après avoir insérer cette valeur dans la table **UTILISATEURS**, on récupère la valeur de clé générée pour l'utiliser dans la requêtes permettant de créer un compte **Membre** :

```sql
INSERT INTO Membre (idUtilisateur, EmailMem, MdpMem, NomMem, PrenomMem, AdrMem) VALUES (?, ?, ?, ?, ?, ?)
```

Puisque le service d'**adhésion** n'est disponible qu'une fois authentifié, on dispose de l'**IdUtilisateur**. Ainsi, lorsque l'utilisateur souhaite souscrire à une adhésion, nous pouvons exécuter la requêtes :

```sql
INSERT INTO Adherents (idutilisateur) VALUES (?)
```

De manière analogue à l'authentification d'un compte **Membre**, un dispositif permet de vérifier si un membre est ou n'est pas un adhérent avec la requête :

```sql
SELECT * FROM Adherents WHERE IdUtilisateur = ?
```

## Parcours du catalogue

#### Affichage des formations :

Pour obtenir un tableau avec les formations triés par ordre croissant de leur date de démarrage et par ordre alphabétique, on utilise la requête suivante :

```sql
SELECT DISTINCT 
    NomFor,
    DateDebutFor,
    DureeFor,
    (NbplaceMaxFor - 
        (SELECT count(*) 
        FROM reserveformation 
        WHERE Formations.AnneeFor=reserveformation.AnneeFor AND formations.NumeroFor=reserveformation.NumeroFor
            )) AS NbPlacesRestantes, Typeactivite 
FROM 
    formations 
JOIN 
    FormationPossedeTypeActivite ON
        Formations.AnneeFor = FormationPossedeTypeActivite.AnneeFor AND Formations.NumeroFor = FormationPossedeTypeActivite.NumeroFor 
ORDER BY 
    DateDebutFor,
    NomFor
```

#### Affichage du matériel :

Pour l'affichage du matériel on sélectionne toutes les catégories que l'utilisateur peut choisir :

###
```sql  
SELECT DISTINCT 
    CatLot 
FROM
    LotMateriel
```

Puis on sélectionne toutes les sous-catégories de la catégorie mère choisie pour lui refaire choisir :

```sql
SELECT 
    CatLot 
FROM 
    CategorieFille 
WHERE
    CategorieFille.CatLotmere = ?
```

Ensuite on affiche toutes les sous-catégories de la sous-catégorie choisie :

```sql
SELECT 
    * 
FROM 
    CategorieFille 
WHERE 
    CategorieFille.CatLotmere = ?
```
Les deux dernières requêtes sont répétées jusqu'à l'arrivée à une feuille.

Enfin on affiche les lots de matériel triés par catégories :
```sql
SELECT DISTINCT
    Lotmateriel.MarqueMatLot, 
    Lotmateriel.ModeleMatLot, 
    Lotmateriel.AnneeAchatLot, 
    Lotmateriel.catlot, 
    NbPiecesLot, 
    (NbPiecesLot - 
    (SELECT count(*) nbpiecesres 
    FROM Locations 
    WHERE ((Locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (? <= Locations.DateRecup AND Locations.DateRecup <= ? ) OR ( ? <= Locations.DateRetour AND Locations.DateRetour <= ?) ))) 
    AS NbPiecesLotRestantes 
FROM 
    Lotmateriel 
WHERE CatLot = ?
```
Sinon sans parcours d'arbres, on peut trier les lots de matériel par type d'activité :

```sql
SELECT DISTINCT 
    Lotmateriel.MarqueMatLot,
    Lotmateriel.ModeleMatLot, 
    Lotmateriel.AnneeAchatLot, 
    NbPiecesLot, 
    (NbPiecesLot - 
    (SELECT count(*) nbpiecesres 
    FROM Locations 
    WHERE ((Locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (? <= Locations.DateRecup AND Locations.DateRecup <= ? ) OR ( ? <= Locations.DateRetour AND Locations.DateRetour <= ?) ))) 
    AS NbPiecesLotRestantes,
    typeActivite 
FROM lotmateriel 
JOIN lotmaterielconcernetypeactivite ON lotmateriel.MarqueMatLot = lotmaterielconcernetypeactivite.MarqueMatLot and lotmateriel.ModeleMatLot = lotmaterielconcernetypeactivite.ModeleMatLot and lotmateriel.AnneeAchatLot = lotmaterielconcernetypeactivite.AnneeAchatLot 
ORDER BY TypeActivite
```

#### Affichage des refuges :

Pour l'affichage des refuges, on dispose de type d'affichage selon le tri souhaité:

Pour le tri des refuges par nom, date de début/fin du gardiennage :

```sql
SELECT 
    nomrefuge, secgeo, dateouverture, datefermeture, nbplacerepas, nbplacedormir 
FROM 
    refuges 
ORDER BY 
    NOMREFUGE, dateouverture, datefermeture
```

Pour le tri des refuges par nombre de places disponibles ascendants :

```sql
SELECT
    nomrefuge, secgeo, dateouverture, datefermeture, NbPlacerepas, nbplacedormir 
FROM
    refuges 
ORDER BY
    Nbplacedormir, nbplacerepas
```

#### Affichage de la fiche complète des formations/refuges/matériels sélectionnés


Pour l'affichage complet de la fiche d'une formation, on a uniquement besoin de la clé identifiant une formation en paramètre :

```sql
SELECT 
    Formations.*,
    ReserveFormation.RangListeAttente,
    Formationpossedetypeactivite.typeactivite 
FROM 
    formations 
JOIN 
    Reserveformation ON formations.AnneeFor = Reserveformation.AnneeFor AND formations.NumeroFor = Reserveformation.NumeroFor 
JOIN 
    formationpossedetypeactivite ON formations.AnneeFor = formationpossedetypeactivite.AnneeFor AND formations.NumeroFor = formationpossedetypeactivite.NumeroFor 
WHERE Formations.AnneeFor = ? AND Formations.NumeroFor = ?
```


Pour l'affichage complet de la fiche d'un lot de matériel, on utilise **MarqueMatLot**, **ModeleMatLot** et **AnneeAchatLot** pour l'identifier:

```sql
SELECT
    LotMateriel.* 
FROM
    LotMateriel 
WHERE LotMateriel.MarqueMatLot = ? AND LotMateriel.ModeleMatLot = ? AND LotMateriel.AnneeAchatlot = ?
```


Pour l'affichage complet de la fiche d'un refuge l'**EmailRefuge** suffit pour identifier le refuge souhaité :

```sql
SELECT
    Refuges.*,
    numtel.numtel,
    PossedeTypepaiement.typepaiement,
    Propose.prixrepas,
    Propose.typerepas
FROM 
    Refuges 
JOIN 
    Numtel ON Refuges.emailrefuge = Numtel.emailrefuge 
JOIN 
    PossedeTypepaiement ON refuges.emailrefuge = possedetypepaiement.emailrefuge 
JOIN
    Propose ON Refuges.emailrefuge = Propose.emailrefuge 
WHERE Refuges.emailrefuge = ?
```

## Réservation d'une formation

Avant de procéder à la réservation d'une formation, on vérifie si un adhérent n'est pas déjà inscrit à la formation qu'il souhaite réserver avec la requête :

```sql
SELECT COUNT(*) FROM ReserveFormation WHERE IdUtilisateur = ? AND AnneeFor = ? AND NumeroFor = ?
```

Pour la formation, on regarde si le nombre de place est suffisant :

Pour cela, on compte le nombre de place déjà réservée par les autres adhérents :
```sql
SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?
```
On regarde également le nombre de place disponible dans la formation voulue :

```sql
SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?
```

On effectue la réservation d'une formation avec la requête suivante :
```sql
INSERT INTO ReserveFormation (AnneeFor, Numerofor, IdUtilisateur, RangListeAttente)
SELECT ?, ?, ?, 
    GREATEST(
        0, 
        (
            SELECT COUNT(IdUtilisateur) 
            FROM ReserveFormation 
            WHERE AnneeFor = ? AND Numerofor = ?
        ) - (
            SELECT NbPlaceMaxFor 
            FROM Formations 
            WHERE AnneeFor = ? AND Numerofor = ?
        ) + 1
    ) 
FROM 
    dual
```
## Réservation de matériel

Concernant la résevation de matériel, on demande dans premier temps à l'utilisateur d'identifier le lot qu'il souhaite réserver et sa période de résevartion.

On récupère le maximum de pièces présentes dans le lot de matériel :

```sql
SELECT 
    NbPiecesLot 
FROM
    LOTMATERIEL
WHERE marquematlot = ? AND anneeachatlot = ? AND modelematlot = ?
```

On soustrait le nombre de pièces réservées par les autres utilisateurs pendant la même période avec :

```sql
SELECT count(*) 
    nbpiecesres 
FROM
    Locations 
WHERE ((DateRecup <= ? AND ? <= DateRetour) OR (DateRecup <= ? AND ? <= DateRetour) OR (? <= DateRecup AND DateRecup <= ? ) OR ( ? <= DateRetour AND DateRetour <= ?) )
```

Lorsque la réservation peut se faire, on procède à une réservation en tuilisant toutes les informations précédemment fournies par l'utilisateur avec la requête :

```sql
INSERT INTO Locations (IdUtilisateur, MarqueMatLot, ModeleMatLot, AnneeAchatLot, NbPiecesRes, DateRecup, DateRetour, NbPiecesEnMoins) VALUES (?,?,?,?,?,?,?,?)
```

## Réservation d'un refuge

Pour la réservation d'un refuge, on demande à l'utilisateur d'identifier le refuge dans lequel il souhaite faire sa réservation.

Puis on contrôle l'existence du refuge souhaité avec :
```sql
SELECT COUNT(*) FROM Refuges WHERE EmailRefuge = ?
```

Ensuite, on demande plus de détails concernant la réservation à faire dans le refuge comme le nombre de jours et le nomber de repas.

On vérifie si les quantités correspondent avec la capicité du refuge, pour le nombre de chambres :
```sql
SELECT 
    R.NbPlaceDormir - COALESCE(
        (
            SELECT SUM(NbNuitsRes) 
            FROM ReservationRefuge 
            WHERE EmailRefuge = R.EmailRefuge 
            AND DateHeureRes BETWEEN ? AND ? + ?
        ), 
        0
    ) AS NbNuitsDisponibles
FROM 
    Refuges R
WHERE
    R.EmailRefuge = ?
```

Pour le nombre de repas :
```sql
SELECT
    R.NbPlaceRepas - COALESCE(
        (
            SELECT SUM(NbPlatsRes)
            FROM ReservationRefuge
            WHERE EmailRefuge = R.EmailRefuge
            AND DateHeureRes BETWEEN ? AND ? + ?
        ),
        0
    ) AS NbRepasDisponibles
FROM
    Refuges R
WHERE
    R.EmailRefuge = ?;
```

Puis lorsque c'est possible, on procède à la réservation :
```sql
INSERT INTO ReservationRefuge (IdRes, EmailRefuge, IdUtilisateur, DateHeureRes, NbNuitsRes, NbPlatsRes, PrixRes)
VALUES (?, ?, ?, ?, ?, ?,
    (
        SELECT (? * R.PrixNuit) + (? * P.PrixRepas) AS PrixRes
        FROM Refuges R
        JOIN (
            SELECT DISTINCT EmailRefuge, PrixRepas
            FROM Propose
            WHERE TypeRepas = ?
        ) P ON R.EmailRefuge = P.EmailRefuge
        WHERE R.EmailRefuge = ?
    )
);
```

On ajout le nombre de repas réservés :
```sql
INSERT INTO PossedeTypesRepas (TypeRepas, IdRes) VALUES (?, ?)
```

Enfin, on met à jour la somme due de l'utilisateur :
```sql
UPDATE Utilisateurs SET SommeDue = SommeDue + (SELECT PrixRes FROM ReservationRefuge WHERE IdRes = ?)
```

## Droit à l'oubli

Pour effacer des données sensibles d'un compte **Membre**, on simplement utilise la requête :
```sql
DELETE FROM Membre where IdUtilisateur = ?
```

Le processus de ```DELETE ON CASCADE``` intégré dans la création de nos tables nos permet de supprimer les donnnées utilisateurs que l'on ne souhaite pas conserver comme l'**Adhésion**.