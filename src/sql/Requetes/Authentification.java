package sql.Requetes;

import java.sql.*;
import java.util.Scanner;

import app.HomeApp;

public class Authentification {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "guibertk";
    static final String PASSWD = "guibertk";

    private Connection conn;

    public int userId;
    private boolean isLoggedIn = false ; 

    // Initialisation de la connexion à la base de données
    private void setDatabaseConnection() {
        try {
            // Enregistrement du driver Oracle
            System.out.print("Loading Oracle driver... "); 
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("loaded");

            // Etablissement de la connection
            System.out.print("Connecting to the database... "); 
            this.conn = DriverManager.getConnection(CONN_URL, USER, PASSWD);
            System.out.println("connected");

            // Demarrage de la transaction
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    public boolean checkUserCredentials(String email, String passwd) {
        String sqlInsertNewUser = "SELECT IDUTILISATEUR, EMAILMEM, MDPMEM FROM MEMBRE WHERE EMAILMEM = ? AND MDPMEM = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sqlInsertNewUser);

            // On ajoute les paramètres
            stmt.setString(1, email);
            stmt.setString(2, passwd);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // L'utilisateur existe
                int idUserKey = rs.getInt(1);
                this.userId = idUserKey;
                System.out.println("Vous avez été identifié avec succès !");
                System.out.println("Voici votre id : " + idUserKey + "\n");
                return true;
            } else {
                System.out.println("Mot de passe ou nom d'utilisateur invalide.");
            }
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
        return false;
    }

    public int insertNewUser() {
        try{
            String sqlInsertNewUser = "INSERT INTO UTILISATEURS (CoutResrefuge,CoutResFormation,SommeDue,SommeRemboursee) VALUES (?, ?, ?, ?)";
  
            // Creation de la requete
            String key[] = {"IDUTILISATEUR"};
            PreparedStatement stmt = conn.prepareStatement(sqlInsertNewUser, key);

            // On ajoute les données par défauts
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setInt(3, 0);
            stmt.setInt(4, 0);
            
            // Nombre de colonnes modifiés après avoir ajouter les données
            int rowCount = stmt.executeUpdate();
            if (rowCount > 0) {
                ResultSet generatedIdUserkeys = stmt.getGeneratedKeys();
                if (generatedIdUserkeys.next()) {
                    int IdUserKey = generatedIdUserkeys.getInt(1);
                    this.userId = IdUserKey;
                    return IdUserKey;
                }
            }

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
        // To indicate an error
        return -1;
      }

    public boolean insertNewMember(String email, String passwd, String name, String firstName, String adress) {

        String sqlReqToCreateNewMember = "INSERT INTO Membre (idUtilisateur, EmailMem, MdpMem, NomMem, PrenomMem, AdrMem) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            int idUserCreated = insertNewUser();

            // Creation de la requete
            PreparedStatement stmt = conn.prepareStatement(sqlReqToCreateNewMember);
            stmt.setInt(1, idUserCreated);
            stmt.setString(2, email);
            stmt.setString(3, passwd);
            stmt.setString(4, name);
            stmt.setString(5, firstName);
            stmt.setString(6, adress);

            int memberRowsCount = stmt.executeUpdate();
            
            if (memberRowsCount > 0) {
                // On termine la transaction
                conn.commit();
                System.out.println("Votre compte a été créé avec succès.");
                return true;
            } else {
                System.out.println("Erreur lors de l'ajout de votre compte");
            }
        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
        return false;
    }
    
    public Authentification(boolean isCreatingNewAccount, Scanner scanner) {
        // Lecture de l'entrée de l'utilisateur
        System.out.println("");

        boolean exit = false;

        // Tant que l'utilisateur ne demande pas d'arreter le processus d'authentification on continue
        while (!exit) {
            if (isCreatingNewAccount == true) {
                System.out.println("Création de votre compte utilisateur :");
        
                //  l'identifiant de l'utilisateur
                System.out.print("Email : ");
                String userEmail = scanner.nextLine();
        
                //  Mot de passe de l'utilisateur
                System.out.print("Mot de passe : ");
                String userMdp = scanner.nextLine();
        
                //  Nom de l'utilisateur
                System.out.print("Nom : ");
                String userName = scanner.nextLine();
        
                //  Prénom de l'utilisateur
                System.out.print("Prénom : ");
                String userFirstName = scanner.nextLine();
        
                //  Adresse de l'utilisateur
                System.out.print("Adresse : ");
                String userAdr = scanner.nextLine();

                this.setDatabaseConnection();
                this.isLoggedIn = this.insertNewMember(userEmail, userMdp, userName, userFirstName, userAdr);
            } else {
                // Pour lire l'identifiant de l'utilisateur
                System.out.println("Veuillez saisir vos identifiants :");
                System.out.print("Email :");
                String userEmail = scanner.nextLine();

                // Lire le mot de passe de l'utilisateur
                System.out.print("Mot de passe :");
                String userMdp = scanner.nextLine();

                this.setDatabaseConnection();
                this.isLoggedIn = this.checkUserCredentials(userEmail, userMdp);
            }

            if (isLoggedIn) {
                new HomeApp(this.conn, scanner, userId);
                exit = true;
            } else {
                System.out.println("");
                System.out.println("Voulez voulez essayer de vous reconnecter ?");
                System.out.println("1. Réessayer");
                System.out.println("2. Quitter l'application");
                System.out.print("Choisissez une option (1-2):");


                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        try {
                            // Fermeture de la connection
                            conn.close();
                        } catch (SQLException e) {
                        System.err.println("failed");
                        e.printStackTrace(System.err);
                        }
                        exit = true;
                        break;
                    default:                     
                        break;
                    }
            }
        }
    }
}
