package controleur;

import dao.RendezVousDAO;
import modele.RendezVous;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Contrôleur pour gérer les opérations relatives aux rendez-vous.
 * Délègue les actions à {@link RendezVousDAO}.
 */
public class ControleurRendezVous {

    /**
     * Réserve un créneau pour un patient auprès d’un spécialiste.
     *
     * @param dateHeure    date et heure du créneau à réserver
     * @param idPatient    identifiant du patient
     * @param idSpecialiste identifiant du spécialiste
     * @param motif        motif du rendez-vous
     * @return {@code true} si la réservation en base a réussi, sinon {@code false}
     */
    public static boolean reserverCreneau(LocalDateTime dateHeure, int idPatient, int idSpecialiste, String motif) {
        return RendezVousDAO.reserverCreneau(dateHeure, idPatient, idSpecialiste, motif);
    }

    /**
     * Récupère l’historique complet des rendez-vous d’un patient.
     *
     * @param idPatient identifiant du patient
     * @return liste des {@link RendezVous} passés et à venir pour ce patient
     */
    public static ArrayList<RendezVous> getHistoriqueParPatient(int idPatient) {
        return RendezVousDAO.getRendezVousParPatient(idPatient);
    }

    /**
     * Récupère tous les rendez-vous (passés) d’un spécialiste.
     *
     * @param idSpecialiste identifiant du spécialiste
     * @return liste des {@link RendezVous} déjà effectués par ce spécialiste
     */
    public static ArrayList<RendezVous> getHistoriqueParSpecialiste(int idSpecialiste) {
        return RendezVousDAO.getHistoriqueParSpecialiste(idSpecialiste);
    }

    /**
     * Récupère les rendez-vous à venir pour un spécialiste.
     *
     * @param idSpecialiste identifiant du spécialiste
     * @return liste des {@link RendezVous} futurs pour ce spécialiste
     */
    public static ArrayList<RendezVous> getRendezVousParSpecialiste(int idSpecialiste) {
        return RendezVousDAO.getRendezVousParSpecialiste(idSpecialiste);
    }

    /**
     * Annule un rendez-vous existant.
     *
     * @param idRdv identifiant du rendez-vous à annuler
     */
    public static void annulerRendezVous(int idRdv) {
        RendezVousDAO.supprimerRendezVous(idRdv);
    }
}