CREATE TABLE Refuges
(
    EmailRefuge VARCHAR(50) PRIMARY KEY NOT NULL,
    NomRefuge VARCHAR(50) NOT NULL,
    SecGeo VARCHAR(50) NOT NULL,
    DateOuverture DATE NOT NULL, 
    DateFermeture DATE NOT NULL,    
    NbPlaceRepas INT NOT NULL CHECK (NbPlaceRepas >= 0),
    NbPlaceDormir INT NOT NULL CHECK (NbPlaceDormir >= 0),
    TextePres VARCHAR(300) NOT NULL,
    PrixNuit INT NOT NULL CHECK (PrixNuit >= 0),
    CONSTRAINT CheckDateRefuges CHECK (DateFermeture > DateOuverture)
);

CREATE TABLE NumTel
(
    NumTel VARCHAR(10) NOT NULL PRIMARY KEY, 
    EmailRefuge VARCHAR(50) NOT NULL,
    FOREIGN KEY(EmailRefuge) REFERENCES Refuges(EmailRefuge)
    ON DELETE CASCADE
);

CREATE TABLE TypePaiement
(
    TypePaiement VARCHAR(10) NOT NULL PRIMARY KEY CHECK (TypePaiement IN ('Espece','CarteBleue','Cheque'))
);

CREATE TABLE PossedeTypePaiement
(
    EmailRefuge VARCHAR(50) NOT NULL,
    TypePaiement VARCHAR(10) NOT NULL CHECK (TypePaiement IN ('Espece','CarteBleue','Cheque')),
    PRIMARY KEY (EmailRefuge, TypePaiement),
    FOREIGN KEY (EmailRefuge) REFERENCES Refuges(EmailRefuge) ON DELETE CASCADE,
    FOREIGN KEY (TypePaiement) REFERENCES TypePaiement(TypePaiement) ON DELETE CASCADE
);

CREATE TABLE TypesRepas 
(
    TypeRepas VARCHAR(20) NOT NULL PRIMARY KEY CHECK (TypeRepas IN ('Dejeuner','Diner','Souper','CasseCroute'))
);

CREATE SEQUENCE UtilisateursIdSeq START WITH 100 INCREMENT BY 1;

CREATE TABLE Utilisateurs
(
    IdUtilisateur INT DEFAULT UtilisateursIdSeq.NEXTVAL PRIMARY KEY,
    CoutResRefuge INT NOT NULL CHECK (CoutResRefuge >= 0),
    CoutResFormation INT NOT NULL CHECK (CoutResFormation >= 0),
    SommeDue INT DEFAULT 0 CHECK (SommeDue >= 0),
    SommeRemboursee INT DEFAULT 0 CHECK(SommeRemboursee >= 0)
);

CREATE SEQUENCE IdResRefugesequence START WITH 100 INCREMENT BY 1;

CREATE TABLE ReservationRefuge 
(
    IdRes INT DEFAULT IdResRefugesequence.NEXTVAL PRIMARY KEY,
    EmailRefuge VARCHAR(50) NOT NULL,
    IdUtilisateur INT NOT NULL,
    DateHeureRes DATE NOT NULL,
    NbNuitsRes INT NOT NULL CHECK (NbNuitsRes >= 0),
    NbPlatsRes INT NOT NULL CHECK (NbPlatsRes >= 0),
    PrixRes INT NOT NULL,
    FOREIGN KEY (EmailRefuge) REFERENCES Refuges(EmailRefuge),
    FOREIGN KEY (IdUtilisateur) REFERENCES Utilisateurs(IdUtilisateur) ON DELETE CASCADE
);

CREATE TABLE PossedeTypesRepas
(
    TypeRepas VARCHAR(20) NOT NULL CHECK (TypeRepas IN ('Dejeuner','Diner','Souper','CasseCroute')),
    IdRes INT NOT NULL,
    FOREIGN KEY (TypeRepas) REFERENCES TypesRepas(TypeRepas) ON DELETE CASCADE,
    FOREIGN KEY (IdRes) REFERENCES ReservationRefuge(IdRes) ON DELETE CASCADE,
    PRIMARY KEY (TypeRepas, IdRes)
);

