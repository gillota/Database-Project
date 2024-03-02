package app;

import java.sql.Connection;
import java.util.Scanner;

import sql.Requetes.AffichageMateriel;
import sql.Requetes.ReservationMateriel;

public class ReservationMaterielApp {
    
    public ReservationMaterielApp(Scanner scann, Connection connection, int userId) {

    boolean exit = false;

    while (!exit) {
        System.out.println("");
        System.out.println("Vous vous apprétez à réserver du matériel :");
        System.out.println("1. Parcourir le catalogue du matériel disponible");
        System.out.println("2. Procéder à une réservation");
        System.out.println("3. Revenir en arrière");
        System.out.print("Choisissez une option (1-3):");

        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                new AffichageMateriel(scann, connection);
                break;
            case 2:
                new ReservationMateriel(scann, connection, userId);
                break;
            case 3:
                exit = true;
                break;
            default: 
                System.out.println("Choix incorrect !");
                break;
            }
        }
    }
}
