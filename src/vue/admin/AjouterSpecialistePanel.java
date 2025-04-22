package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * AjouterSpecialistePanel permet à l'administrateur d'ajouter un nouveau spécialiste.
 *
 * <p>Affiche un formulaire avec les champs nécessaires (prénom, nom, email, mot de passe,
 * adresse, téléphone, spécialité, description) et un bouton pour créer le spécialiste.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class AjouterSpecialistePanel extends JPanel {

    /**
     * Constructeur qui initialise le formulaire d'ajout de spécialiste.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     */
    public AjouterSpecialistePanel(JFrame fenetre, Utilisateur admin) {
        // layout principal en BorderLayout et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header de l'admin en haut
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // centre avec GridBagLayout pour centrer la "carte" du formulaire
        JPanel centre = new JPanel(new GridBagLayout());
        centre.setBackground(Color.decode("#f5f7fb"));
        add(centre, BorderLayout.CENTER);

        // carte blanche pour le formulaire d'ajout
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 600));
        card.setMaximumSize(new Dimension(550, 650));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(35, 40, 35, 40)
        ));

        // titre du formulaire
        JLabel titre = new JLabel("➕ Ajouter un nouveau spécialiste");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        card.add(titre);

        // champs de saisie pour les données du spécialiste
        JTextField prenomField      = new JTextField();
        JTextField nomField         = new JTextField();
        JTextField emailField       = new JTextField();
        JPasswordField mdpField     = new JPasswordField();
        JTextField adresseField     = new JTextField();
        JTextField telephoneField   = new JTextField();   // champ téléphone
        JTextField specialiteField  = new JTextField();
        JTextArea descriptionArea   = new JTextArea(3, 20);

        // méthode utilitaire pour ajouter un label et son champ
        creerChamp(card, "Prénom :", prenomField);
        creerChamp(card, "Nom :", nomField);
        creerChamp(card, "Email :", emailField);
        creerChamp(card, "Mot de passe :", mdpField);
        creerChamp(card, "Adresse :", adresseField);
        creerChamp(card, "Téléphone :", telephoneField);
        creerChamp(card, "Spécialité :", specialiteField);

        // label et zone de texte pour la description
        JLabel descLabel = new JLabel("Description :");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(descLabel);

        // configuration de la zone de description
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        card.add(scrollPane);

        // bouton pour créer le spécialiste
        JButton creer = new JButton("✅ Créer le spécialiste");
        creer.setFont(new Font("Arial", Font.BOLD, 15));
        creer.setBackground(new Color(40, 167, 69)); // vert
        creer.setForeground(Color.WHITE);
        creer.setFocusPainted(false);
        creer.setAlignmentX(Component.CENTER_ALIGNMENT);
        creer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // action du bouton créer
        creer.addActionListener(e -> {
            // récupération et nettoyage des valeurs saisies
            String prenom     = prenomField.getText().trim();
            String nom        = nomField.getText().trim();
            String email      = emailField.getText().trim();
            String mdp        = new String(mdpField.getPassword()).trim();
            String adresse    = adresseField.getText().trim();
            String telephone  = telephoneField.getText().trim();
            String specialite = specialiteField.getText().trim();
            String description= descriptionArea.getText().trim();

            // vérification des champs obligatoires
            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || mdp.isEmpty()
                    || adresse.isEmpty() || telephone.isEmpty() || specialite.isEmpty()) {
                JOptionPane.showMessageDialog(
                        fenetre,
                        "Veuillez remplir tous les champs obligatoires",
                        "Champs requis",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // appel au DAO pour créer le spécialiste en base
            boolean success = UtilisateurDAO.creerSpecialiste(
                    prenom, nom, email, mdp, specialite, description, telephone, adresse
            );

            if (success) {
                JOptionPane.showMessageDialog(fenetre, "Spécialiste ajouté avec succès ✅");
                fenetre.setContentPane(new ListeSpecialistesPanel(fenetre, admin));
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                JOptionPane.showMessageDialog(
                        fenetre,
                        "Erreur lors de l'ajout ❌",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(creer);

        // ajout de la carte au panel centre
        centre.add(card);
        // footer admin en bas
        add(new FooterAdmin(fenetre, admin, "ajout"), BorderLayout.SOUTH);
    }

    /**
     * Ajoute un label et un champ (JComponent) à un panel avec un espacement.
     *
     * @param panel      le conteneur où ajouter
     * @param labelTexte le texte du label
     * @param champ      le composant de saisie associé
     */
    private void creerChamp(JPanel panel, String labelTexte, JComponent champ) {
        JLabel label = new JLabel(labelTexte);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(label);
        panel.add(champ);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
}