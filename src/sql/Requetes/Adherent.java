package sql.Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Adherent {

    // Méthode pour vérifier si un membre est un adhérent    
    public static boolean checkIsAlreadyAnAdherent(Connection conn, int idUser) {

        String sqlReqToCreateNewMember = "SELECT * FROM Adherents WHERE IdUtilisateur = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlReqToCreateNewMember);
    
            stmt.setInt(1, idUser);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // C'est un adhérent
                System.out.println("vous êtes un adhérent");
                return true;
            }
            System.out.println("ce n'est pas un adhérent");
        } catch (SQLException e) {
            System.err.println("failed adherent creation");
            e.printStackTrace(System.err);
        }
        return false;
    }

    public boolean insertNewAdherent(Connection conn, int idUser) {

        String sqlReqToCreateNewMember = "INSERT INTO Adherents (idutilisateur) VALUES (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlReqToCreateNewMember);
    
            stmt.setInt(1, idUser);

            // TODO : Rajouter la facturation dans le SommeDue

            int memberRowsCount = stmt.executeUpdate();
            if (memberRowsCount > 0) {
                // Terminaison de la transaction
                conn.commit();

                System.out.println("Votre adhésion a été validée avec succès.");
                return true;
            } else {
                System.out.println("Une erreur s'est produite...");
            }
        } catch (SQLException e) {
            System.err.println("failed adherent creation");
            e.printStackTrace(System.err);
        }
        return false;
    }

    public Adherent(Scanner scann,Connection conn, int userId) {

        // Entré pour confirmer l'adhésion
        System.out.println("");
        System.out.println("Vous vous apprétez à souscrire à une adhésion.");
        System.out.println("Veuillez confirmer votre choix avant de procéder à la facturation :");
        System.out.println("1. Oui, je veux devenir un adhérent");
        System.out.println("2. Non, revenir en arrière");
        System.out.print("Choisissez une option (1-2):");

        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                this.insertNewAdherent(conn, userId);
                break;
            case 2:
                break;
            default:
                break;
       }
    }
}
