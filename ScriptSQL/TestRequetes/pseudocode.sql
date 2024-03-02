/*
RESERVATION FORMATION : Check si un adhérent peut réserver une 
formation ie il reste de la place : Check le nbre de place max de 
la formation puis le nbre de gens déjà inscrit.
*/

if ((SELECT COUNT(*) FROM Adherents WHERE IdUtilisateur = ?)){ 
    /*Vérifie que le user ne soit pas déjà inscrit*/
    if(!(SELECT COUNT(*) FROM ReserveFormation WHERE IdUtilisateur = ? AND AnneeFor = ? AND NumeroFor = ?))

    if ((SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?) > (SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?)){/*Cas liste d'attente*/
        print("liste d'attente : place = " + (SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?) - (SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?) + 1)
    } else {/*Cas ya la place*/
        print("ya la place trkl")
    }
    INSERT into ReserveFormation SELECT ?, ?, ?, GREATEST(0, (SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?) - (SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?) + 1)
    FROM dual;
} else {
    print("Seul les adhérents peuvent réserver.")
}


/*
RESERVATION FORMATION : 
Mise à jour de la liste d'attente en cas d'annulation
*/


/*Affichage de toutes les resfor d'un utilisateur*/
SELECT AnneeFor, NumeroFor FROM ReserveFormation WHERE IdUtilisateur = ?
/*il choisit celle qu'il veut supprimer et on la supprime. il choisit et on récupère ce qu'il prend*/

/*Check si c'est valide*/
SELECT * FROM ReservationRefuge WHERE AnneeFor = ? AND NumeroFor = ? AND IdUtilisateur = ?
/*Si oui (si le select renvoie qqchose):*/
UPDATE ReserveFormation SET RangListeAttente = RangListeAttente - 1 WHERE AnneeFor = ? AND Numerofor = ? AND RangListeAttente > (SELECT RangListeAttente FROM ReserveFormation WHERE IdUtilisateur = ? AND AnneeFor = ? AND Numerofor = ?) AND RangListeAttente > 0;
DELETE FROM ReserveFormation WHERE IdUtilisateur = ? AND AnneeFor = ? AND Numerofor = ?;
/*Si non, redemander une formation valide a supprimer*/







/*
RESERVATION REFUGE : 
Check si ya la place en repas puis en nuits dans la boucle if : il faut faire des phrases à print dans les deux cas
et si oui ajoute la réservation : on insert dans ReservationRefuge avec les données associés, on calcule le prix 
de la réservation et on rajoute ce prix à ce que nous doit l'utilisateur.
*/


if (
    (SELECT R.NbPlaceRepas - COALESCE((SELECT SUM(NbPlatsRes) FROM ReservationRefuge WHERE EmailRefuge = R.EmailRefuge AND DateHeureRes BETWEEN ? AND ? + ?), 0) AS NbRepasDisponibles FROM Refuges R WHERE R.EmailRefuge = ?) > 0){

    if((SELECT 
        R.NbPlaceDormir - COALESCE((SELECT SUM(NbNuitsRes) FROM ReservationRefuge WHERE EmailRefuge = R.EmailRefuge AND DateHeureRes BETWEEN ? AND ? + ?), 0) AS NbNuitsDisponibles
    FROM 
        Refuges R
    WHERE 
        R.EmailRefuge = ?) > 0){
    
        idRes = IdResRefugeSequence.NEXTVAL /*Sequence SQL pour la table ReservationRefuge*/

        

        INSERT INTO ReservationRefuge (IdRes, EmailRefuge, IdUtilisateur, DateHeureRes, NbNuitsRes, NbPlatsRes, PrixRes) VALUES (?, ?, ?, ?, ?, ?, (SELECT (? * R.PrixNuit) + (? * P.PrixRepas) AS PrixRes FROM Refuges R JOIN (SELECT EmailRefuge, PrixRepas FROM Propose WHERE TypeRepas = (SELECT TypeRepas FROM PossedeTypesRepas WHERE IdRes = idRes)) P ON R.EmailRefuge = P.EmailRefuge WHERE R.EmailRefuge = ?))

        UPDATE Utilisateurs SET SommeDue = SommeDue + (SELECT PrixRes FROM ReservationRefuge WHERE IdRes = idRes);


    } else {
        print("Refuge complet : plus de chambres disponibles")
    }    
} else {
    print("Refuge complet : plus de repas disponibles")
}

/*
RESERVATION REFUGE : 
Mise à jour des disponibilité en cas d'annulation est faite automatiquement, il suffit de supprimer la réservation, cependant il faut aussi
mettre à jour ce que nous doit l'utilisateur.
*/

/*Affichage de toutes les res d'un utilisateur*/
SELECT EmailRefuge, DateHeureRes, IdRes FROM ReservationRefuge WHERE IdUtilisateur = ?
/*il choisit l'idRes de celle qu'il veut supprimer et on la supprime.il choisit et on récupère ce qu'il prend*/

/*Check si l'idRes est valide*/
SELECT IdRes FROM ReservationRefuge WHERE IdRes = ? AND IdUtilisateur = ?
/*Si oui : (si le select renvoie qqchose), on update le prixdû.*/
UPDATE Utilisateurs SET SommeDue = SommeDue - (SELECT PrixRes FROM ReservationRefuge WHERE IdRes = idRes) WHERE IdUtilisateur = (SELECT IdUtilisateur FROM ReservationRefuge WHERE IdRes = idRes);
DELETE FROM ReservationRefuge WHERE IdRes = ?
/*Si non, redemander un IdRes valide*/