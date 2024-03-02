Truncate table Adherents;
Drop table Adherents;
Truncate table Membre;
Drop table Membre;
Truncate table utilisateurs;
Drop table utilisateurs;
Truncate table Numtel;
Drop table Numtel;
Truncate table Possedetypepaiement;
Drop table Possedetypepaiement;
Truncate table PossedeTypesRepas;
Drop table PossedeTypesRepas;
Truncate table propose;
Drop table propose;
Truncate table ReservationRefuge;
Drop table ReservationRefuge;
Truncate table refuges;
Drop table refuges;
Truncate table categoriefille;
Drop table categoriefille;
Truncate table Locations;
Drop table Locations;
Truncate table typepaiement;
Drop table typepaiement;
Truncate table formationpossedetypeactivite;
Drop table formationpossedetypeactivite;
Truncate table reserveformation;
Drop table reserveformation;
Truncate table formations;
Drop table formations;
Truncate table typesrepas;
Drop table typesrepas;
Truncate table utilisateurs;
Drop table utilisateurs;
Truncate table lotmaterielconcernetypeactivite;
Drop table lotmaterielconcernetypeactivite;
Truncate table textelot;
Drop table textelot;
Truncate table lotmateriel;
Drop table lotmateriel;
Truncate table categoriemere;
Drop table categoriemere;
Truncate table typesactivite;
Drop table typesactivite;
Drop sequence UtilisateursIdSeq;
Drop sequence Locidseq;
Drop sequence IdAdhseq;
Drop sequence IdResRefugesequence;


Insert into typesactivite values('Randonnee');
Insert into typesactivite values('Escalade');
Insert into typesactivite values('Speleologie');
Insert into typesactivite values('Alpinisme');
Insert into typesactivite values('SkiDeRando');
Insert into typesactivite values('CascadeDeGlace');
INSERT INTO formations VALUES (2020, 1, 'montagne', to_date('01/10/2020', 'dd/mm/yyyy'), 10, 10, 'form', 40);
INSERT INTO formations VALUES (2020, 2, 'montagne et mer', to_date('01/09/2020', 'dd/mm/yyyy'), 9, 11, 'blitz', 50);
INSERT INTO formations VALUES (2020, 3, 'abricot', to_date('01/09/2020', 'dd/mm/yyyy'), 9, 11, 'blitz', 50);

Insert into formationpossedetypeactivite values(2020, 1, 'Randonnee');
Insert into formationpossedetypeactivite values(2020, 2, 'Randonnee');
Insert into formationpossedetypeactivite values(2020, 2, 'Escalade');
Insert into formationpossedetypeactivite values(2020, 3, 'Alpinisme');

Insert into utilisateurs values(1, 0, 0, 0, 0);
Insert into membre values(1, 'teste', 'teste', 'teste', 'teste', 'teste'); 
Insert into reserveformation values(1, 2020, 2, 0);

Insert into refuges values('romain.chevret@gmail.com', 'teste', 'teste', to_date('31-08-2020', 'dd-mm-yyyy'), to_date('01/01/2021', 'dd/mm/yyyy'), 8, 7, 'ok', 100);
Insert into refuges values('rom1.chevret@gmail.com', 'aeste', 'teste', to_date('29-08-2020', 'dd-mm-yyyy'), to_date('01/01/2021', 'dd/mm/yyyy'), 8, 7, 'ok', 100);
Insert into refuges values('rom.chevret@gmail.com', 'veste', 'teste', to_date('28-08-2020', 'dd-mm-yyyy'), to_date('01/01/2021', 'dd/mm/yyyy'), 10, 6, 'ok', 100);

Insert into reservationrefuge values(1, 'romain.chevret@gmail.com', 1, TO_DATE('10-09-2020 12:30', 'dd-mm-yyyy HH24:MI'), 5, 10, 200);

select distinct NomFor, DateDebutFor, DureeFor, (NbplaceMaxFor - (select count(*) from reserveformation WHERE Formations.AnneeFor = reserveformation.AnneeFor AND formations.NumeroFor = reserveformation.NumeroFor)) AS NbPlacesRestantes , Typeactivite from formations JOIN FormationPossedeTypeActivite ON Formations.AnneeFor = FormationPossedeTypeActivite.AnneeFor AND Formations.NumeroFor = FormationPossedeTypeActivite.NumeroFor ORDER BY DateDebutFor, NomFor;
select nomrefuge, secgeo, dateouverture, datefermeture, nbplacerepas, nbplacedormir from refuges order by NOMREFUGE, dateouverture, datefermeture;
select nomrefuge, secgeo, dateouverture, datefermeture, NbPlacerepas, nbplacedormir from refuges order by Nbplacedormir, nbplacerepas;


/* condition pour vérifier adhérents */
if ((SELECT COUNT(*) FROM Adherents WHERE IdUtilisateur = ?))

if (NbPiecesLot from ?/* lotmateriel de l'utilisateur */ - count(*) nbpiecesres from Locations where (DateRecup <= ?/* Daterecup entrée par utilisateur*/ <= DateRetour or DateRecup <= ?/* Dateretour entrée par utilisateur*/ <= DateRetour)) <= ?/* NbPiecesRes rentré par l'utilisateur */;

/* Mise à jour du Lot si perte ou casse */

UPDATE LotMateriel SET NbPiecesLot -= NbPiecesEnMoins
UPDATE Utilisateurs SET SommeDue += select PrixDegr from ?/* lotmateriel de l'utilisateur */