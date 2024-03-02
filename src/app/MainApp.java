package app;
import java.util.Scanner;

import sql.Requetes.Authentification;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        System.out.println("");
        System.out.println("Bienvenue dans notre Démonstrateur\n");
        // On continue à proposer des services tant que l'utilisateur ne quitte pas l'application
        while (!exit) {
            System.out.println("Voici les options disponibles :");
            System.out.println("1. S'identifier pour accéder aux différents services");
            System.out.println("2. Créer un compte");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option (1-3): ");
    
            Object choice = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne restante après nextInt()
            if (!(choice instanceof Integer)) {
                System.out.println("Erreur de saisie. Veuillez choisir une option valide");
            } else {
                switch ((int) choice) {
                case 1:
                    new Authentification(false, scanner);
                    break;
                case 2:
                    new Authentification(true, scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix incorrect. Veuillez choisir une option valide.");
                }
            }
        }
        System.out.println("Merci d'avoir utilisé le Démonstrateur\n");
        scanner.close();
    }
}
