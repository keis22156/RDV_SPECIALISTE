package dao;

import modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour les opérations et authentification liées aux utilisateurs.
 * Gère patients, spécialistes et administrateurs en base de données.
 */
public class UtilisateurDAO {

    /**
     * Vérifie les identifiants lors de la connexion.
     * @param email identifiant utilisateur
     * @param motDePasse mot de passe en clair
     * @return Utilisateur correspondant si authentification réussie, sinon null
     */
    public static Utilisateur verifierConnexion(String email, String motDePasse) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND motdepasse = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = mapperUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    /**
     * Crée un compte patient en base de données.
     * @param prenom prénom du patient
     * @param nom nom de famille
     * @param email adresse email unique
     * @param motDePasse mot de passe en clair
     * @param age âge du patient
     * @param poids poids en kg
     * @param numeroSecu numéro de sécurité sociale
     * @param sexe sexe du patient
     * @param adresse adresse postale
     * @param telephone numéro de téléphone
     * @return true si création réussie, false sinon
     */
    public static boolean creerPatient(String prenom, String nom, String email, String motDePasse,
                                       Integer age, Double poids, String numeroSecu,
                                       String sexe, String adresse, String telephone) {
        String sql = "INSERT INTO utilisateurs "
                + "(prenom, nom, email, motdepasse, role, age, poids, numero_secu, sexe, adresse, telephone) "
                + "VALUES (?, ?, ?, ?, 'PATIENT', ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prenom);
            stmt.setString(2, nom);
            stmt.setString(3, email);
            stmt.setString(4, motDePasse);
            stmt.setInt(5, age);
            stmt.setDouble(6, poids);
            stmt.setString(7, numeroSecu);
            stmt.setString(8, sexe);
            stmt.setString(9, adresse);
            stmt.setString(10, telephone);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crée un compte spécialiste en base de données.
     * @param prenom prénom du spécialiste
     * @param nom nom de famille
     * @param email adresse email unique
     * @param motDePasse mot de passe en clair
     * @param specialite spécialité médicale
     * @param description description ou bio
     * @param telephone numéro de téléphone
     * @param adresse adresse postale
     * @return true si création réussie, false sinon
     */
    public static boolean creerSpecialiste(String prenom, String nom, String email, String motDePasse,
                                           String specialite, String description, String telephone, String adresse) {
        String sql = "INSERT INTO utilisateurs "
                + "(prenom, nom, email, motdepasse, role, specialite, description, telephone, adresse) "
                + "VALUES (?, ?, ?, ?, 'SPECIALISTE', ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prenom);
            stmt.setString(2, nom);
            stmt.setString(3, email);
            stmt.setString(4, motDePasse);
            stmt.setString(5, specialite);
            stmt.setString(6, description);
            stmt.setString(7, telephone);
            stmt.setString(8, adresse);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour le chemin de la photo de profil d’un utilisateur.
     * @param id identifiant de l’utilisateur
     * @param cheminPhoto chemin du fichier image
     */
    public static void mettreAJourPhoto(int id, String cheminPhoto) {
        String sql = "UPDATE utilisateurs SET photo = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cheminPhoto);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour le mot de passe d’un utilisateur.
     * @param id identifiant de l’utilisateur
     * @param nouveauMotDePasse mot de passe en clair
     */
    public static void mettreAJourMotDePasse(int id, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateurs SET motdepasse = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouveauMotDePasse);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les utilisateurs d’un rôle donné.
     * @param roleRecherche rôle (<code>PATIENT</code>, <code>SPECIALISTE</code>, <code>ADMIN</code>)
     * @return liste d’utilisateurs correspondants
     */
    public static List<Utilisateur> getUtilisateursParRole(String roleRecherche) {
        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs WHERE role = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roleRecherche.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    liste.add(mapperUtilisateur(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * Récupère un utilisateur par son identifiant.
     * @param id identifiant unique
     * @return Utilisateur si trouvé, sinon null
     */
    public static Utilisateur getUtilisateurParId(int id) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = mapperUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    /**
     * Récupère la liste de tous les spécialistes.
     * @return liste de spécialistes
     */
    public static List<Utilisateur> getTousLesSpecialistes() {
        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs WHERE role = 'SPECIALISTE'";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                liste.add(mapperUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * Met à jour les informations générales d’un utilisateur (nom, email, etc.).
     * @param u objet Utilisateur avec nouvelles valeurs
     */
    public static void mettreAJourInfos(Utilisateur u) {
        String sql = "UPDATE utilisateurs SET "
                + "nom = ?, prenom = ?, email = ?, adresse = ?, age = ?, poids = ?, "
                + "numero_secu = ?, sexe = ?, specialite = ?, description = ?, telephone = ? "
                + "WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getPrenom());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getAdresse());
            if (u.getAge() != null)    stmt.setInt(5, u.getAge());
            else                        stmt.setNull(5, Types.INTEGER);
            if (u.getPoids() != null)  stmt.setDouble(6, u.getPoids());
            else                        stmt.setNull(6, Types.DOUBLE);
            stmt.setString(7, u.getNumeroSecu());
            stmt.setString(8, u.getSexe());
            stmt.setString(9, u.getSpecialite());
            stmt.setString(10, u.getDescription());
            stmt.setString(11, u.getTelephone());
            stmt.setInt(12, u.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mappe une ligne de ResultSet en objet Utilisateur.
     * @param rs résultat de la requête positionné sur une ligne valide
     * @return instance de Utilisateur avec tous les champs remplis
     * @throws SQLException en cas d’erreur de lecture
     */
    private static Utilisateur mapperUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur u = new Utilisateur(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("motdepasse"),
                rs.getString("role"),
                rs.getString("photo")
        );
        u.setAdresse(rs.getString("adresse"));
        u.setAge(rs.getObject("age") != null ? rs.getInt("age") : null);
        u.setPoids(rs.getObject("poids") != null ? rs.getDouble("poids") : null);
        u.setNumeroSecu(rs.getString("numero_secu"));
        u.setSexe(rs.getString("sexe"));
        u.setSpecialite(rs.getString("specialite"));
        u.setDescription(rs.getString("description"));
        u.setTelephone(rs.getString("telephone"));
        return u;
    }
}