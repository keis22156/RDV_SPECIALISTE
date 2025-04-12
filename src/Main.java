import com.formdev.flatlaf.FlatLightLaf;
import vue.Accueil.AccueilPanel;

import javax.swing.*;

// Swing pour l’UI

/**
 * Point d’entrée de l’application de prise de rendez-vous spécialiste.
 *
 * <p>Cette classe configure le look, crée la fenêtre principale
 * et affiche directement le panneau d’accueil.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 *
 * @param args arguments de la ligne de commande (non utilisés)
 * @throws UnsupportedLookAndFeelException si le thème FlatLaf ne peut être appliqué
 */
public static void main(String[] args) throws UnsupportedLookAndFeelException {
    // 1. Applique le thème FlatLight pour de meilleures graphismes
    UIManager.setLookAndFeel(new FlatLightLaf());

    // 2. Création de la fenêtre principale
    JFrame fenetre = new JFrame("Application de rendez-vous spécialiste");

    // 3. Configuration basique de la fenêtre
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // ferme l’appli quand on clique sur la croix
    fenetre.setSize(1000, 700);                               // taille initiale : 1000×700 px
    fenetre.setLocationRelativeTo(null);                      // centre la fenêtre à l’écran

    // 4. Ajout du panneau d’accueil comme contenu principal
    fenetre.setContentPane(new AccueilPanel(fenetre));

    // 5. Rend la fenêtre visible
    fenetre.setVisible(true);
}