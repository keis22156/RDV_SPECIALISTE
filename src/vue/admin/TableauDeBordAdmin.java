package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Affiche le tableau de bord pour l'administrateur.
 *
 * <p>Présente une carte d'actions permettant de gérer les spécialistes,
 * les patients et d'ajouter de nouveaux spécialistes, ainsi que
 * la déconnexion.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class TableauDeBordAdmin extends JPanel {

    /**
     * Constructeur qui initialise l'interface du tableau de bord admin.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     */
    public TableauDeBordAdmin(JFrame fenetre, Utilisateur admin) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec infos admin
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // contenu central en deux colonnes
        JPanel contenu = new JPanel(new GridLayout(1, 2, 40, 0));
        contenu.setBackground(Color.decode("#f5f7fb"));
        contenu.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // carte des actions
        JPanel cardActions = new JPanel();
        cardActions.setLayout(new BoxLayout(cardActions, BoxLayout.Y_AXIS));
        cardActions.setBackground(Color.WHITE);
        cardActions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // titre de la carte
        JLabel titreActions = new JLabel("Bienvenue ADMIN");
        titreActions.setFont(new Font("Arial", Font.BOLD, 20));
        titreActions.setAlignmentX(Component.CENTER_ALIGNMENT);

        // création des boutons d'action
        JButton btnSpecs       = creerBoutonAction("👨‍⚕️ Gérer les spécialistes");
        JButton btnPatients    = creerBoutonAction("🧍 Gérer les patients");
        JButton btnAjout       = creerBoutonAction("➕ Ajouter un spécialiste");
        JButton btnDeconnexion = creerBoutonAction("🚪 Déconnexion");

        // listeners pour changer de vue
        btnSpecs.addActionListener(e -> {
            fenetre.setContentPane(new ListeSpecialistesPanel(fenetre, admin));
            fenetre.revalidate(); fenetre.repaint();
        });
        btnPatients.addActionListener(e -> {
            fenetre.setContentPane(new ListePatientsPanel(fenetre, admin));
            fenetre.revalidate(); fenetre.repaint();
        });
        btnAjout.addActionListener(e -> {
            fenetre.setContentPane(new AjouterSpecialistePanel(fenetre, admin));
            fenetre.revalidate(); fenetre.repaint();
        });
        btnDeconnexion.addActionListener(e -> {
            fenetre.setContentPane(new vue.Accueil.AccueilPanel(fenetre));
            fenetre.revalidate(); fenetre.repaint();
        });

        // ajout du titre et des boutons dans la carte
        cardActions.add(titreActions);
        cardActions.add(Box.createRigidArea(new Dimension(0, 25)));
        for (JButton b : new JButton[]{btnSpecs, btnPatients, btnAjout, btnDeconnexion}) {
            cardActions.add(b);
            cardActions.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // ajout de la carte au contenu
        contenu.add(cardActions);
        add(contenu, BorderLayout.CENTER);

        // footer avec navigation admin
        add(new FooterAdmin(fenetre, admin, "accueil"), BorderLayout.SOUTH);
    }

    /**
     * Fabrique un bouton d'action stylisé pour l'admin.
     *
     * @param texte le texte du bouton
     * @return un JButton configuré
     */
    private JButton creerBoutonAction(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setFocusPainted(false);
        bouton.setBackground(new Color(0, 123, 255));
        bouton.setForeground(Color.WHITE);
        bouton.setMaximumSize(new Dimension(240, 40));
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return bouton;
    }
}
