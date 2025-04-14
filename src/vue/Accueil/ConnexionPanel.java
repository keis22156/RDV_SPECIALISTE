package vue.Accueil;

import controleur.ControleurConnexion;
import modele.Role;
import modele.Utilisateur;
import vue.Style.StyleGraphique;
import javax.swing.*;
import java.awt.*;

public class ConnexionPanel extends JPanel {
    public ConnexionPanel(JFrame fenetre) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));

        add(new HeaderAccueil(), BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel emailLabel = new JLabel("Adresse email :");
        JTextField emailField = new JTextField();
        JLabel mdpLabel = new JLabel("Mot de passe :");
        JPasswordField mdpField = new JPasswordField();

        StyleGraphique.styliserLabelChamp(emailLabel);
        StyleGraphique.styliserChamp(emailField);
        StyleGraphique.styliserLabelChamp(mdpLabel);
        StyleGraphique.styliserChamp(mdpField);

        JButton boutonConnexion = new JButton("Se connecter");
        StyleGraphique.styliserBoutonPrincipal(boutonConnexion);

        JLabel messageInscription = new JLabel("Ou inscrivez-vous en cliquant ici");
        messageInscription.setFont(new Font("Arial", Font.ITALIC, 13));
        messageInscription.setForeground(Color.GRAY);
        messageInscription.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageInscription.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messageInscription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fenetre.setContentPane(new InscriptionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        boutonConnexion.addActionListener(e -> {
            String email = emailField.getText();
            String mdp = new String(mdpField.getPassword());
            Utilisateur u = ControleurConnexion.verifierConnexion(email, mdp);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Bienvenue " + u.getNom());
                switch (u.getRole()) {
                    case PATIENT -> fenetre.setContentPane(new vue.patient.TableauDeBordPatient(fenetre, u));
                    case SPECIALISTE -> fenetre.setContentPane(new vue.specialiste.TableauDeBordSpecialiste(fenetre, u));
                    case ADMIN -> fenetre.setContentPane(new vue.admin.TableauDeBordAdmin(fenetre, u));
                }
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
