package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitaire pour gérer la connexion JDBC à la base de données MySQL.
 */
public class ConnexionBD {
    /** URL de la base de données (JDBC) */
    private static final String URL = "jdbc:mysql://localhost:8889/rendezvous_db";
    /** Nom d’utilisateur pour la connexion à la BDD */
    private static final String UTILISATEUR = "root";
    /** Mot de passe pour la connexion à la BDD */
    private static final String MOT_DE_PASSE = "root";

    /**
     * Ouvre et renvoie une connexion JDBC vers la base de données.
     *
     * @return une {@link Connection} active
     * @throws SQLException si la connexion échoue
     */
    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}