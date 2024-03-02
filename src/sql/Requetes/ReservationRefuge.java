package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

public class ReservationRefuge {

    public ReservationRefuge(Connection conn, Scanner scanner, int userId) {

        System.out.println("");

        boolean exit = false;

        while (!exit){
            try {    
                // Demander le mail du refuge à réserver
                System.out.print("Entrez le mail du refuge : ");
                String mailRef = scanner.nextLine(); 
    
                // Vérifier si le refuge donné existe
                String sqlExistingRefuge = "SELECT COUNT(*) FROM Refuges WHERE EmailRefuge = ?";
                PreparedStatement stmtExistingRefuge = conn.prepareStatement(sqlExistingRefuge);
                stmtExistingRefuge.setString(1, mailRef);
                ResultSet rsExistingRefuge = stmtExistingRefuge.executeQuery();
    
                if (!(rsExistingRefuge.next() && rsExistingRefuge.getInt(1) > 0)) {
                    System.out.println("\n Le refuge n'existe pas, veuillez entrez un email valide.");
                    break;
                }
                
                // Demander les spés de réservation :
                System.out.print("Entrez la date de début de votre réservation au format aaaa-mm-dd : ");
                String dateRes = scanner.nextLine();
                dateRes = "TO_DATE('" +  dateRes + " 00:00', 'yyyy-mm-dd hh24:mi')";
                System.out.print("Entrez le nombre de jours que vous souhaitez rester : ");
                int nbNuitsRes = scanner.nextInt();
                System.out.print("Entrez le nombre de repas que vous souhaitez réserver : ");
                int nbRepasRes = scanner.nextInt();
                System.out.print("Entrez le type de repas que vous souhaitez réserver, parmi : 'Dejeuner','Diner','Souper','CasseCroute' : ");
                scanner.nextLine();
                String typeRepasRes = scanner.nextLine(); 


                // Vérifier si il reste des chambres dans le refuge après res:
                String sqlChambreLeft = "SELECT R.NbPlaceDormir - COALESCE((SELECT SUM(NbNuitsRes) FROM ReservationRefuge WHERE EmailRefuge = R.EmailRefuge AND DateHeureRes BETWEEN "+ dateRes + " AND "+ dateRes + " + ?), 0) AS NbNuitsDisponibles FROM Refuges R WHERE R.EmailRefuge = ?";
                PreparedStatement stmtChambreLeft = conn.prepareStatement(sqlChambreLeft);
                stmtChambreLeft.setInt(1, nbNuitsRes);
                stmtChambreLeft.setString(2, mailRef);
                ResultSet rsChambreLeft = stmtChambreLeft.executeQuery();
                
                //Plus de place chambre
                if (!(rsChambreLeft.next() && rsChambreLeft.getInt(1) > 0)) {
                    System.out.println("\n Refuge complet : plus de chambres disponibles au vu de votre demande.");
                    break;
                }else{

                    // Vérifier si il reste des repas dans le refuge après res:
                    String sqlRepasLeft = "SELECT R.NbPlaceRepas - COALESCE((SELECT SUM(NbPlatsRes) FROM ReservationRefuge WHERE EmailRefuge = R.EmailRefuge AND DateHeureRes BETWEEN "+ dateRes + " AND "+ dateRes + " + ?), 0) AS NbRepasDisponibles FROM Refuges R WHERE R.EmailRefuge = ?";
                    PreparedStatement stmtRepasLeft = conn.prepareStatement(sqlRepasLeft);
                    stmtRepasLeft.setInt(1, nbNuitsRes);
                    stmtRepasLeft.setString(2, mailRef);
                    ResultSet rsRepasLeft = stmtRepasLeft.executeQuery();
                    
                    //Plus de place repas
                    if (!(rsRepasLeft.next() && rsRepasLeft.getInt(1) > 0)) {
                        System.out.println("\n Refuge complet : plus de repas disponibles au vu de votre demande.");
                        break;
                    } else {

                        //La réservation est valide : on lui associe un id idRes pour la suite :
                        int idRes = -1;
                        String sqlGetIdRes = "SELECT IdResRefugeSequence.NEXTVAL FROM dual";
                        PreparedStatement stmtGetIdRes = conn.prepareStatement(sqlGetIdRes);
                        ResultSet rsGetIdRes = stmtGetIdRes.executeQuery();
                        if (rsGetIdRes.next()) {
                            idRes = rsGetIdRes.getInt(1);
                        }
                        
                        //On ajoute la réservation complète
                        String sqlAddReservation = "INSERT INTO ReservationRefuge (IdRes, EmailRefuge, IdUtilisateur, DateHeureRes, NbNuitsRes, NbPlatsRes, PrixRes) VALUES (?, ?, ?, " + dateRes + ", ?, ?, (SELECT (? * R.PrixNuit) + (? * P.PrixRepas) AS PrixRes FROM Refuges R JOIN (SELECT DISTINCT EmailRefuge, PrixRepas FROM Propose WHERE TypeRepas = ?) P ON R.EmailRefuge = P.EmailRefuge WHERE R.EmailRefuge = ?))";
                        PreparedStatement stmtAddReservation = conn.prepareStatement(sqlAddReservation);
                        stmtAddReservation.setInt(1, idRes);
                        stmtAddReservation.setString(2, mailRef);
                        stmtAddReservation.setInt(3, userId);
                        stmtAddReservation.setInt(4, nbNuitsRes);
                        stmtAddReservation.setInt(5, nbRepasRes);
                        stmtAddReservation.setInt(6, nbNuitsRes);
                        stmtAddReservation.setInt(7, nbRepasRes);
                        stmtAddReservation.setString(8, typeRepasRes);
                        stmtAddReservation.setString(9, mailRef);
                        stmtAddReservation.executeUpdate();

                        //On ajoute le repas demandé dans la réservation :
                        String sqlAddRepas = "INSERT INTO PossedeTypesRepas (TypeRepas, IdRes) VALUES (?, ?)";
                        PreparedStatement stmtAddRepas = conn.prepareStatement(sqlAddRepas);
                        stmtAddRepas.setString(1, typeRepasRes);
                        stmtAddRepas.setInt(2, idRes);
                        stmtAddRepas.executeUpdate();

                        //Update de ce que doit l'utilisateur
                        String sqlSommeDueUtilisateur = "UPDATE Utilisateurs SET SommeDue = SommeDue + (SELECT PrixRes FROM ReservationRefuge WHERE IdRes = ?)";
                        PreparedStatement stmtSommeDueUtilisateur = conn.prepareStatement(sqlSommeDueUtilisateur);
                        stmtSommeDueUtilisateur.setInt(1, idRes);
                        stmtSommeDueUtilisateur.executeUpdate();
                    }
                }

                // On termine la transaction
                conn.commit();
                System.out.println("\n Réservation effectuée avec succès !");
                exit = true; 
            } catch (SQLException e) {
                System.err.println("Erreur lors de la réservation de la formation.");
                e.printStackTrace(System.err);
                System.out.println("Voulez-vous réessayer ?");
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
