package controleur;

import dao.UtilisateurDAO;
import modele.Utilisateur;

public class ControleurConnexion {
    public static Utilisateur verifierConnexion(String email, String motDePasse) {
        return UtilisateurDAO.verifierConnexion(email, motDePasse);
    }
}
