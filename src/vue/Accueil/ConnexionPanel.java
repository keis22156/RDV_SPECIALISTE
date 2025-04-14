package vue.Accueil;

import controleur.ControleurConnexion;
import modele.Role;
import modele.Utilisateur;
import vue.Style.StyleGraphique;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau de connexion principal.
 * <p>
 * Affiche un formulaire permettant à l’utilisateur (patient, spécialiste ou admin)
 * de se connecter via son email et mot de passe.
 * </p>
 */
public class ConnexionPanel extends JPanel {

    /**
     * Construit le panneau de connexion.
     *
     * @param fenetre la fenêtre Swing dans laquelle ce panneau sera affiché
     */
    public ConnexionPanel(JFrame fenetre) {
        // layout principal et fond gris clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));

        add(new HeaderAccueil(), BorderLayout.NORTH);

        // Panel central blanc contenant le formulaire
        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Label et champ pour l’email
        JLabel emailLabel = new JLabel("Adresse email :");
        JTextField emailField = new JTextField();
        StyleGraphique.styliserLabelChamp(emailLabel);
        StyleGraphique.styliserChamp(emailField);

        // Label et champ pour le mot de passe
        JLabel mdpLabel = new JLabel("Mot de passe :");
        JPasswordField mdpField = new JPasswordField();
        StyleGraphique.styliserLabelChamp(mdpLabel);
        StyleGraphique.styliserChamp(mdpField);

        // Bouton de connexion
        JButton boutonConnexion = new JButton("Se connecter");
        StyleGraphique.styliserBoutonPrincipal(boutonConnexion);

        // Lien vers l’inscription
        JLabel messageInscription = new JLabel("Ou inscrivez-vous en cliquant ici");
        messageInscription.setFont(new Font("Arial", Font.ITALIC, 13));
        messageInscription.setForeground(Color.GRAY);
        messageInscription.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messageInscription.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Bascule vers le panneau d’inscription
                fenetre.setContentPane(new InscriptionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        // Ajout des composants dans le panel central
        centre.add(emailLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 5)));
        centre.add(emailField);
        centre.add(Box.createRigidArea(new Dimension(0, 10)));
        centre.add(mdpLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 5)));
        centre.add(mdpField);
        centre.add(Box.createRigidArea(new Dimension(0, 20)));
        centre.add(boutonConnexion);
        centre.add(Box.createRigidArea(new Dimension(0, 20)));
        centre.add(messageInscription);

        add(centre, BorderLayout.CENTER);

        // Action à l’appui du bouton "Se connecter"
        boutonConnexion.addActionListener(e -> {
            String email = emailField.getText();
            String motDePasse = new String(mdpField.getPassword());
            Utilisateur u = ControleurConnexion.verifierConnexion(email, motDePasse);
            if (u != null) {
                // Affichage d’un message de bienvenue selon le rôle
                switch (u.getRole()) {
                    case PATIENT -> JOptionPane.showMessageDialog(this, "Bienvenue " + u.getPrenom() + " " + u.getNom());
                    case SPECIALISTE -> JOptionPane.showMessageDialog(this, "Bienvenue Dr. " + u.getPrenom() + " " + u.getNom());
                    case ADMIN -> JOptionPane.showMessageDialog(this, "Bienvenue Administrateur");
                }
                // Redirection vers le tableau de bord adapté
                switch (u.getRole()) {
                    case PATIENT -> fenetre.setContentPane(new vue.patient.TableauDeBordPatient(fenetre, u));
                    case SPECIALISTE -> fenetre.setContentPane(new vue.specialiste.TableauDeBordSpecialiste(fenetre, u));
                    case ADMIN -> fenetre.setContentPane(new vue.admin.TableauDeBordAdmin(fenetre, u));
                }
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                // Message d’erreur si email/mot de passe invalide
                JOptionPane.showMessageDialog(this,
                        "Email ou mot de passe incorrect.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}