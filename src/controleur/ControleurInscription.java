package controleur;

import dao.UtilisateurDAO;

/**
 * Contrôleur pour gérer l’inscription de nouveaux patients.
 * Délègue la création en base à {@link dao.UtilisateurDAO}.
 */
public class ControleurInscription {

    /**
     * Inscrit un nouveau patient avec les informations fournies.
     *
     * @param prenom      prénom du patient
     * @param nom         nom de famille du patient
     * @param email       adresse email du patient
     * @param motDePasse  mot de passe choisi
     * @param age         âge du patient
     * @param poids       poids du patient en kilogrammes
     * @param numeroSecu  numéro de sécurité sociale
     * @param sexe        sexe du patient
     * @param adresse     adresse postale du patient
     * @param telephone   numéro de téléphone du patient
     * @return {@code true} si l’inscription en base s’est bien déroulée, sinon {@code false}
     */
    public static boolean inscrirePatient(
            String prenom,
            String nom,
            String email,
            String motDePasse,
            Integer age,
            Double poids,
            String numeroSecu,
            String sexe,
            String adresse,
            String telephone
    ) {
        return UtilisateurDAO.creerPatient(
                prenom,
                nom,
                email,
                motDePasse,
                age,
                poids,
                numeroSecu,
                sexe,
                adresse,
                telephone
        );
    }

}