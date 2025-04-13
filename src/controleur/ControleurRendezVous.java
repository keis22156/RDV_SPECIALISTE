package controleur;

import dao.RendezVousDAO;
import modele.RendezVous;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControleurRendezVous {

    public static boolean reserverCreneau(LocalDateTime dateHeure, int idPatient, int idSpecialiste, String motif) {
        return RendezVousDAO.reserverCreneau(dateHeure, idPatient, idSpecialiste, motif);
    }

    public static ArrayList<RendezVous> getHistoriqueParPatient(int idPatient) {
        return RendezVousDAO.getRendezVousParPatient(idPatient);
    }

    public static RendezVous getProchainRendezVous(int idPatient) {
        return RendezVousDAO.getProchainRendezVous(idPatient);
    }

    public static void noterRendezVous(int idPatient, String date, int idSpec, int note) {
        RendezVousDAO.noterRendezVous(idPatient, date, idSpec, note);
    }

    public static ArrayList<RendezVous> getHistoriqueParSpecialiste(int idSpecialiste) {
        return RendezVousDAO.getHistoriqueParSpecialiste(idSpecialiste);
    }

    public static ArrayList<RendezVous> getRendezVousParSpecialiste(int idSpecialiste) {
        return RendezVousDAO.getRendezVousParSpecialiste(idSpecialiste);
    }

    public static RendezVous getRendezVousParDateEtSpecialiste(LocalDateTime date, int idSpecialiste) {
        return RendezVousDAO.getRendezVousParDateEtSpecialiste(date, idSpecialiste);
    }

    public static void annulerRendezVous(int idRdv) {
        RendezVousDAO.supprimerRendezVous(idRdv);
    }
}
