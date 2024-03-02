package sql.Requetes;
import java.sql.*;

public class Query1 {
    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    static final String USER = "chevretr";
    static final String PASSWD = "ratio**";
    static final String PRE_STMT = "select distinct NomFor, DateDebutFor, DureeFor, (NbplaceMaxFor - (select count(*) from reserveformation WHERE Formations.AnneeFor = reserveformation.AnneeFor AND formations.NumeroFor = reserveformation.NumeroFor)) AS NbPlacesRestantes , Typeactivite from formations JOIN FormationPossedeTypeActivite ON Formations.AnneeFor = FormationPossedeTypeActivite.AnneeFor AND Formations.NumeroFor = FormationPossedeTypeActivite.NumeroFor ORDER BY DateDebutFor, NomFor";

    public Query1() {
        try {
	    // Enregistrement du driver Oracle
	    System.out.print("Loading Oracle driver... "); 
	    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("loaded");

	    // Etablissement de la connections
	    System.out.print("Connecting to the database... "); 
	    Connection conn = DriverManager.getConnection(CONN_URL, USER, PASSWD);
            System.out.println("connected");

	    // Creation de la requete
            PreparedStatement stmt = conn.prepareStatement(PRE_STMT);
	    // Execution de la requete
            ResultSet rset = stmt.executeQuery();
	    // Affichage du resultat
            System.out.println("Results:");
            dumpResultSet(rset);
            System.out.println("");

	    // Fermeture 
	    rset.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    private void dumpResultSet(ResultSet rset) throws SQLException {
        ResultSetMetaData rsetmd = rset.getMetaData();
        int i = rsetmd.getColumnCount();
        while (rset.next()) {
            for (int j = 1; j <= i; j++) {
                System.out.print(rset.getString(j) + "\t");
	    }
	    System.out.println();
        }
}
}
