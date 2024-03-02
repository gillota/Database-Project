package app;

import java.sql.Connection;
import java.util.Scanner;

import sql.Requetes.ReservationFormation;
import sql.Requetes.ReservationRefuge;
import sql.Requetes.Adherent;
import sql.Requetes.SuppressionCompte;

public class HomeApp {

    private int userId;
    private Connection conn;                                                                                                                     
    private boolean isAnAdherent = false;

    public HomeApp(Connection dataBaseConn,Scanner scanner, int userId) {
        boolean exit = false;

        this.conn = dataBaseConn;
        this.userId = userId;
        
        this.isAnAdherent = Adherent.checkIsAlreadyAnAdherent(dataBaseConn, userId);

        // On continue à proposer des services tant que l'utilisateur ne quitte pas l'application
        while (!exit) {
            System.out.println("");
            System.out.println("Bienvenue dans le catalogue des services");
            System.out.println("1. Parcours du catalogue");
            System.out.println("2. Réserver une formation");
            System.out.println("3. Réserver un refuge");
            System.out.println("4. Louer du matériel");
            System.out.println("5. Supprimer mon compte");
            System.out.println("6. Souscrire à une adhésion");
            System.out.println("7. Déconnexion");
            System.out.print("Choisissez une option (1-6):");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne restante après nextInt()

            switch (choice) {
                case 1:
                    new CatalogueApp(conn, scanner);
                    break;
                case 2:
                    new ReservationFormation(conn, scanner, userId);
                    break;
                case 3:
                    new ReservationRefuge(conn, scanner, userId);
                    break;
                case 4:
                    if (isAnAdherent) {
                        new ReservationMaterielApp(scanner, dataBaseConn, this.userId);
                    } else {
                        System.out.println("");
                        System.out.println("Attention pour louer du matériel vous dvez être un adhérent.");
                        System.out.println("Pour souscrire à une adhésion taper l'option 6");
                    }
                    break;
                case 5:
                    
                    SuppressionCompte stateAccount = new SuppressionCompte(scanner, this.conn, this.userId);
                    // Si le compte est supprimé on sort de l'application
                    if (stateAccount.getAccountDeleted()) {
                        exit = true;
                    }
                    break;
                case 6:
                    if (isAnAdherent) {
                        System.out.println("");
                        System.out.println("Vous n'avez pas besoin de ce service car vous possédez déjà une adhésion chez nous.");
                        System.out.println("Veuillez choisir une autre option");
                    } else {
                        // On propose le service pour souscrire à une adhésion
                        new Adherent(scanner, dataBaseConn, userId);
                        this.isAnAdherent = Adherent.checkIsAlreadyAnAdherent(dataBaseConn, userId);
                    }
                    break;                
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix incorrect. Veuillez choisir une option valide.");
            }
        }

        System.out.println("Vous avez été déconnecté.");
        System.out.println("");
    }
}
