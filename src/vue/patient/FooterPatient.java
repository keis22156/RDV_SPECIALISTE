package vue.patient;

import modele.Utilisateur;
import vue.Accueil.AccueilPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Affiche la barre de navigation en bas pour le patient.
 *
 * <p>Comprend les boutons Accueil, Prendre RDV, Historique et Déconnexion,
 * avec mise en forme du bouton actif.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class FooterPatient extends JPanel {

    /**
     * Constructeur qui initialise la barre de navigation pour le patient.
     *
     * @param fenetre    la fenêtre principale de l'application
     * @param patient    l'utilisateur patient connecté
     * @param pageActive identifiant de la page active ("dashboard", "rdv", "historique", "deconnexion")
     */
    public FooterPatient(JFrame fenetre, Utilisateur patient, String pageActive) {
        // layout centré et espace entre les boutons
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)); // ligne en haut

        // création des boutons
        JButton btnAccueil     = new JButton("🏠 Accueil");
        JButton btnRDV         = new JButton("📆 Prendre RDV");
        JButton btnHistorique  = new JButton("📜 Historique");
        JButton btnDeco        = new JButton("🚪 Déconnexion");

        // style selon la page active
        styliser(btnAccueil,    pageActive.equals("dashboard"));
        styliser(btnRDV,        pageActive.equals("rdv"));
        styliser(btnHistorique, pageActive.equals("historique"));
        styliser(btnDeco,       pageActive.equals("deconnexion"));

        // ajout des boutons
        add(btnAccueil);
        add(btnRDV);
        add(btnHistorique);
        add(btnDeco);

        // action bouton Accueil
        btnAccueil.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordPatient(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action bouton Prendre RDV
        btnRDV.addActionListener(e -> {
            fenetre.setContentPane(new PrendreRDVPanel(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action bouton Historique
        btnHistorique.addActionListener(e -> {
            fenetre.setContentPane(new HistoriquePatientPanel(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // action bouton Déconnexion
        btnDeco.addActionListener(e -> {
            fenetre.setContentPane(new AccueilPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });
    }

    /**
     * Applique le style de base à un bouton et change sa couleur s'il est actif.
     *
     * @param bouton le bouton à styliser
     * @param actif  true si le bouton correspond à la page active, false sinon
     */
    private void styliser(JButton bouton, boolean actif) {
        bouton.setFocusPainted(false);                    // pas de contour focus
        bouton.setFont(new Font("Arial", Font.BOLD, 14)); // police et taille
        bouton.setPreferredSize(new Dimension(150, 35));  // taille fixe
        if (actif) {
            bouton.setBackground(new Color(0, 123, 255)); // fond bleu si actif
            bouton.setForeground(Color.WHITE);
        } else {
            bouton.setBackground(Color.LIGHT_GRAY);      // fond gris si inactif
            bouton.setForeground(Color.DARK_GRAY);
        }
    }
}
