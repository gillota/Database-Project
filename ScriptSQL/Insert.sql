INSERT INTO Refuges VALUES('refugedegex@gmail.com', 'Refuge de Gex','Ain',TO_DATE('2023-12-02','yyyy-mm-dd'),TO_DATE('2024-04-05','yyyy-mm-dd'),50,35,'Blablablablablabla',55);
INSERT INTO Refuges VALUES('refugelaval@gmail.com', 'Refuge Laval','Hates-Alpes',TO_DATE('2023-12-09','yyyy-mm-dd'),TO_DATE('2024-03-24','yyyy-mm-dd'),25,15,'Blablablablablabla',45);
INSERT INTO Refuges VALUES('refugedelaigle@gmail.com', 'Refuge de l Aigle','Hautes-Alpes',TO_DATE('2023-12-02','yyyy-mm-dd'),TO_DATE('2024-04-17','yyyy-mm-dd'),78,57,'Blablablablablabla',75);
INSERT INTO Refuges VALUES('refugedesmerveilles@gmail.com', 'Refuge des Merveilles','Alpes-Maritimes',TO_DATE('2023-12-22','yyyy-mm-dd'),TO_DATE('2024-04-05','yyyy-mm-dd'),54,47,'Blablablablablabla',62);
INSERT INTO Refuges VALUES('refugetempleecrins@gmail.com', 'Refuge Temple-Ecrins','Isere',TO_DATE('2023-12-24','yyyy-mm-dd'),TO_DATE('2024-01-02','yyyy-mm-dd'),15,15,'Blablablablablabla',300);

INSERT INTO NumTel VALUES ('0161380023','refugedegex@gmail.com');
INSERT INTO NumTel VALUES('0425651325','refugelaval@gmail.com');
INSERT INTO NumTel VALUES('0414548745','refugedelaigle@gmail.com');
INSERT INTO NumTel VALUES('0856424154','refugedesmerveilles@gmail.com');
INSERT INTO NumTel VALUES('0545985652','refugetempleecrins@gmail.com');

INSERT INTO TypePaiement (TypePaiement) VALUES ('Cheque');
INSERT INTO TypePaiement (TypePaiement) VALUES ('Espece');
INSERT INTO TypePaiement (TypePaiement) VALUES ('CarteBleue');

INSERT INTO PossedeTypePaiement VALUES ('refugedegex@gmail.com','Cheque');
INSERT INTO PossedeTypePaiement VALUES('refugelaval@gmail.com','Espece');
INSERT INTO PossedeTypePaiement VALUES('refugedelaigle@gmail.com','CarteBleue');
INSERT INTO PossedeTypePaiement VALUES('refugedesmerveilles@gmail.com','Espece');
INSERT INTO PossedeTypePaiement VALUES('refugetempleecrins@gmail.com','Espece');

INSERT INTO TypesRepas VALUES('Dejeuner');
INSERT INTO TypesRepas VALUES('Diner');
INSERT INTO TypesRepas VALUES('Souper');
INSERT INTO TypesRepas VALUES('CasseCroute');


INSERT INTO Utilisateurs VALUES (0, 1275, 30, 1305, 0);
INSERT INTO Utilisateurs VALUES(1, 140, 0, 140, 0);
INSERT INTO Utilisateurs VALUES(2, 772, 40, 812, 0);
INSERT INTO Utilisateurs VALUES(3, 557, 20, 577, 0);
INSERT INTO Utilisateurs VALUES(4, 200, 0, 200, 0);

