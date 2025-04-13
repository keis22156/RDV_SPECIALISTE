package dao;

import modele.RendezVous;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RendezVousDAO {

    public static boolean reserverCreneau(LocalDateTime dateHeure, int idPatient, int idSpecialiste, String motif) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String insert = "INSERT INTO rendezvous (id_patient, id_specialiste, dateheure, note, motif) VALUES (?, ?, ?, 0, ?)";
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setInt(1, idPatient);
            stmt.setInt(2, idSpecialiste);
            stmt.setTimestamp(3, Timestamp.valueOf(dateHeure));
            stmt.setString(4, motif);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<RendezVous> getRendezVousParPatient(int idPatient) {
        ArrayList<RendezVous> liste = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM rendezvous WHERE id_patient = ? ORDER BY dateheure DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPatient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RendezVous r = new RendezVous();
                r.setId(rs.getInt("id"));
                r.setIdPatient(rs.getInt("id_patient"));
                r.setIdSpecialiste(rs.getInt("id_specialiste"));
                Timestamp ts = rs.getTimestamp("dateheure");
                if (ts != null) {
                    r.setDateHeure(ts.toLocalDateTime());
                } else {
                    System.err.println("⚠️ Champ 'dateheure' nul pour le RDV id=" + rs.getInt("id"));
                }
                r.setNote(rs.getInt("note"));
                r.setMotif(rs.getString("motif"));
                liste.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static RendezVous getProchainRendezVous(int idPatient) {
        RendezVous prochain = null;
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM rendezvous WHERE id_patient = ? AND dateheure > NOW() ORDER BY dateheure ASC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPatient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RendezVous r = new RendezVous();
                r.setId(rs.getInt("id"));
                r.setIdPatient(rs.getInt("id_patient"));
                r.setIdSpecialiste(rs.getInt("id_specialiste"));
                r.setDateHeure(rs.getTimestamp("dateheure").toLocalDateTime());
                r.setNote(rs.getInt("note"));
                r.setMotif(rs.getString("motif"));
                prochain = r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prochain;
    }

    public static void noterRendezVous(int idPatient, String date, int idSpec, int note) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String requete = "UPDATE rendezvous SET note = ? WHERE id_patient = ? AND dateheure = ? AND id_specialiste = ?";
            PreparedStatement stmt = conn.prepareStatement(requete);
            stmt.setInt(1, note);
            stmt.setInt(2, idPatient);
            stmt.setString(3, date);
            stmt.setInt(4, idSpec);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RendezVous> getHistoriqueParSpecialiste(int idSpecialiste) {
        ArrayList<RendezVous> liste = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM rendezvous WHERE id_specialiste = ? AND dateheure IS NOT NULL ORDER BY dateheure DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("dateheure");
                if (ts != null) {
                    RendezVous r = new RendezVous();
                    r.setId(rs.getInt("id"));
                    r.setIdPatient(rs.getInt("id_patient"));
                    r.setIdSpecialiste(rs.getInt("id_specialiste"));
                    r.setDateHeure(ts.toLocalDateTime());
                    r.setNote(rs.getInt("note"));
                    r.setMotif(rs.getString("motif"));
                    liste.add(r);
                } else {
                    System.err.println("⚠️ RDV ignoré (pas de dateheure) → ID: " + rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static ArrayList<RendezVous> getRendezVousParSpecialiste(int idSpecialiste) {
        ArrayList<RendezVous> liste = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM rendezvous WHERE id_specialiste = ? AND dateheure >= NOW() ORDER BY dateheure ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RendezVous r = new RendezVous();
                r.setId(rs.getInt("id"));
                r.setIdPatient(rs.getInt("id_patient"));
                r.setIdSpecialiste(rs.getInt("id_specialiste"));
                r.setDateHeure(rs.getTimestamp("dateheure").toLocalDateTime());
                r.setNote(rs.getInt("note"));
                r.setMotif(rs.getString("motif"));
                liste.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static RendezVous getRendezVousParDateEtSpecialiste(LocalDateTime date, int idSpecialiste) {
        RendezVous rdv = null;
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "SELECT * FROM rendezvous WHERE dateheure = ? AND id_specialiste = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(date));
            stmt.setInt(2, idSpecialiste);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rdv = new RendezVous();
                rdv.setId(rs.getInt("id"));
                rdv.setIdPatient(rs.getInt("id_patient"));
                rdv.setIdSpecialiste(rs.getInt("id_specialiste"));
                rdv.setDateHeure(rs.getTimestamp("dateheure").toLocalDateTime());
                rdv.setNote(rs.getInt("note"));
                rdv.setMotif(rs.getString("motif"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rdv;
    }
    public static void supprimerRendezVous(int idRdv) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            String sql = "DELETE FROM rendezvous WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idRdv);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
