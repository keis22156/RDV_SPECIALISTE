package modele;

/**
 * Enumération des rôles possibles pour un utilisateur de l’application.
 * <ul>
 *   <li>{@link #ADMIN} – Administrateur</li>
 *   <li>{@link #PATIENT} – Patient</li>
 *   <li>{@link #SPECIALISTE} – Spécialiste</li>
 * </ul>
 */
public enum Role {
    /** Administrateur, peut gérer utilisateurs et consulter tous les RDV. */
    ADMIN,
    /** Patient, peut prendre RDV, consulter son historique et modifier ses infos. */
    PATIENT,
    /** Spécialiste, peut gérer son agenda et consulter son historique de consultations. */
    SPECIALISTE
}