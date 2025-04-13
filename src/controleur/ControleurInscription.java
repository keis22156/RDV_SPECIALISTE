package controleur;

import dao.UtilisateurDAO;

public class ControleurInscription {
    public static boolean inscrirePatient(String nom, String email, String motDePasse) {
        return UtilisateurDAO.creerPatient(nom, email, motDePasse);
    }
}