CREATE TABLE Propose
(
    EmailRefuge VARCHAR(50) NOT NULL,
    TypeRepas VARCHAR(20) NOT NULL CHECK (TypeRepas IN ('Dejeuner','Diner','Souper','CasseCroute')),
    PrixRepas INT NOT NULL, CHECK (PrixRepas >= 0),
    FOREIGN KEY (EmailRefuge) REFERENCES Refuges(EmailRefuge) ON DELETE CASCADE,
    FOREIGN KEY (TypeRepas) REFERENCES TypesRepas(TypeRepas) ON DELETE CASCADE,
    PRIMARY KEY (EmailRefuge,TypeRepas)
);

CREATE TABLE Membre
(   
    IdUtilisateur INT NOT NULL PRIMARY KEY,
    EmailMem VARCHAR(30) NOT NULL UNIQUE,
    MdpMem VARCHAR(30) NOT NULL,
    NomMem VARCHAR(30) NOT NULL,
    PrenomMem VARCHAR(30) NOT NULL,
    AdrMem VARCHAR(30) NOT NULL,
    FOREIGN KEY (IdUtilisateur) REFERENCES Utilisateurs(IdUtilisateur) ON DELETE CASCADE  
);

CREATE SEQUENCE IdAdhSeq START WITH 100 INCREMENT BY 1;
CREATE TABLE Adherents
(  
    IdAdh INT DEFAULT IdAdhSeq.NEXTVAL,
    IdUtilisateur INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (IdUtilisateur) REFERENCES Utilisateurs(IdUtilisateur) ON DELETE CASCADE
);

