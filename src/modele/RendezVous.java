package modele;

import java.time.LocalDateTime;

/**
 * Représente un rendez-vous entre un patient et un spécialiste.
 * Contient l’identifiant, les références patient/spécialiste, la date & heure,
 * le motif et la note attribuée.
 */
public class RendezVous {
    private int id;                     // Identifiant unique du rendez-vous
    private int idPatient;              // Identifiant du patient
    private int idSpecialiste;          // Identifiant du spécialiste
    private LocalDateTime dateHeure;    // Date et heure du rendez-vous
    private String motif;               // Motif de la consultation
    private int note;                   // Note donnée par le patient

    /**
     * Retourne l’identifiant du rendez-vous.
     * @return id du rendez-vous
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l’identifiant du rendez-vous.
     * @param id nouvel identifiant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne l’identifiant du patient concerné.
     * @return id du patient
     */
    public int getIdPatient() {
        return idPatient;
    }

    /**
     * Définit l’identifiant du patient concerné.
     * @param idPatient nouvel id du patient
     */
    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    /**
     * Retourne l’identifiant du spécialiste concerné.
     * @return id du spécialiste
     */
    public int getIdSpecialiste() {
        return idSpecialiste;
    }

    /**
     * Définit l’identifiant du spécialiste concerné.
     * @param idSpecialiste nouvel id du spécialiste
     */
    public void setIdSpecialiste(int idSpecialiste) {
        this.idSpecialiste = idSpecialiste;
    }

    /**
     * Retourne la date et l’heure du rendez-vous.
     * @return dateHeure du rendez-vous
     */
    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    /**
     * Définit la date et l’heure du rendez-vous.
     * @param dateHeure nouvelle date et heure
     */
    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    /**
     * Retourne le motif de la consultation.
     * @return motif du rendez-vous
     */
    public String getMotif() {
        return motif;
    }

    /**
     * Définit le motif de la consultation.
     * @param motif nouveau motif
     */
    public void setMotif(String motif) {
        this.motif = motif;
    }

    /**
     * Retourne la note attribuée par le patient.
     * @return note (1–5) ou 0 si non noté
     */
    public int getNote() {
        return note;
    }

    /**
     * Définit la note attribuée par le patient.
     * @param note valeur de la note (1–5)
     */
    public void setNote(int note) {
        this.note = note;
    }

    /**
     * Retourne une représentation textuelle du rendez-vous.
     * Utile pour le débogage.
     * @return chaîne décrivant tous les attributs
     */
    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", patient=" + idPatient +
                ", specialiste=" + idSpecialiste +
                ", dateHeure=" + dateHeure +
                ", motif='" + motif + '\'' +
                ", note=" + note +
                '}';
    }
}