package vue.admin;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * FooterAdmin affiche la barre de navigation en bas pour l'administrateur.
 *
 * <p>Comprend des boutons pour accéder au tableau de bord, à la liste des spécialistes,
 * à la liste des patients, et pour se déconnecter, avec indication du bouton actif.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class FooterAdmin extends JPanel {

    /**
     * Constructeur qui initialise les boutons de navigation.
     *
     * @param fenetre    la fenêtre principale de l'application
     * @param admin      l'utilisateur administrateur connecté
     * @param pageActive identifiant de la page active pour la mise en avant du bouton
     */
    public FooterAdmin(JFrame fenetre, Utilisateur admin, String pageActive) {
        // layout centré avec espacements entre les boutons
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        setBackground(Color.WHITE);
        // ligne de séparation en haut
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        // création des boutons de navigation
        JButton btnDashboard    = new JButton("🏠 Tableau de bord");
        JButton btnSpecialistes = new JButton("👨‍⚕️ Spécialistes");
        JButton btnPatients     = new JButton("🧍 Patients");
        JButton btnDeco         = new JButton("🚪 Déconnexion");

        // application du style et mise en avant du bouton actif
        styliser(btnDashboard,    pageActive.equals("dashboard"));
        styliser(btnSpecialistes, pageActive.equals("specialistes"));
        styliser(btnPatients,     pageActive.equals("patients"));
        styliser(btnDeco,         pageActive.equals("deconnexion"));

        // actions des boutons pour changer de contenu
        btnDashboard.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordAdmin(fenetre, admin));
            fenetre.revalidate();
            fenetre.repaint();
        });
        btnSpecialistes.addActionListener(e -> {
            fenetre.setContentPane(new ListeSpecialistesPanel(fenetre, admin));
            fenetre.revalidate();
            fenetre.repaint();
        });
        btnPatients.addActionListener(e -> {
            fenetre.setContentPane(new ListePatientsPanel(fenetre, admin));
            fenetre.revalidate();
            fenetre.repaint();
        });
        btnDeco.addActionListener(e -> {
            fenetre.setContentPane(new vue.Accueil.AccueilPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // ajout des boutons au panel
        add(btnDashboard);
        add(btnSpecialistes);
        add(btnPatients);
        add(btnDeco);
    }

    /**
     * Applique le style de base à un bouton et change sa couleur si actif.
     *
     * @param bouton le JButton à styliser
     * @param actif  true si le bouton représente la page active
     */
    private void styliser(JButton bouton, boolean actif) {
        bouton.setFocusPainted(false);                    // pas de contour focus
        bouton.setFont(new Font("Arial", Font.BOLD, 14)); // police et taille
        bouton.setPreferredSize(new Dimension(160, 35));  // taille fixe
        if (actif) {
            // couleur pour le bouton actif
            bouton.setBackground(new Color(0, 123, 255));
            bouton.setForeground(Color.WHITE);
        } else {
            // couleur pour les boutons inactifs
            bouton.setBackground(Color.LIGHT_GRAY);
            bouton.setForeground(Color.DARK_GRAY);
        }
    }
}