INSERT INTO ReservationRefuge VALUES (0, 'refugedegex@gmail.com', 2, TO_DATE('2023-12-08 14:45', 'yyyy-mm-dd hh24:mi'), 0, 2, 22);
INSERT INTO ReservationRefuge VALUES(1, 'refugedelaigle@gmail.com', 4, TO_DATE('2024-01-08 18:00', 'yyyy-mm-dd hh24:mi'), 2, 5, 200);
INSERT INTO ReservationRefuge VALUES(2, 'refugetempleecrins@gmail.com', 0, TO_DATE('2023-12-24 11:15', 'yyyy-mm-dd hh24:mi'), 4, 0, 1200);
INSERT INTO ReservationRefuge VALUES(3, 'refugedesmerveilles@gmail.com', 3, TO_DATE('2024-02-15 12:00', 'yyyy-mm-dd hh24:mi'), 6, 12, 492);
INSERT INTO ReservationRefuge VALUES(4, 'refugelaval@gmail.com', 1, TO_DATE('2024-03-14 23:00', 'yyyy-mm-dd hh24:mi'), 2, 5, 140);
INSERT INTO ReservationRefuge VALUES(5, 'refugetempleecrins@gmail.com', 2, TO_DATE('2023-12-30 18:30', 'yyyy-mm-dd hh24:mi'), 2, 3, 750);
INSERT INTO ReservationRefuge VALUES(6, 'refugedelaigle@gmail.com', 0, TO_DATE('2023-12-21 19:0', 'yyyy-mm-dd hh24:mi'), 1, 0, 75);
INSERT INTO ReservationRefuge VALUES(7, 'refugedegex@gmail.com', 3, TO_DATE('2024-02-14 14:45', 'yyyy-mm-dd hh24:mi'), 1, 1, 65);


INSERT INTO PossedeTypesRepas VALUES('Dejeuner', 0);
INSERT INTO PossedeTypesRepas VALUES('CasseCroute', 0);
INSERT INTO PossedeTypesRepas VALUES('Dejeuner', 1);
INSERT INTO PossedeTypesRepas VALUES('Diner', 1);
INSERT INTO PossedeTypesRepas VALUES('Souper', 1);
INSERT INTO PossedeTypesRepas VALUES('CasseCroute', 1);
INSERT INTO PossedeTypesRepas VALUES('Diner', 3);
INSERT INTO PossedeTypesRepas VALUES('Souper', 3);
INSERT INTO PossedeTypesRepas VALUES('Dejeuner', 4);
INSERT INTO PossedeTypesRepas VALUES('Diner', 4);
INSERT INTO PossedeTypesRepas VALUES('Souper', 4);
INSERT INTO PossedeTypesRepas VALUES('CasseCroute', 4);
INSERT INTO PossedeTypesRepas VALUES('Souper', 5);
INSERT INTO PossedeTypesRepas VALUES('Diner', 5);
INSERT INTO PossedeTypesRepas VALUES('Souper', 6);
INSERT INTO PossedeTypesRepas VALUES('Dejeuner', 7);


INSERT INTO Propose VALUES ('refugedegex@gmail.com', 'Dejeuner', 10);
INSERT INTO Propose VALUES('refugelaval@gmail.com', 'Dejeuner', 8);
INSERT INTO Propose VALUES('refugelaval@gmail.com', 'Diner', 13);
INSERT INTO Propose VALUES('refugelaval@gmail.com', 'Souper', 16);
INSERT INTO Propose VALUES('refugelaval@gmail.com', 'CasseCroute', 9);
INSERT INTO Propose VALUES('refugedelaigle@gmail.com', 'Dejeuner', 10);
INSERT INTO Propose VALUES('refugedelaigle@gmail.com', 'Diner', 10);
INSERT INTO Propose VALUES('refugedelaigle@gmail.com', 'Souper', 10);
INSERT INTO Propose VALUES('refugedelaigle@gmail.com', 'CasseCroute', 10);
INSERT INTO Propose VALUES('refugedesmerveilles@gmail.com', 'Diner', 10);
INSERT INTO Propose VALUES('refugedesmerveilles@gmail.com', 'Souper', 10);
INSERT INTO Propose VALUES('refugetempleecrins@gmail.com', 'Diner', 50);
INSERT INTO Propose VALUES('refugetempleecrins@gmail.com', 'Souper', 50);


INSERT INTO Membre VALUES (0, 'romain.chevret@gmail.com', 'ratio', 'Chevret', 'Romain', 'Nancy');
INSERT INTO Membre VALUES(1, 'killian.guibert@gmail.com', 'yann le boss', 'Guibert', 'Killian', 'Courbevoie');
INSERT INTO Membre VALUES(2, 'raphael.danan@gmail.com', 'Blitz1234', 'Danan', 'Raphael', 'Rueil Malmaison');
INSERT INTO Membre VALUES(3, 'alexandre.vrecko@gmail.com', 'Blitz5678', 'Vrecko', 'Alexandre', 'St Germain en Layes');
INSERT INTO Membre VALUES(4, 'aurelie.gillot@gmail.com', 'BlitzBlitz', 'Gillot', 'Aurélie', 'Nancy');

