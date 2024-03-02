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


Insert into typesactivite values('Randonnee');
Insert into typesactivite values('Escalade');
Insert into typesactivite values('Speleologie');
Insert into typesactivite values('Alpinisme');
Insert into typesactivite values('SkiDeRando');
Insert into typesactivite values('CascadeDeGlace');
Insert into lotmateriel values ('Marque', 'Modele', 2019, 'Categorie', 100, 5, 2029)
Insert into lotmateriel values ('MarqueSki', 'glisse', 2016, 'Ski', 200, 5, 2034)
Insert into lotmateriel values ('MarqueGant', 'Alys', 2021, 'Gant', 10, 1, 2026)

select distinct MarqueMatLot, ModeleMatLot, CatLot, NbPiecesLot, (NbPiecesLot - (select count(*) from locations where lotmateriel.MarqueMatLot = locations.MarqueMatLot and lotmateriel.ModeleMatLot = locations.ModeleMatLot and lotmateriel.AnneeAchatLot = locations.AnneeAchatLot)) AS NbPiecesLotRestantes from lotmateriel ORDER BY CatLot 
select distinct MarqueMatLot, ModeleMatLot, TypeActivite, NbPiecesLot, (NbPiecesLot - (select count(*) from locations where lotmateriel.MarqueMatLot = locations.MarqueMatLot and lotmateriel.ModeleMatLot = locations.ModeleMatLot and lotmateriel.AnneeAchatLot = locations.AnneeAchatLot)) AS NbPiecesLotRestantes, TypeActivite from lotmateriel for lotmateriel JOIN lotmaterielconcernetypeactivite.MarqueMatLot = lotmateriel.MarqueMatLot and lotmaterielconcernetypeactivite.ModeleMatLot = lotmateriel.ModeleMatLot and lotmaterielconcernetypeactivite.AnneeAchatLot = lotmateriel.AnneeAchatLot ORDER BY TypeActivite
select * from Categorie
