package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

public class AffichageFormation {

    // Une requête très longue
    static final String PRE_STMT = "select distinct NomFor, DateDebutFor, DureeFor, (NbplaceMaxFor - (select count(*) from reserveformation WHERE Formations.AnneeFor = reserveformation.AnneeFor AND formations.NumeroFor = reserveformation.NumeroFor)) AS NbPlacesRestantes , Typeactivite from formations JOIN FormationPossedeTypeActivite ON Formations.AnneeFor = FormationPossedeTypeActivite.AnneeFor AND Formations.NumeroFor = FormationPossedeTypeActivite.NumeroFor ORDER BY DateDebutFor, NomFor";
    
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

    public void afficheFormation(Connection dataBaseConn) {
        try {
            // Creation de la requete
            PreparedStatement stmt = dataBaseConn.prepareStatement(PRE_STMT);

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

    public void ficheFormation(Connection conn, Scanner scanner){
        String sqlRequestficheFormation = "select Formations.*, ReserveFormation.RangListeAttente, Formationpossedetypeactivite.typeactivite from formations JOIN Reserveformation ON formations.AnneeFor = Reserveformation.AnneeFor and formations.NumeroFor = Reserveformation.NumeroFor JOIN formationpossedetypeactivite ON formations.AnneeFor = formationpossedetypeactivite.AnneeFor and formations.NumeroFor = formationpossedetypeactivite.NumeroFor where Formations.AnneeFor = ? and Formations.NumeroFor = ?";

        try {
            System.out.println("");
            System.out.print("Entrez l'année de la formation : ");
            int anneeFor = scanner.nextInt();
            System.out.print("Entrez le numéro de la formation : ");
            int numeroFor  = scanner.nextInt();
            scanner.nextLine();
            // Creation de la requete
            PreparedStatement stmtFicheFormation = conn.prepareStatement(sqlRequestficheFormation);
            stmtFicheFormation.setInt(1, anneeFor);
            stmtFicheFormation.setInt(2, numeroFor);

            // Execution de la requete
            ResultSet rset = stmtFicheFormation.executeQuery();

            // Affichage du resultat
            System.out.println("");
            dumpResultSet(rset);
            System.out.println("");

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public AffichageFormation(Scanner scann, Connection dataBaseConnection) {
        boolean exit = false;

        // Choix du mode d'affichage
        while (!exit) {
        System.out.println("");
        System.out.println("1. Afficher les formations");
        System.out.println("2. Afficher la fiche complète d'une formation");
        System.out.println("3. Revenir en arrière");
        System.out.print("Choisissez une option (1-3):");

        // Lecture de la saisie utilisateur
        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                this.afficheFormation(dataBaseConnection);
                break;
            case 2:
                // affichage de la fiche du refuge choisi
                this.ficheFormation(dataBaseConnection, scann);
                break;
            case 3:
                // On sort de la section affichage des refuges
                exit = true;
                break;
            default:
                break;
        }

        }
    }
} 
