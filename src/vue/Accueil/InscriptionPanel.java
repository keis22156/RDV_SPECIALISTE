package vue.Accueil;

import controleur.ControleurInscription;
import vue.Style.StyleGraphique;

import javax.swing.*;
import java.awt.*;

public class InscriptionPanel extends JPanel {
    public InscriptionPanel(JFrame fenetre) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));

        add(new HeaderAccueil(), BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel nomLabel = new JLabel("Nom complet :");
        JTextField nomField = new JTextField();
        JLabel emailLabel = new JLabel("Adresse email :");
        JTextField emailField = new JTextField();
        JLabel mdpLabel = new JLabel("Mot de passe :");
        JPasswordField mdpField = new JPasswordField();

        StyleGraphique.styliserLabelChamp(nomLabel);
        StyleGraphique.styliserChamp(nomField);
        StyleGraphique.styliserLabelChamp(emailLabel);
        StyleGraphique.styliserChamp(emailField);
        StyleGraphique.styliserLabelChamp(mdpLabel);
        StyleGraphique.styliserChamp(mdpField);

        JButton boutonValider = new JButton("Créer mon compte");
        StyleGraphique.styliserBoutonPrincipal(boutonValider);

        JLabel messageConnexion = new JLabel("Déjà inscrit ? Connectez-vous ici");
        messageConnexion.setFont(new Font("Arial", Font.ITALIC, 13));
        messageConnexion.setForeground(Color.GRAY);
        messageConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageConnexion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messageConnexion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fenetre.setContentPane(new ConnexionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mdpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonValider.setAlignmentX(Component.CENTER_ALIGNMENT);

        centre.add(nomLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 5)));
        centre.add(nomField);
        centre.add(Box.createRigidArea(new Dimension(0, 10)));
        centre.add(emailLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 5)));
        centre.add(emailField);
        centre.add(Box.createRigidArea(new Dimension(0, 10)));
        centre.add(mdpLabel);
        centre.add(Box.createRigidArea(new Dimension(0, 5)));
        centre.add(mdpField);
        centre.add(Box.createRigidArea(new Dimension(0, 20)));
        centre.add(boutonValider);
        centre.add(Box.createRigidArea(new Dimension(0, 20)));
        centre.add(messageConnexion);

        add(centre, BorderLayout.CENTER);

        boutonValider.addActionListener(e -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            String motDePasse = new String(mdpField.getPassword());
            boolean success = ControleurInscription.inscrirePatient(nom, email, motDePasse);
            if (success) {
                JOptionPane.showMessageDialog(this, "Inscription réussie ! Vous pouvez maintenant vous connecter.");
                fenetre.setContentPane(new ConnexionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
