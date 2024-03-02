package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

public class AfficheRefuge {

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

    private void printResultSet(ResultSet rset) throws SQLException {;
        // On affiche les tableau en fonction du type des données
        System.out.println("-------------------------------------");

        ResultSetMetaData rsMetaData = rset.getMetaData();
        int nbColumns = rsMetaData.getColumnCount();
        for(int i = 1; i <= nbColumns; i++) {
            System.out.print(rsMetaData.getColumnName(i) + "\t");
        }
        System.out.println("");

        while (rset.next()) {
            System.out.print(rset.getString("NOMREFUGE") + "\t");
            System.out.print(rset.getString("SECGEO") + "\t");
            System.out.print(rset.getDate("DATEOUVERTURE") + "\t");
            System.out.print(rset.getDate("DATEFERMETURE") + "\t");
            System.out.print(rset.getInt("NBPLACEREPAS") + "\t");
            System.out.print(rset.getInt("NBPLACEDORMIR") + "\t");
	        System.out.println("");
        }
        System.out.println("-------------------------------------");
    }

    private void refugeOrdered(String sqlRequest, Connection conn) {
        try {
            // Creation de la requete
            PreparedStatement stmt = conn.prepareStatement(sqlRequest);

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

    private void ficheRefuge(Connection conn, Scanner scanner) {

        String sqlRequestficheRefuge = "select Refuges.*, numtel.numtel, PossedeTypepaiement.typepaiement, Propose.prixrepas, Propose.typerepas from Refuges JOIN Numtel ON Refuges.emailrefuge = Numtel.emailrefuge JOIN PossedeTypepaiement ON refuges.emailrefuge = possedetypepaiement.emailrefuge JOIN Propose ON Refuges.emailrefuge = Propose.emailrefuge where Refuges.emailrefuge = ?";

        try {
            System.out.println("");
            System.out.print("Entrez l'email du refuge : ");
            String emailRefuge = scanner.nextLine();
            // Creation de la requete
            PreparedStatement stmtFicheRefuge = conn.prepareStatement(sqlRequestficheRefuge);
            stmtFicheRefuge.setString(1, emailRefuge);

            // Execution de la requete
            ResultSet rset = stmtFicheRefuge.executeQuery();

            // Affichage du resultat
            System.out.println("");
            dumpResultSet(rset);
            System.out.println("");

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public AfficheRefuge(Scanner scann, Connection dataBaseConnection) {
        boolean exit = false;

        // Choix du mode d'affichage
        while (!exit) {
        System.out.println("");
        System.out.println("Trier l'affichage des refuges par :");
        System.out.println("1. Nom, Date de début/fin du gardiennage");
        System.out.println("2. Nombre de places disponibles");
        System.out.println("3. Afficher la fiche complète d'un refuge");
        System.out.println("4. Revenir en arrière");
        System.out.print("Choisissez une option (1-4):");

        // Lecture de la saisie utilisateur
        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                // requête des refuges ordonnés par Nom, Date de début/fin du gardiennage
                String sqlRequestRefugeOrderedByName = "select nomrefuge, secgeo, dateouverture, datefermeture, nbplacerepas, nbplacedormir from refuges order by NOMREFUGE, dateouverture, datefermeture";
                this.refugeOrdered(sqlRequestRefugeOrderedByName, dataBaseConnection);
                break;
            case 2:
                // requête des refuges ordonnés par le nombre de places disponibles
                String sqlRequestRefugeOrderedByPlace = "select nomrefuge, secgeo, dateouverture, datefermeture, NbPlacerepas, nbplacedormir from refuges order by Nbplacedormir, nbplacerepas";
                this.refugeOrdered(sqlRequestRefugeOrderedByPlace, dataBaseConnection);
                break;
            case 3:
                // affichage de la fiche du refuge choisi
                this.ficheRefuge(dataBaseConnection, scann);
                break;
            case 4:
                // On sort de la section affichage des refuges
                exit = true;
                break;
            default:
                break;
        }

        }
    }
}
