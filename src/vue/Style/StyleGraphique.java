package vue.Style;

import javax.swing.*;
import java.awt.*;

/**
 * Centralise les styles graphiques de l’application.
 *
 * Permet d’appliquer un style uniforme aux boutons, labels et champs de texte.
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class StyleGraphique {

    /**
     * Applique le style du bouton principal.
     *
     * @param bouton le bouton à styliser
     */
    public static void styliserBoutonPrincipal(JButton bouton) {
        bouton.setBackground(new Color(0, 123, 255)); // fond bleu
        bouton.setForeground(Color.WHITE);             // texte blanc
        bouton.setFocusPainted(false);                 // pas de contour quand on clique
        bouton.setFont(new Font("Arial", Font.BOLD, 14));  // police grasse taille 14
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // marge interne
    }

    /**
     * Applique le style du bouton secondaire.
     *
     * @param bouton le bouton à styliser
     */
    public static void styliserBoutonSecondaire(JButton bouton) {
        bouton.setBackground(new Color(230, 230, 230)); // fond gris clair
        bouton.setForeground(Color.BLACK);             // texte noir
        bouton.setFocusPainted(false);                 // pas de contour quand on clique
        bouton.setFont(new Font("Arial", Font.PLAIN, 14)); // police normale taille 14
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // marge interne
    }

    /**
     * Applique le style au label d’un champ de formulaire.
     *
     * @param label le label à styliser
     */
    public static void styliserLabelChamp(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 16)); // texte taille 16
        label.setAlignmentX(Component.LEFT_ALIGNMENT);    // aligné à gauche
    }

    /**
     * Applique le style au champ de texte.
     *
     * @param champ le champ de texte à styliser
     */
    public static void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14)); // police normale taille 14
        champ.setMaximumSize(new Dimension(400, 30));     // taille max
    }
}
