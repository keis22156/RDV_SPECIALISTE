package dao;

import modele.Role;
import modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public static Utilisateur verifierConnexion(String email, String motDePasse) {
        Utilisateur utilisateur = null;
        try (Connection conn = ConnexionBD.getConnexion()) {
            String requete = "SELECT * FROM utilisateurs WHERE email = ? AND motdepasse = ?";
            PreparedStatement stmt = conn.prepareStatement(requete);
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("role"),
                        rs.getString("photo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    public static boolean creerPatient(String nom, String email, String motDePasse) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String requete = "INSERT INTO utilisateurs (nom, email, motdepasse, role, photo) VALUES (?, ?, ?, 'PATIENT', '')";
            PreparedStatement stmt = conn.prepareStatement(requete);
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void mettreAJourPhoto(int id, String cheminPhoto) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String requete = "UPDATE utilisateurs SET photo = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(requete);
            stmt.setString(1, cheminPhoto);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mettreAJourMotDePasse(int id, String nouveauMotDePasse) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "UPDATE utilisateurs SET motdepasse = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nouveauMotDePasse);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Utilisateur> getUtilisateursParRole(String roleRecherche) {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnexion()) {
            String requete = "SELECT * FROM utilisateurs WHERE role = ?";
            PreparedStatement stmt = conn.prepareStatement(requete);
            stmt.setString(1, roleRecherche.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("role"),
                        rs.getString("photo")
                );
                liste.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static Utilisateur getUtilisateurParId(int id) {
        Utilisateur utilisateur = null;
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM utilisateurs WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        rs.getString("role"),
                        rs.getString("photo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    public static List<Utilisateur> getTousLesSpecialistes() {
        List<Utilisateur> liste = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM utilisateurs WHERE role = 'SPECIALISTE'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        "SPECIALISTE",
                        rs.getString("photo")
                );
                liste.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
    public static void mettreAJourInfos(Utilisateur u) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setInt(3, u.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean creerSpecialiste(String nom, String email, String motDePasse) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "INSERT INTO utilisateurs (nom, email, motdepasse, role, photo) VALUES (?, ?, ?, 'SPECIALISTE', '')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