CREATE TABLE CategorieMere
(
    CatLot VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE LotMateriel
(
    MarqueMatLot VARCHAR(30) NOT NULL,
    ModeleMatLot VARCHAR(30) NOT NULL,
    AnneeAchatLot INT NOT NULL,
    CatLot VARCHAR(20) NOT NULL,
    PrixDegr INT NOT NULL CHECK (PrixDegr >= 0),
    NbPiecesLot INT NOT NULL CHECK (NbPiecesLot > 0),
    AnneePer INT NOT NULL CHECK (AnneePer >= 0),
    FOREIGN KEY (CatLot) REFERENCES CategorieMere(CatLot),
    PRIMARY KEY (MarqueMatLot,ModeleMatLot,AnneeAchatLot),
    CONSTRAINT CheckAnnee CHECK (AnneePer > AnneeAchatLot)
);

CREATE SEQUENCE LocIdSeq START WITH 100 INCREMENT BY 1;

CREATE TABLE Locations
(
    IdLoc INT DEFAULT LocIdSeq.NEXTVAL PRIMARY KEY,
    IdUtilisateur INT NOT NULL,
    MarqueMatLot VARCHAR(30) NOT NULL,
    ModeleMatLot VARCHAR(30) NOT NULL,
    AnneeAchatLot INT NOT NULL,
    NbPiecesRes INT NOT NULL CHECK (NbPiecesRes > 0),
    DateRecup DATE NOT NULL,
    DateRetour DATE NOT NULL,
    NbPiecesEnMoins INT NOT NULL CHECK (NbPiecesEnMoins >= 0),
    FOREIGN KEY (IdUtilisateur) REFERENCES Utilisateurs(IdUtilisateur) ON DELETE CASCADE,
    FOREIGN KEY (MarqueMatLot, ModeleMatLot, AnneeAchatLot) REFERENCES LotMateriel(MarqueMatLot, ModeleMatLot, AnneeAchatLot),
    CONSTRAINT CheckNbPieces CHECK (NbPiecesRes >= NbPiecesEnMoins),
    CONSTRAINT CheckDateLoc CHECK (DateRetour > DateRecup)
    
);

CREATE TABLE TypesActivite
(
    TypeActivite VARCHAR(20) NOT NULL PRIMARY KEY CHECK (TypeActivite IN ('Randonnee','Escalade','Alpinisme','Speleologie','SkiDeRando','CascadeDeGlace'))
);

CREATE TABLE LotMaterielConcerneTypeActivite 
(
    MarqueMatLot VARCHAR(30) NOT NULL,
    ModeleMatLot VARCHAR(30) NOT NULL,
    AnneeAchatLot INT NOT NULL,
    TypeActivite VARCHAR(20) NOT NULL CHECK (TypeActivite IN ('Randonnee','Escalade','Alpinisme','Speleologie','SkiDeRando','CascadeDeGlace')),
    FOREIGN KEY (MarqueMatLot, ModeleMatLot, AnneeAchatLot) REFERENCES LotMateriel(MarqueMatLot, ModeleMatLot, AnneeAchatLot) ON DELETE CASCADE,
    FOREIGN KEY (TypeActivite) REFERENCES TypesActivite(TypeActivite) ON DELETE CASCADE,
    PRIMARY KEY (MarqueMatLot,ModeleMatLot,AnneeAchatLot,TypeActivite)
);

CREATE TABLE CategorieFille
(
    CatLot VARCHAR(20) NOT NULL,
    CatLotMere VARCHAR(20) NOT NULL,
    FOREIGN KEY (CatLotMere) REFERENCES CategorieMere(CatLot) ON DELETE CASCADE,
    PRIMARY KEY (CatLot, CatLotMere)
);

CREATE TABLE TexteLot
(
    TexteLot VARCHAR(300) NOT NULL PRIMARY KEY,
    MarqueMatLot VARCHAR(30) NOT NULL,
    ModeleMatLot VARCHAR(30) NOT NULL,
    AnneeAchatLot INT NOT NULL,
    FOREIGN KEY (MarqueMatLot, ModeleMatLot, AnneeAchatLot) REFERENCES LotMateriel(MarqueMatLot, ModeleMatLot, AnneeAchatLot) ON DELETE CASCADE
);

CREATE TABLE Formations
(
    AnneeFor INT NOT NULL,
    NumeroFor INT NOT NULL CHECK (NumeroFor >= 0),
    NomFor VARCHAR(50) NOT NULL,
    DateDebutFor DATE NOT NULL,
    DureeFor INT NOT NULL CHECK (DureeFor >= 0),
    NbPlaceMaxFor INT NOT NULL CHECK (NbPlaceMaxFor >= 0),
    DescriptionFor VARCHAR(300) NOT NULL,
    PrixFor INT NOT NULL CHECK (PrixFor >= 0),
    PRIMARY KEY (AnneeFor, NumeroFor)
);

CREATE TABLE ReserveFormation
(
    IdUtilisateur INT NOT NULL,
    AnneeFor INT NOT NULL,
    NumeroFor INT NOT NULL CHECK (NumeroFor >= 0),
    RangListeAttente INT NOT NULL CHECK (RangListeAttente >= 0),
    FOREIGN KEY (IdUtilisateur) REFERENCES Utilisateurs(IdUtilisateur) ON DELETE CASCADE,
    FOREIGN KEY (AnneeFor, NumeroFor) REFERENCES Formations(AnneeFor, NumeroFor),
    PRIMARY KEY (IdUtilisateur,AnneeFor,NumeroFor)
);

CREATE TABLE FormationPossedeTypeActivite 
(
    AnneeFor INT NOT NULL,
    NumeroFor INT NOT NULL CHECK (NumeroFor >= 0),
    TypeActivite VARCHAR(20) NOT NULL CHECK (TypeActivite IN ('Randonnee','Escalade','Alpinisme','Speleologie','SkiDeRando','CascadeDeGlace')),
    FOREIGN KEY (AnneeFor, NumeroFor) REFERENCES Formations(AnneeFor, NumeroFor) ON DELETE CASCADE,
    FOREIGN KEY (TypeActivite) REFERENCES TypesActivite(TypeActivite) ON DELETE CASCADE,
    PRIMARY KEY (AnneeFor,NumeroFor,TypeActivite)
);
