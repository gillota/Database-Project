package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

public class SuppressionCompte {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "guibertk";
    static final String PASSWD = "guibertk";

    private boolean accountDeleted = false;

    public boolean getAccountDeleted() {
        return this.accountDeleted;
    }

    public void deleteMember(int userId, Connection connection) {

        String sqlDeleteMember = "DELETE FROM Membre where IdUtilisateur = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlDeleteMember);

            stmt.setInt(1, userId);

            int rowsModified = stmt.executeUpdate();
            if (rowsModified > 0) {
                // L'utilisateur a été supprimé

                // Terminaison de la transaction
                connection.commit();

                this.accountDeleted = true;
                System.out.println("Vous a compte bien été supprimé !");
            } else {
                System.out.println("Erreur. Aucun membre possédant l'Idutilisateur " + userId + " a été trouvé." );
            }
        } catch (SQLException e) {
            System.err.println("Delete User account failed");
            e.printStackTrace(System.err);
        }
    }

    public SuppressionCompte(Scanner scann, Connection connection, int userId) {
        System.out.println("");
        System.out.println("Attention !!! Votre compte va être supprimé");
        System.out.println("Êtes-vous sûr de vouloir supprimer votre compte ?");
        System.out.println("1. Oui ");
        System.out.println("2. Non");
        System.out.print("Choisissez une option (1-2):");

        int choice = scann.nextInt();
        scann.nextLine();

        switch (choice) {
            case 1:
                this.deleteMember(userId, connection);
                break;
            case 2:
                break;
            default: 
                System.out.println("Choix incorrect !");
                break;
            }

    }
}
