package vue.specialiste;

import modele.Utilisateur;
import javax.swing.*;
import java.awt.*;

/**
 * Affiche le footer avec la barre de navigation pour le spécialiste.
 *
 * <p>Permet de naviguer entre les sections Accueil, Agenda, Historique
 * et de se déconnecter.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class FooterSpecialiste extends JPanel {

    /**
     * Constructeur qui crée la barre de navigation.
     *
     * @param fenetre     la fenêtre principale de l'application
     * @param specialiste l'utilisateur spécialiste connecté
     * @param pageActive  la page courante (accueil, agenda, historique, deconnexion)
     */
    public FooterSpecialiste(JFrame fenetre, Utilisateur specialiste, String pageActive) {
        // layout centré et fond blanc
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)); // ligne du haut

        // création des boutons
        JButton btnAccueil     = new JButton("🏠Accueil");
        JButton btnAgenda      = new JButton("🗓Mon agenda");
        JButton btnHistorique  = new JButton("📜Historique");
        JButton btnDeco        = new JButton("🚪Déconnexion");

        // style des boutons, le bouton actif est coloré
        styliser(btnAccueil,    pageActive.equals("accueil"));
        styliser(btnAgenda,     pageActive.equals("agenda"));
        styliser(btnHistorique, pageActive.equals("historique"));
        styliser(btnDeco,       pageActive.equals("deconnexion"));

        // action accueil
        btnAccueil.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordSpecialiste(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action agenda
        btnAgenda.addActionListener(e -> {
            fenetre.setContentPane(new AgendaSpecialistePanel(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action historique
        btnHistorique.addActionListener(e -> {
            fenetre.setContentPane(new HistoriqueSpecialistePanel(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action déconnexion
        btnDeco.addActionListener(e -> {
            fenetre.setContentPane(new vue.Accueil.AccueilPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // ajout des boutons au panel
        add(btnAccueil);
        add(btnAgenda);
        add(btnHistorique);
        add(btnDeco);
    }

    /**
     * Applique le style à un bouton selon qu'il soit actif ou non.
     *
     * @param bouton le bouton à styliser
     * @param actif  {@code true} si le bouton correspond à la page active
     */
    private void styliser(JButton bouton, boolean actif) {
        bouton.setFocusPainted(false);                    // pas de contour focus
        bouton.setFont(new Font("Arial", Font.BOLD, 14)); // police et taille
        bouton.setPreferredSize(new Dimension(150, 35));  // taille fixe
        if (actif) {
            bouton.setBackground(new Color(0, 123, 255)); // fond bleu si actif
            bouton.setForeground(Color.WHITE);           // texte blanc
        } else {
            bouton.setBackground(Color.LIGHT_GRAY);      // fond gris si inactif
            bouton.setForeground(Color.DARK_GRAY);       // texte gris foncé
        }
    }
}