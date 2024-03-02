package sql.Requetes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class AffichageMateriel {
    
    private void printResultSet(ResultSet rset) throws SQLException {;

        ResultSetMetaData rsMetaData = rset.getMetaData();
        
        int nbColumns = rsMetaData.getColumnCount();

        for(int i = 1; i <= nbColumns; i++) {
            System.out.print(rsMetaData.getColumnName(i) + "\t");
        }

        System.out.println("");

        while (rset.next()) {
            System.out.print(rset.getString("NOMFOR") + "\t");
            System.out.print(rset.getDate("DATEDEBUTFOR") + "\t");
            System.out.print(rset.getInt("DUREEFOR") + "\t");
            System.out.print(rset.getInt("NBPLACESRESTANTES") + "\t");
            System.out.print(rset.getString("TYPEACTIVITE") + "\t");
	        System.out.println("");
        }
    }

    /*
     * Méthode pour convertir une chaîne de date en Date sql
     */
    public static Date convertStringToSqlDate(String dateString) {
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            System.err.println("String Parser failed");
            e.printStackTrace(System.err);
            return null;
        }
    }

    private void dumpResultSet(ResultSet rset) throws SQLException {

        System.out.println("-------------------------------------");
        ResultSetMetaData rsetmd = rset.getMetaData();
        int nbColumns = rsetmd.getColumnCount();

        for(int i = 1; i <= nbColumns; i++) {
            System.out.print(rsetmd.getColumnName(i) + "\t");
        }
        System.out.println("");

        while (rset.next()) {
            for (int j = 1; j <= nbColumns; j++) {
                System.out.print(rset.getString(j) + "\t");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    public void ficheMateriel(Connection conn, Scanner scanner){
        String sqlRequestficheMateriel = "select LotMateriel.* from LotMateriel where LotMateriel.MarqueMatLot = ? and LotMateriel.ModeleMatLot = ? and LotMateriel.AnneeAchatlot = ?";

        try {
            System.out.println("");
            System.out.print("Entrez la marque du lot: ");
            String marqueMatLot = scanner.nextLine();
            System.out.print("Entrez le modèle du lot : ");
            String modeleMatLot  = scanner.nextLine();
            System.out.print("Entrez l'année du lot : ");
            int anneeAchatLot  = scanner.nextInt();
            scanner.nextLine();
            // Creation de la requete
            PreparedStatement stmtFicheMateriel = conn.prepareStatement(sqlRequestficheMateriel);
            stmtFicheMateriel.setString(1, marqueMatLot);
            stmtFicheMateriel.setString(2, modeleMatLot);
            stmtFicheMateriel.setInt(3, anneeAchatLot);

            // Execution de la requete
            ResultSet rset = stmtFicheMateriel.executeQuery();

            // Affichage du resultat
            System.out.println("");
            dumpResultSet(rset);
            System.out.println("");

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public void afficheMateriel(Connection dataBaseConn, Scanner scann) {
        String PRE_STMT = "SELECT distinct Lotmateriel.MarqueMatLot, Lotmateriel.ModeleMatLot, Lotmateriel.AnneeAchatLot, Lotmateriel.catlot, NbPiecesLot, (NbPiecesLot - (select count(*) nbpiecesres from Locations where ((Locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (? <= Locations.DateRecup AND Locations.DateRecup <= ? ) OR ( ? <= Locations.DateRetour AND Locations.DateRetour <= ?) ))) AS NbPiecesLotRestantes FROM Lotmateriel where CatLot = ?";
        try {
            String categorie =  choixCategorie(dataBaseConn, scann);
            // Creation de la requete
            PreparedStatement stmt = dataBaseConn.prepareStatement(PRE_STMT);
            System.out.println("");
            System.out.print("Date de récupération (format YYYY-MM-DD):");
            String dateRecupString = scann.next();

            Date dateRecupUser = convertStringToSqlDate(dateRecupString);

            // Date de retour
            System.out.println("");
            System.out.print("Date de retour (format YYYY-MM-DD):");
            String dateRetourString = scann.next();

            Date dateRetourUser = convertStringToSqlDate(dateRetourString);
            stmt.setDate(1, dateRecupUser);
            stmt.setDate(2, dateRecupUser);

            stmt.setDate(3, dateRetourUser);
            stmt.setDate(4, dateRetourUser);

            stmt.setDate(5, dateRecupUser);
            stmt.setDate(6, dateRetourUser);

            stmt.setDate(7, dateRecupUser);
            stmt.setDate(8, dateRetourUser);
            stmt.setString(9, categorie);

            // Execution de la requete
            ResultSet rset = stmt.executeQuery();

            // Affichage du resultat
            System.out.println("");
            printResultSet(rset);
            System.out.println("");


        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public void afficheMaterielActivite(Connection dataBaseConn, Scanner scanner) {
        String PRE_STMT = "select distinct Lotmateriel.MarqueMatLot, Lotmateriel.ModeleMatLot, Lotmateriel.AnneeAchatLot, NbPiecesLot, (NbPiecesLot - (select count(*) nbpiecesres from Locations where ((Locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (locations.DateRecup <= ? AND ? <= Locations.DateRetour) OR (? <= Locations.DateRecup AND Locations.DateRecup <= ? ) OR ( ? <= Locations.DateRetour AND Locations.DateRetour <= ?) ))) AS NbPiecesLotRestantes, typeActivite from lotmateriel JOIN lotmaterielconcernetypeactivite ON lotmateriel.MarqueMatLot = lotmaterielconcernetypeactivite.MarqueMatLot and lotmateriel.ModeleMatLot = lotmaterielconcernetypeactivite.ModeleMatLot and lotmateriel.AnneeAchatLot = lotmaterielconcernetypeactivite.AnneeAchatLot ORDER BY TypeActivite";
        try {
            // Creation de la requete
            PreparedStatement stmt = dataBaseConn.prepareStatement(PRE_STMT);
            // Date de récupération
            System.out.println("");
            System.out.print("Date de récupération (format YYYY-MM-DD):");
            String dateRecupString = scanner.next();

            Date dateRecupUser = convertStringToSqlDate(dateRecupString);

            // Date de retour
            System.out.println("");
            System.out.print("Date de retour (format YYYY-MM-DD):");
            String dateRetourString = scanner.next();

            Date dateRetourUser = convertStringToSqlDate(dateRetourString);
            stmt.setDate(1, dateRecupUser);
            stmt.setDate(2, dateRecupUser);

            stmt.setDate(3, dateRetourUser);
            stmt.setDate(4, dateRetourUser);

            stmt.setDate(5, dateRecupUser);
            stmt.setDate(6, dateRetourUser);

            stmt.setDate(7, dateRecupUser);
            stmt.setDate(8, dateRetourUser);
            // Execution de la requete
            ResultSet rset = stmt.executeQuery();

            // Affichage du resultat
            System.out.println("");
            dumpResultSet(rset);
            System.out.println("");


        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public String choixCategorie(Connection dataBaseConn, Scanner scann) throws SQLException{
        boolean existeSousCat = true;
        String categorieMere = "Maman";
        String sqlRequestSousCat = "SELECT distinct CatLot from LotMateriel";
        PreparedStatement stmt = dataBaseConn.prepareStatement(sqlRequestSousCat);    
        ResultSet result = stmt.executeQuery();
        System.out.println("\nChoisissez une catégorie \n");
        while (result.next()){
                System.out.println(result.getString("CatLot"));
            }
        categorieMere = scann.nextLine();
        while (existeSousCat){
            String sqlRequestSousCat2 = "SELECT CatLot from CategorieFille where CategorieFille.CatLotmere = ?";
            PreparedStatement stmt2 = dataBaseConn.prepareStatement(sqlRequestSousCat2);
            stmt2.setString(1, categorieMere);
            ResultSet result2 = stmt2.executeQuery();
            System.out.println("\nChoisissez une catégorie \n");
            while (result2.next()){
                System.out.println(result2.getString("CatLot"));
            }
            System.out.println("Quelle catégorie ?");
            categorieMere = scann.nextLine();
            String sqlRequestSousCat1 = "SELECT * from CategorieFille where CategorieFille.CatLotmere = ?";
            PreparedStatement stmt1 = dataBaseConn.prepareStatement(sqlRequestSousCat1);
            stmt1.setString(1, categorieMere);
            ResultSet result1 = stmt1.executeQuery();
            existeSousCat = result1.next();
        }
        return categorieMere;
    }

    public AffichageMateriel(Scanner scann, Connection dataBaseConnection) {
        boolean exit = false;

        // Choix du mode d'affichage
        while (!exit) {
        System.out.println("");
        System.out.println("1. Afficher les lots de matériel triés par catégorie");
        System.out.println("2. Afficher les lots de matériel triés par types d'activité");
        System.out.println("3. Afficher la fiche complète d'un lot de matériel");
        System.out.println("4. Revenir en arrière");
        System.out.print("Choisissez une option (1-4):");

        // Lecture de la saisie utilisateur
        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                this.afficheMateriel(dataBaseConnection, scann);
                break;
            case 2:
                this.afficheMaterielActivite(dataBaseConnection, scann);
                break;
            case 3:
                // affichage de la fiche du materiel choisi
                this.ficheMateriel(dataBaseConnection, scann);
                break;
            case 4:
                // On sort de la section affichage du materiel
                exit = true;
                break;
            default:
                break;
        }

        }
    }

}
