package app;

import java.sql.Connection;
import java.util.Scanner;

import sql.Requetes.AffichageFormation;
import sql.Requetes.AffichageMateriel;
import sql.Requetes.AfficheRefuge;

public class CatalogueApp {

    public CatalogueApp(Connection conn, Scanner scann) {
        boolean exit = false;

        // Parcours des services proposées 
        while (!exit) {
            System.out.println("");
            System.out.println("Catalogue des formations/refuges/matériels disponibles");
            System.out.println("1. Menu des Formations");
            System.out.println("2. Menu des lots de matériel");
            System.out.println("3. Afficher les refuges");
            System.out.println("4. Fermer le catalogue");
            System.out.print("Choisissez une option (1-4):");

            int choice = scann.nextInt();
            scann.nextLine();

            switch (choice) {
                case 1:
                    new AffichageFormation(scann, conn);
                    break;
                case 2:
                    new AffichageMateriel(scann, conn);
                    break;
                case 3:
                    new AfficheRefuge(scann, conn);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
