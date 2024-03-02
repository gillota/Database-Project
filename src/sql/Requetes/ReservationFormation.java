package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

public class ReservationFormation {

    public ReservationFormation(Connection conn, Scanner scanner, int userId) {

        System.out.println("");

        boolean exit = false;

        while (!exit){
            try {    
                // Demander le numéro et l'année de la formation
                System.out.print("Entrez le numéro de la formation : ");
                int numeroFor = scanner.nextInt();
                System.out.print("Entrez l'année de la formation : ");
                int anneeFor = scanner.nextInt();
                scanner.nextLine(); 
    
                // Vérifier si l'utilisateur est adhérent
                String sqlCheckAdherent = "SELECT COUNT(*) FROM Adherents WHERE IdUtilisateur = ?";
                PreparedStatement stmtAdherent = conn.prepareStatement(sqlCheckAdherent);
                stmtAdherent.setInt(1, userId);
                ResultSet rsAdherent = stmtAdherent.executeQuery();
    
                if (rsAdherent.next() && rsAdherent.getInt(1) > 0) {

                    // Verifier si l'utilisateur est pas déjà inscrit à la formation
                    String sqlAlreadyInscrit = "SELECT COUNT(*) FROM ReserveFormation WHERE IdUtilisateur = ? AND AnneeFor = ? AND NumeroFor = ?";
                    PreparedStatement stmtAlreadyInscrit = conn.prepareStatement(sqlAlreadyInscrit);
                    stmtAlreadyInscrit.setInt(1, userId);
                    stmtAlreadyInscrit.setInt(2, anneeFor);
                    stmtAlreadyInscrit.setInt(3, numeroFor);
                    ResultSet rsAlreadyInscrit = stmtAlreadyInscrit.executeQuery();

                    if (rsAlreadyInscrit.next() && rsAlreadyInscrit.getInt(1) > 0) {
                        System.out.println("\n Vous avez déja réservé cette formation. \n Retour au Menu Principal :");
                    } else {
                        // Vérifier la disponibilité des places
                        String sqlCountInscrits = "SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?";
                        PreparedStatement stmtCountInscrits = conn.prepareStatement(sqlCountInscrits);
                        stmtCountInscrits.setInt(1, anneeFor);
                        stmtCountInscrits.setInt(2, numeroFor);
                        ResultSet rsCountInscrits = stmtCountInscrits.executeQuery();
        
                        String sqlCountPlaces = "SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?";
                        PreparedStatement stmtCountPlaces = conn.prepareStatement(sqlCountPlaces);
                        stmtCountPlaces.setInt(1, anneeFor);
                        stmtCountPlaces.setInt(2, numeroFor);
                        ResultSet rsCountPlaces = stmtCountPlaces.executeQuery();
        
                        if (rsCountInscrits.next() && rsCountPlaces.next() && rsCountInscrits.getInt(1) >= rsCountPlaces.getInt(1)) {
                            // Cas liste d'attente
                            int placeListeAttente = rsCountInscrits.getInt(1) - rsCountPlaces.getInt(1) + 1;
                            System.out.println("\n La formation est pleine, vous avec été placé en liste d'attente pour cette formation à la place n°" + placeListeAttente);
                        } else {
                            // Cas il y a de la place
                            System.out.println("\n Il reste de la place pour cette formation, réservation en cours...");
                        }
        
                        // Effectuer la réservation
                        String sqlReserverFormation = "INSERT INTO ReserveFormation (AnneeFor, Numerofor, IdUtilisateur, RangListeAttente) SELECT ?, ?, ?, GREATEST(0, (SELECT COUNT(IdUtilisateur) FROM ReserveFormation WHERE AnneeFor = ? AND Numerofor = ?) - (SELECT NbPlaceMaxFor FROM Formations WHERE AnneeFor = ? AND Numerofor = ?) + 1) FROM dual";
                        PreparedStatement stmtReserver = conn.prepareStatement(sqlReserverFormation);
                        stmtReserver.setInt(1, anneeFor);
                        stmtReserver.setInt(2, numeroFor);
                        stmtReserver.setInt(3, userId);
                        stmtReserver.setInt(4, anneeFor);
                        stmtReserver.setInt(5, numeroFor);
                        stmtReserver.setInt(6, anneeFor);
                        stmtReserver.setInt(7, numeroFor);
                        stmtReserver.executeUpdate();
        
                        // On termine la transaction
                        conn.commit();
                        System.out.println("Réservation effectuée avec succès.");
                    }
                    exit = true;
                } else {
                    System.out.println("Seuls les adhérents peuvent réserver. Voulez-vous réentrer votre IdUtilisateur ?");
                    System.out.println("1. Réessayer");
                    System.out.println("2. Quitter l'application");
    
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 
    
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            exit = true;
                            break;
                        default:
                            exit = true;                        
                            break;
                        }
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la réservation de la formation.");
                e.printStackTrace(System.err);
                System.out.println("Voulez-vous réentrer votre IdUtilisateur ?");
                    System.out.println("1. Réessayer");
                    System.out.println("2. Quitter l'application");
    
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 
    
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            exit = true;
                            break;
                        default:
                            exit = true;                        
                            break;
                    }
            }
        }
    }
}