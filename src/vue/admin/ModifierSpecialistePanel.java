package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Panel permettant à l'administrateur de modifier les informations d'un spécialiste.
 *
 * <p>Affiche un formulaire pré-rempli avec le nom et l'email du spécialiste,
 * et permet de sauvegarder les modifications dans la base de données.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class ModifierSpecialistePanel extends JPanel {

    /**
     * Constructeur qui initialise le formulaire de modification pour un spécialiste.
     *
     * @param fenetre      la fenêtre principale de l'application
     * @param admin        l'utilisateur administrateur connecté
     * @param specialiste  le spécialiste à modifier
     */
    public ModifierSpecialistePanel(JFrame fenetre, Utilisateur admin, Utilisateur specialiste) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header admin
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // panneau central avec GridBag pour centrer la carte
        JPanel centre = new JPanel(new GridBagLayout());
        centre.setBackground(Color.decode("#f5f7fb"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // carte blanche pour le formulaire
        JPanel carte = new JPanel();
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBackground(Color.WHITE);
        carte.setPreferredSize(new Dimension(500, 300));
        carte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // titre de la carte
        JLabel titre = new JLabel("Modifier le spécialiste : " + specialiste.getNom());
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // label et champ pour le nom complet
        JLabel nomLabel = new JLabel("Nom complet :");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nomField = new JTextField(specialiste.getNom());
        nomField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nomField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // label et champ pour l'email
        JLabel emailLabel = new JLabel("Adresse email :");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField emailField = new JTextField(specialiste.getEmail());
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // bouton pour enregistrer les modifications
        JButton enregistrer = new JButton("Modifier");
        enregistrer.setFont(new Font("Arial", Font.BOLD, 15));
        enregistrer.setBackground(new Color(0, 123, 255));
        enregistrer.setForeground(Color.WHITE);
        enregistrer.setFocusPainted(false);
        enregistrer.setAlignmentX(Component.CENTER_ALIGNMENT);
        enregistrer.setMaximumSize(new Dimension(300, 40));
        enregistrer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // action du bouton enregistrer
        enregistrer.addActionListener(e -> {
            String nouveauNom = nomField.getText().trim();
            String nouveauMail = emailField.getText().trim();
            if (!nouveauNom.isEmpty() && !nouveauMail.isEmpty()) {
                // mise à jour de l'objet et de la BDD
                specialiste.setNom(nouveauNom);
                specialiste.setEmail(nouveauMail);
                UtilisateurDAO.mettreAJourInfos(specialiste);
                JOptionPane.showMessageDialog(this, "Spécialiste mis à jour ✅");
                // retour à la liste des spécialistes
                fenetre.setContentPane(new ListeSpecialistesPanel(fenetre, admin));
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                // message d'erreur si champs vides
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez remplir tous les champs.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // ajout des composants dans la carte
        carte.add(titre);
        carte.add(nomLabel);
        carte.add(nomField);
        carte.add(emailLabel);
        carte.add(emailField);
        carte.add(Box.createRigidArea(new Dimension(0, 25)));
        carte.add(enregistrer);

        // ajout de la carte au panneau central
        centre.add(carte, gbc);
        add(centre, BorderLayout.CENTER);

        // footer admin avec navigation spécialistes
        add(new FooterAdmin(fenetre, admin, "specialistes"), BorderLayout.SOUTH);
    }
}