package controleur;

import dao.UtilisateurDAO;
import modele.Utilisateur;

/**
 * Contrôleur pour gérer la connexion des utilisateurs.
 * Délègue la vérification des identifiants au DAO.
 */
public class ControleurConnexion {

    /**
     * Vérifie les identifiants d’un utilisateur.
     *
     * @param email      adresse email fournie par l’utilisateur
     * @param motDePasse mot de passe fourni par l’utilisateur
     * @return l’objet {@link Utilisateur} correspondant si la connexion est validée, sinon null
     */
    public static Utilisateur verifierConnexion(String email, String motDePasse) {
        return UtilisateurDAO.verifierConnexion(email, motDePasse);
    }
}