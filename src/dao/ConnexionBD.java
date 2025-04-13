package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost:8889/rendezvous_db";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "root";

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}