INSERT INTO Adherents VALUES(0,0);
INSERT INTO Adherents VALUES(2,1);
INSERT INTO Adherents VALUES(3,2);

INSERT INTO CategorieMere (CatLot) VALUES ('Escalade');
INSERT INTO CategorieMere (CatLot) VALUES ('Ski');
INSERT INTO CategorieMere (CatLot) VALUES ('Camping');
INSERT INTO CategorieMere (CatLot) VALUES ('Vêtements');
INSERT INTO CategorieMere (CatLot) VALUES ('Alpinisme');


INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Bloc', 'Escalade');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Voie', 'Escalade');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Falaises', 'Escalade');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Piste', 'Ski');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Randonnée', 'Ski');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Camping gear', 'Camping');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Vêtements chauds', 'Vêtements');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Chaussures', 'Vêtements');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Glaciaire', 'Alpinisme');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Escalade glaciaire', 'Alpinisme');

INSERT INTO CategorieMere (CatLot) VALUES ('Camping gear');
INSERT INTO CategorieMere (CatLot) VALUES ('Vêtements chauds');

INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Réchaud', 'Camping gear');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Matelas', 'Camping gear');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Polaire', 'Vêtements chauds');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Gants', 'Vêtements chauds');

INSERT INTO CategorieMere (CatLot) VALUES ('Polaire');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Polaire Low', 'Polaire');
INSERT INTO CategorieFille (CatLot, CatLotMere) VALUES ('Polaire Blitz', 'Polaire');

INSERT INTO LotMateriel VALUES ('Black Diamond', 'Solution Harness', 2022, 'Escalade', 120, 2, 2024);
INSERT INTO LotMateriel VALUES('Salomon', 'QST 106 Skis', 2020, 'Ski', 600, 1, 2022);
INSERT INTO LotMateriel VALUES('MSR', 'Hubba Hubba NX Tent', 2019, 'Camping', 400, 1, 2021);
INSERT INTO LotMateriel VALUES('The North Face', 'Summit Series', 2021, 'Vêtements', 350, 5, 2023);
INSERT INTO LotMateriel VALUES('Petzl', 'Laser Speed Ice Screw', 2020, 'Alpinisme', 80, 4, 2023);
INSERT INTO LotMateriel VALUES('Mammut', 'Barryvox S Beacon', 2022, 'Alpinisme', 350, 1, 2024);
INSERT INTO LotMateriel VALUES('Arc''teryx', 'Beta AR Jacket', 2018, 'Vêtements', 500, 3, 2020);
INSERT INTO LotMateriel VALUES('La Sportiva', 'Miura Climbing Shoes', 2021, 'Escalade', 150, 2, 2023);
INSERT INTO LotMateriel VALUES('Patagonia', 'Nano Puff Jacket', 2019, 'Vêtements', 200, 2, 2022);
INSERT INTO LotMateriel VALUES('Black Diamond', 'ClimbOn Bar', 2020, 'Escalade', 10, 10, 2022);

