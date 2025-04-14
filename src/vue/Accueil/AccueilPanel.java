package vue.Accueil;

import vue.Style.StyleGraphique;
import javax.swing.*;
import java.awt.*;

/**
 * Panneau principal de l’application affiché à l’ouverture.
 * <p>
 * Contient le header commun, un message de bienvenue, deux boutons
 * pour accéder à la page de connexion ou d’inscription, et un pied de page
 * avec les crédits.
 * </p>
 */
public class AccueilPanel extends JPanel {

    /**
     * Construit le panneau d’accueil de l’application.
     *
     * @param fenetre la fenêtre Swing dans laquelle le panneau est affiché
     */
    public AccueilPanel(JFrame fenetre) {
        // layout principal et fond gris clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // === Header commun ===
        add(new HeaderAccueil(), BorderLayout.NORTH);

        // === Zone centre : message + boutons ===
        JPanel centre = new JPanel();
        centre.setBackground(Color.decode("#f5f7fb"));
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        // message de bienvenue
        JLabel message = new JLabel("Bienvenue sur votre espace de prise de rendez-vous en ligne");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setFont(new Font("Arial", Font.BOLD, 20));
        message.setBorder(BorderFactory.createEmptyBorder(50, 10, 30, 10));
        message.setForeground(Color.DARK_GRAY);

        // panel boutons
        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        boutonsPanel.setBackground(Color.decode("#f5f7fb"));

        JButton boutonConnexion   = new JButton("Connexion");
        JButton boutonInscription = new JButton("Inscription");

        // style des boutons (primaire / secondaire)
        StyleGraphique.styliserBoutonPrincipal(boutonConnexion);
        StyleGraphique.styliserBoutonSecondaire(boutonInscription);

        boutonsPanel.add(boutonConnexion);
        boutonsPanel.add(boutonInscription);

        // on ajoute au centre
        centre.add(message);
        centre.add(boutonsPanel);

        add(centre, BorderLayout.CENTER);

        // === bas de page : crédits ===
        JPanel bas = new JPanel(new BorderLayout());
        bas.setBackground(Color.decode("#f5f7fb"));
        JLabel credits = new JLabel("2025 Keis Aissaoui, Nicolas Chaudemanche");
        credits.setFont(new Font("Arial", Font.ITALIC, 12));
        credits.setForeground(Color.GRAY);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        bas.add(credits, BorderLayout.EAST);

        add(bas, BorderLayout.SOUTH);

        // === actions des boutons ===

        /**
         * Affiche le panneau de connexion dans la fenêtre principale.
         */
        boutonConnexion.addActionListener(e -> {
            fenetre.setContentPane(new ConnexionPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });

        /**
         * Affiche le panneau d’inscription dans la fenêtre principale.
         */
        boutonInscription.addActionListener(e -> {
            fenetre.setContentPane(new InscriptionPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });
    }
}