package sql.Requetes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ReservationMateriel {

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

    /*
     * On récupère le nombre de pièces réservés par les autres utilisateurs pendant la période indiquée
     */
    private int getNbPieceRes(Connection conn, Date dateRecupUser, Date dateRetourUser ) {
        try{
            // Creation de la requete
            String sqlReqToGetNbPieceLot = "select count(*) nbpiecesres from Locations where ((DateRecup <= ? AND ? <= DateRetour) OR (DateRecup <= ? AND ? <= DateRetour) OR (? <= DateRecup AND DateRecup <= ? ) OR ( ? <= DateRetour AND DateRetour <= ?) )";

            PreparedStatement stmt = conn.prepareStatement(sqlReqToGetNbPieceLot);
            stmt.setDate(1, dateRecupUser);
            stmt.setDate(2, dateRecupUser);

            stmt.setDate(3, dateRetourUser);
            stmt.setDate(4, dateRetourUser);

            stmt.setDate(5, dateRecupUser);
            stmt.setDate(6, dateRetourUser);

            stmt.setDate(7, dateRecupUser);
            stmt.setDate(8, dateRetourUser);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int nbPieceLotRes = rs.getInt(1);
                return nbPieceLotRes;
            }
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
        // To indicate an error
        return -1;
    }

    private int getNbPieceLot(Connection conn, String marqueMatLot, int anneeAchatLot, String modeleMatLot) {
        try{
            // Creation de la requete
            String sqlReqToGetNbPieceLot = "select NbPiecesLot from LOTMATERIEL WHERE marquematlot = ? AND anneeachatlot = ? AND modelematlot = ?";

            PreparedStatement stmt = conn.prepareStatement(sqlReqToGetNbPieceLot);
            stmt.setString(1, marqueMatLot);
            stmt.setInt(2, anneeAchatLot);
            stmt.setString(3, modeleMatLot);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                int nbPieceLot = rs.getInt(1);
                return nbPieceLot;
            } else {
                System.out.println("Le lot que vous avez choisi n'existe pas.");
            }
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
        // To indicate an error
        return -1;
    }

    /*
     * Renvoie vrai si le lot de matériel identifié par l'utilisateur est disponible
     * durant la période indiqué par l'utilisateur et en nombre suffisant
     * Renvoie faux sinon 
     */
    private boolean checkIsAnAvailableLoc(Scanner scanner, Connection connection, String marqueMatLot, String modeleMatLot, int anneeAchatLot, int nbPieceResUser, Date dateRecup, Date dateretour ) {
        int nbPieceLot = getNbPieceLot(connection, marqueMatLot, anneeAchatLot, modeleMatLot);
        System.out.println("Le nombre de pieces dans le lot que vous souhaitez réserver est : " + nbPieceLot);
        if(nbPieceLot >= 0) {
            int nbPieceLotRes = getNbPieceRes(connection, dateRecup, dateretour);
            System.out.println("Le nombre de pieces déjà réservées dans le lot est : " + nbPieceLot);
            System.out.println("Le nombre de pieces que vous voulez réserver est : " + nbPieceResUser);
            if(nbPieceLotRes >= 0) {
                int nbPieceDispo = nbPieceLot - nbPieceLotRes;
                if( nbPieceDispo >= nbPieceResUser) {
                    // Le nombre de pièces disponibles dans le lot est suffisant
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Ajoute une nouvelle location à la table location
     */
    private void insertNewLocation(Connection conn, int userId, String MarqueMatLot, String  ModeleMatLot, int AnneeAchatLot, int NbPiecesRes, Date DateRecup, Date DateRetour) {
        String sqlReqToAddNewLocation = "INSERT INTO Locations (IdUtilisateur, MarqueMatLot, ModeleMatLot, AnneeAchatLot, NbPiecesRes, DateRecup, DateRetour, NbPiecesEnMoins) VALUES (?,?,?,?,?,?,?,?)";
        try {
            // Creation de la requete
            PreparedStatement stmt = conn.prepareStatement(sqlReqToAddNewLocation);
            stmt.setInt(1, userId);
            stmt.setString(2, MarqueMatLot);
            stmt.setString(3, ModeleMatLot);
            stmt.setInt(4, AnneeAchatLot);
            stmt.setInt(5, NbPiecesRes);
            stmt.setDate(6, DateRecup);
            stmt.setDate(7, DateRetour);
            stmt.setInt(8, 0);

            int locationsRowsCount = stmt.executeUpdate();
            if (locationsRowsCount > 0) {
                // Terminaison de la transaction
                conn.commit();
                System.out.println("Votre réservation a bien été prise en compte.");
            } else {
                System.out.println("Une erreur s'est produite lors de la réservation de matériel");
            }
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }
    
    public ReservationMateriel(Scanner scanner, Connection connection, int userId) {
        System.out.println("");
        System.out.println("Veuillez identifier le lot de matériel que vous souhaitez louer :");
        System.out.println("");
  
        //  Marque du lot
        System.out.print("Marque du lot : ");
        String marqueMatLot = scanner.nextLine();

        //  Modèle du lot
        System.out.print("modèle du lot : ");
        String modeleMatLot = scanner.nextLine();

        //  Annèe achat du lot
        System.out.print("Année d'achat du lot : ");
        int anneeAchatLot = scanner.nextInt();

        // Nombre de pièces à louer
        System.out.print("Nombre de pièce à emprunter :");
        int nbPieceResUser = scanner.nextInt();

        System.out.println("");
        System.out.println("Quelle est votre période de réservation ?");

        // Date de récupération
        System.out.println("");
        System.out.print("Date de récupération (format YYYY-MM-DD):");
        String dateRecupString = scanner.next();

        Date dateRecup = convertStringToSqlDate(dateRecupString);

        // Date de retour
        System.out.println("");
        System.out.print("Date de retour (format YYYY-MM-DD):");
        String dateRetourString = scanner.next();

        Date dateretour = convertStringToSqlDate(dateRetourString);

        if(checkIsAnAvailableLoc(scanner, connection, marqueMatLot, modeleMatLot, anneeAchatLot, nbPieceResUser, dateRecup, dateretour)) {
            System.out.println("");
            System.out.println("Votre réservation peut se faire, voulez-vous la confirmer ?");
            System.out.println("1. Oui ");
            System.out.println("2. Non");
            System.out.print("Choisissez une option (1-2):");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    insertNewLocation(connection, userId, marqueMatLot, modeleMatLot, anneeAchatLot, nbPieceResUser, dateRecup, dateretour);
                    break;
                case 2:
                    System.out.println("Réservation annulée");
                    break;
                default:
                    System.out.println("Choix incorrect !");
                    break;
                }
        } else {
            System.out.println("La réservation que vous souhaitez réaliser n'est pas possible");
        }


    }
}
