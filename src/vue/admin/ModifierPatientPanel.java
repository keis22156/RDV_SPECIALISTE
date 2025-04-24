package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Panel permettant à l'administrateur de modifier les informations d'un patient.
 *
 * <p>Affiche un formulaire pour mettre à jour le nom complet et l'email du patient,
 * et sauvegarde les modifications en base de données.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class ModifierPatientPanel extends JPanel {

    /**
     * Constructeur qui initialise le formulaire de modification pour un patient.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     * @param patient le patient à modifier
     */
    public ModifierPatientPanel(JFrame fenetre, Utilisateur admin, Utilisateur patient) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec infos admin
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // panneau central centré
        JPanel centre = new JPanel(new GridBagLayout());
        centre.setBackground(Color.decode("#f5f7fb"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // carte blanche pour le formulaire
        JPanel carte = new JPanel();
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBackground(Color.WHITE);
        carte.setPreferredSize(new Dimension(500, 300));
        carte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // titre du formulaire
        JLabel titre = new JLabel("Modifier le patient : " + patient.getNom());
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // champ pour le nom complet
        JLabel nomLabel = new JLabel("Nom complet :");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nomField = new JTextField(patient.getNom());
        nomField.setMaximumSize(new Dimension(400, 30));
        nomField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // champ pour l'email
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField emailField = new JTextField(patient.getEmail());
        emailField.setMaximumSize(new Dimension(400, 30));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // bouton enregistrer
        JButton valider = new JButton("💾 Enregistrer");
        valider.setFont(new Font("Arial", Font.BOLD, 14));
        valider.setBackground(new Color(0, 123, 255));
        valider.setForeground(Color.WHITE);
        valider.setFocusPainted(false);
        valider.setPreferredSize(new Dimension(300, 40));
        valider.setAlignmentX(Component.CENTER_ALIGNMENT);

        // action du bouton enregistrer
        valider.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String email = emailField.getText().trim();
            if (!nom.isEmpty() && !email.isEmpty()) {
                // mise à jour de l'objet et de la BDD
                patient.setNom(nom);
                patient.setEmail(email);
                UtilisateurDAO.mettreAJourInfos(patient);
                JOptionPane.showMessageDialog(this, "Patient mis à jour");
                // retour à la liste des patients
                fenetre.setContentPane(new ListePatientsPanel(fenetre, admin));
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                // message d'erreur si champs vides
                JOptionPane.showMessageDialog(
                        this,
                        "Champs invalides",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // assemblage du formulaire
        carte.add(titre);
        carte.add(Box.createRigidArea(new Dimension(0, 25)));
        carte.add(nomLabel);
        carte.add(nomField);
        carte.add(Box.createRigidArea(new Dimension(0, 15)));
        carte.add(emailLabel);
        carte.add(emailField);
        carte.add(Box.createRigidArea(new Dimension(0, 25)));
        carte.add(valider);

        centre.add(carte, gbc);
        add(centre, BorderLayout.CENTER);

        // footer avec navigation admin
        add(new FooterAdmin(fenetre, admin, "patients"), BorderLayout.SOUTH);
    }
}