INSERT INTO Locations VALUES (1, 0, 'Black Diamond', 'Solution Harness', 2022, 1, TO_DATE('2023-05-15', 'yyyy-mm-dd'), TO_DATE('2023-05-20', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(2, 1, 'Salomon', 'QST 106 Skis', 2020, 1, TO_DATE('2023-02-10', 'yyyy-mm-dd'), TO_DATE('2023-02-15', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(3, 2, 'MSR', 'Hubba Hubba NX Tent', 2019, 1, TO_DATE('2022-07-01', 'yyyy-mm-dd'), TO_DATE('2022-07-10', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(4, 3, 'The North Face', 'Summit Series', 2021, 3, TO_DATE('2023-08-05', 'yyyy-mm-dd'), TO_DATE('2023-08-15', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(5, 4, 'Petzl', 'Laser Speed Ice Screw', 2020, 2, TO_DATE('2023-01-20', 'yyyy-mm-dd'), TO_DATE('2023-01-25', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(6, 0, 'Mammut', 'Barryvox S Beacon', 2022, 1, TO_DATE('2023-06-10', 'yyyy-mm-dd'), TO_DATE('2023-06-15', 'yyyy-mm-dd'), 1);
INSERT INTO Locations VALUES(7, 1, 'Arc''teryx', 'Beta AR Jacket', 2018, 10, TO_DATE('2022-12-01', 'yyyy-mm-dd'), TO_DATE('2022-12-10', 'yyyy-mm-dd'), 2);
INSERT INTO Locations VALUES(8, 2, 'La Sportiva', 'Miura Climbing Shoes', 2021, 1, TO_DATE('2023-04-20', 'yyyy-mm-dd'), TO_DATE('2023-04-25', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(9, 3, 'Patagonia', 'Nano Puff Jacket', 2019, 1, TO_DATE('2022-09-15', 'yyyy-mm-dd'), TO_DATE('2022-09-20', 'yyyy-mm-dd'), 0);
INSERT INTO Locations VALUES(10, 4, 'Black Diamond', 'ClimbOn Bar', 2020, 5, TO_DATE('2022-03-05', 'yyyy-mm-dd'), TO_DATE('2022-03-15', 'yyyy-mm-dd'), 1);

INSERT INTO TypesActivite (TypeActivite) VALUES ('Randonnee');
INSERT INTO TypesActivite (TypeActivite) VALUES ('Escalade');
INSERT INTO TypesActivite (TypeActivite) VALUES ('Alpinisme');
INSERT INTO TypesActivite (TypeActivite) VALUES ('Speleologie');
INSERT INTO TypesActivite (TypeActivite) VALUES ('SkiDeRando');
INSERT INTO TypesActivite (TypeActivite) VALUES ('CascadeDeGlace');

INSERT INTO LotMaterielConcerneTypeActivite VALUES ('Black Diamond', 'Solution Harness', 2022, 'Escalade');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Salomon', 'QST 106 Skis', 2020, 'SkiDeRando');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('MSR', 'Hubba Hubba NX Tent', 2019, 'CascadeDeGlace');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('The North Face', 'Summit Series', 2021, 'Randonnee');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Petzl', 'Laser Speed Ice Screw', 2020, 'Alpinisme');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Mammut', 'Barryvox S Beacon', 2022, 'Alpinisme');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Arc''teryx', 'Beta AR Jacket', 2018, 'Speleologie');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('La Sportiva', 'Miura Climbing Shoes', 2021, 'Escalade');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Patagonia', 'Nano Puff Jacket', 2019, 'Randonnee');
INSERT INTO LotMaterielConcerneTypeActivite VALUES('Black Diamond', 'ClimbOn Bar', 2020, 'Speleologie');


INSERT INTO TexteLot VALUES ('BlackDiamond_SolutionHarness_2022', 'Black Diamond', 'Solution Harness', 2022);
INSERT INTO TexteLot VALUES('Salomon_QST106Skis_2020', 'Salomon', 'QST 106 Skis', 2020);
INSERT INTO TexteLot VALUES('MSR_HubbaHubbaNXTent_2019', 'MSR', 'Hubba Hubba NX Tent', 2019);
INSERT INTO TexteLot VALUES('TheNorthFace_SummitSeries_2021', 'The North Face', 'Summit Series', 2021);
INSERT INTO TexteLot VALUES('Petzl_LaserSpeedIceScrew_2020', 'Petzl', 'Laser Speed Ice Screw', 2020);
INSERT INTO TexteLot VALUES('Mammut_BarryvoxSBeacon_2022', 'Mammut', 'Barryvox S Beacon', 2022);
INSERT INTO TexteLot VALUES('Arcteryx_BetaARJacket_2018', 'Arc''teryx', 'Beta AR Jacket', 2018);
INSERT INTO TexteLot VALUES('LaSportiva_MiuraClimbingShoes_2021', 'La Sportiva', 'Miura Climbing Shoes', 2021);
INSERT INTO TexteLot VALUES('Patagonia_NanoPuffJacket_2019', 'Patagonia', 'Nano Puff Jacket', 2019);
INSERT INTO TexteLot VALUES('BlackDiamond_ClimbOnBar_2020', 'Black Diamond', 'ClimbOn Bar', 2020);


INSERT INTO Formations VALUES (2023, 1, 'Initiation à lEscalade en Bloc', TO_DATE('2023-02-01', 'yyyy-mm-dd'), 2, 15, 'Formation pour débutants en escalade de bloc.', 150);
INSERT INTO Formations VALUES(2023, 2, 'Ski de Randonnée Découverte', TO_DATE('2023-03-15', 'yyyy-mm-dd'), 1, 10, 'Formation découverte en ski de randonnée.', 200);
INSERT INTO Formations VALUES(2023, 3, 'Alpinisme sur Glace Express', TO_DATE('2023-04-10', 'yyyy-mm-dd'), 3, 12, 'Formation express en alpinisme sur glace.', 250);
INSERT INTO Formations VALUES(2023, 4, 'Ski Freestyle Intro', TO_DATE('2023-05-20', 'yyyy-mm-dd'), 2, 8, 'Formation introductive au ski freestyle.', 180);
INSERT INTO Formations VALUES(2023, 5, 'Initiation à lAlpinisme', TO_DATE('2023-06-15', 'yyyy-mm-dd'), 2, 20, 'Formation pour débutants en alpinisme.', 200);
INSERT INTO Formations VALUES(2023, 6, 'Escalade Sportive Intensive', TO_DATE('2023-07-10', 'yyyy-mm-dd'), 3, 15, 'Formation intensive en escalade sportive.', 250);
INSERT INTO Formations VALUES(2023, 7, 'Ski de Fond Débutant', TO_DATE('2023-08-05', 'yyyy-mm-dd'), 1, 10, 'Formation pour débutants en ski de fond.', 120);
INSERT INTO Formations VALUES(2023, 8, 'Randonnée en Montagne', TO_DATE('2023-09-15', 'yyyy-mm-dd'), 2, 12, 'Formation de randonnée en montagne.', 180);
INSERT INTO Formations VALUES(2023, 9, 'Alpinisme Avancé', TO_DATE('2023-10-20', 'yyyy-mm-dd'), 3, 8, 'Formation avancée en alpinisme.', 300);
INSERT INTO Formations VALUES(2023, 10, 'Ski Freeride Experience', TO_DATE('2023-11-10', 'yyyy-mm-dd'), 2, 10, 'Expérience de ski freeride.', 220);

INSERT INTO ReserveFormation VALUES (0, 2023, 1, 0);
INSERT INTO ReserveFormation VALUES(0, 2023, 2, 0);
INSERT INTO ReserveFormation VALUES(0, 2023, 3, 0);
INSERT INTO ReserveFormation VALUES(1, 2023, 1, 1);
INSERT INTO ReserveFormation VALUES(1, 2023, 4, 1);
INSERT INTO ReserveFormation VALUES(1, 2023, 5, 0);
INSERT INTO ReserveFormation VALUES(2, 2023, 5, 1);
INSERT INTO ReserveFormation VALUES(2, 2023, 6, 0);
INSERT INTO ReserveFormation VALUES(2, 2023, 8, 0);
INSERT INTO ReserveFormation VALUES(3, 2023, 5, 2);
INSERT INTO ReserveFormation VALUES(3, 2023, 7, 0);
INSERT INTO ReserveFormation VALUES(3, 2023, 9, 1);
INSERT INTO ReserveFormation VALUES(4, 2023, 4, 0);
INSERT INTO ReserveFormation VALUES(4, 2023, 10, 0);

INSERT INTO FormationPossedeTypeActivite VALUES (2023, 1, 'Escalade');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 2, 'SkiDeRando');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 3, 'Alpinisme');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 4, 'Randonnee');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 5, 'Speleologie');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 6, 'Escalade');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 7, 'SkiDeRando');
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 8, 'Randonnee');  
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 9, 'Alpinisme'); 
INSERT INTO FormationPossedeTypeActivite VALUES(2023, 10, 'SkiDeRando');
