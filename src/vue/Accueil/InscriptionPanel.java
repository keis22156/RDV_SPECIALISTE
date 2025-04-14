/**
 * Panel d'inscription pour les patients.
 * Affiche un formulaire comprenant tous les champs nécessaires
 * pour créer un compte patient, avec validation et navigation.
 */
package vue.Accueil;

import controleur.ControleurInscription;
import vue.Style.StyleGraphique;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class InscriptionPanel extends JPanel {

    /**
     * Constructeur du panel d'inscription.
     * Initialise tous les composants UI et configure les actions.
     *
     * @param fenetre La fenêtre principale dans laquelle le panel est affiché
     */
    public InscriptionPanel(JFrame fenetre) {
        // layout en border et fond gris clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));

        // header en haut
        add(new HeaderAccueil(), BorderLayout.NORTH);

        // panel scrollable pour le formulaire
        JPanel panneauFormulaire = new JPanel();
        panneauFormulaire.setLayout(new BoxLayout(panneauFormulaire, BoxLayout.Y_AXIS));
        panneauFormulaire.setBackground(Color.WHITE);
        panneauFormulaire.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JScrollPane scroll = new JScrollPane(panneauFormulaire);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);

        // titre du formulaire
        JLabel titre = new JLabel("Créer un compte patient", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panneauFormulaire.add(titre);

        // création des champs
        JTextField prenomField     = new JTextField();
        JTextField nomField        = new JTextField();
        JComboBox<Integer> ageBox  = new JComboBox<>(IntStream.rangeClosed(18, 100).boxed().toArray(Integer[]::new));
        JComboBox<Integer> poidsBox= new JComboBox<>(IntStream.rangeClosed(30, 200).boxed().toArray(Integer[]::new));
        JComboBox<String> sexeBox  = new JComboBox<>(new String[]{"Homme", "Femme"});
        JTextField numeroSecuField = new JTextField();
        JTextField adresseField    = new JTextField();
        JTextField telephoneField  = new JTextField();
        JTextField emailField      = new JTextField();
        JPasswordField mdpField    = new JPasswordField();

        // labels correspondants
        JLabel[] labels = {
                new JLabel("Prénom :"),
                new JLabel("Nom :"),
                new JLabel("Âge :"),
                new JLabel("Poids (kg) :"),
                new JLabel("Sexe :"),
                new JLabel("Numéro de sécurité sociale :"),
                new JLabel("Adresse :"),
                new JLabel("Téléphone :"),
                new JLabel("Adresse email :"),
                new JLabel("Mot de passe :")
        };

        // composants à ajouter
        JComponent[] champs = {
                prenomField, nomField, ageBox, poidsBox, sexeBox,
                numeroSecuField, adresseField, telephoneField,
                emailField, mdpField
        };

        // style et dimension des champs
        Dimension tailleFixe = new Dimension(400, 35);
        for (int i = 0; i < labels.length; i++) {
            StyleGraphique.styliserLabelChamp(labels[i]);
            labels[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            JComponent champ = champs[i];
            champ.setPreferredSize(tailleFixe);
            champ.setMaximumSize(tailleFixe);
            champ.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (champ instanceof JTextField) {
                StyleGraphique.styliserChamp((JTextField) champ);
            } else if (champ instanceof JPasswordField) {
                StyleGraphique.styliserChamp((JTextField) champ);
            }

            panneauFormulaire.add(labels[i]);
            panneauFormulaire.add(Box.createRigidArea(new Dimension(0, 5)));
            panneauFormulaire.add(champ);
            panneauFormulaire.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // bouton valider
        JButton boutonValider = new JButton("Créer mon compte");
        StyleGraphique.styliserBoutonPrincipal(boutonValider);
        boutonValider.setAlignmentX(Component.CENTER_ALIGNMENT);

        // lien vers connexion
        JLabel messageConnexion = new JLabel("Déjà inscrit ? Connectez-vous ici");
        messageConnexion.setFont(new Font("Arial", Font.ITALIC, 13));
        messageConnexion.setForeground(Color.GRAY);
        messageConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageConnexion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messageConnexion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fenetre.setContentPane(new ConnexionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        panneauFormulaire.add(Box.createRigidArea(new Dimension(0, 20)));
        panneauFormulaire.add(boutonValider);
        panneauFormulaire.add(Box.createRigidArea(new Dimension(0, 15)));
        panneauFormulaire.add(messageConnexion);

        add(scroll, BorderLayout.CENTER);

        // action du bouton valider
        boutonValider.addActionListener(e -> {
            String prenom     = prenomField.getText().trim();
            String nom        = nomField.getText().trim();
            Integer age       = (Integer) ageBox.getSelectedItem();
            Double poids      = ((Integer) poidsBox.getSelectedItem()).doubleValue();
            String sexe       = (String) sexeBox.getSelectedItem();
            String numSecu    = numeroSecuField.getText().trim();
            String adresse    = adresseField.getText().trim();
            String telephone  = telephoneField.getText().trim();
            String email      = emailField.getText().trim();
            String mdp        = new String(mdpField.getPassword()).trim();

            boolean success = ControleurInscription.inscrirePatient(
                    prenom, nom, email, mdp,
                    age, poids, numSecu, sexe,
                    adresse, telephone
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inscription réussie ! Vous pouvez maintenant vous connecter."
                );
                fenetre.setContentPane(new ConnexionPanel(fenetre));
                fenetre.revalidate();
                fenetre.repaint();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Erreur lors de l'inscription.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}