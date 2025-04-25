package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Panel listant tous les patients pour l'administrateur.
 *
 * <p>Affiche une carte pour chaque patient avec son nom, son email,
 * et propose des actions pour consulter ses rendez-vous ou modifier ses informations.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class ListePatientsPanel extends JPanel {

    /**
     * Constructeur qui initialise la liste des patients.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     */
    public ListePatientsPanel(JFrame fenetre, Utilisateur admin) {
        // setup de base
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // panel scrollable pour les cartes
        JPanel liste = new JPanel();
        liste.setLayout(new BoxLayout(liste, BoxLayout.Y_AXIS));
        liste.setBackground(Color.decode("#f5f7fb"));
        liste.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // récupère tous les patients
        List<Utilisateur> patients = UtilisateurDAO.getUtilisateursParRole("PATIENT");
        if (!patients.isEmpty()) {
            // création d'une carte par patient
            for (Utilisateur p : patients) {
                JPanel card = new JPanel(new BorderLayout(15, 0));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                // photo ou icône par défaut
                JLabel photo;
                if (p.getPhoto() != null && new File(p.getPhoto()).exists()) {
                    ImageIcon icon = new ImageIcon(p.getPhoto());
                    Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    photo = new JLabel(new ImageIcon(img));
                } else {
                    photo = new JLabel("👤");
                    photo.setFont(new Font("Arial", Font.PLAIN, 40));
                    photo.setForeground(Color.GRAY);
                }

                // nom et email
                JLabel nom = new JLabel(p.getNom());
                nom.setFont(new Font("Arial", Font.BOLD, 16));
                JLabel email = new JLabel(p.getEmail());
                email.setFont(new Font("Arial", Font.PLAIN, 14));
                email.setForeground(Color.GRAY);

                JPanel texte = new JPanel();
                texte.setLayout(new BoxLayout(texte, BoxLayout.Y_AXIS));
                texte.setBackground(Color.WHITE);
                texte.add(nom);
                texte.add(email);

                JPanel gauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
                gauche.setBackground(Color.WHITE);
                gauche.add(photo);
                gauche.add(Box.createHorizontalStrut(10));
                gauche.add(texte);

                // panel actions à droite
                JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                actions.setBackground(Color.WHITE);

                // bouton voir RDV
                JButton voirRDV = new JButton("🗓 Voir RDV");
                voirRDV.setFont(new Font("Arial", Font.BOLD, 13));
                voirRDV.setBackground(new Color(40, 167, 69));
                voirRDV.setForeground(Color.WHITE);
                voirRDV.setFocusPainted(false);
                voirRDV.setPreferredSize(new Dimension(120, 35));
                voirRDV.addActionListener(e -> {
                    // affiche les RDV du patient
                    fenetre.setContentPane(new RendezVousPatientAdminPanel(fenetre, admin, p));
                    fenetre.revalidate();
                    fenetre.repaint();
                });

                // bouton modifier
                JButton modifier = new JButton("🖊 Modifier");
                modifier.setFont(new Font("Arial", Font.BOLD, 13));
                modifier.setBackground(new Color(0, 123, 255));
                modifier.setForeground(Color.WHITE);
                modifier.setFocusPainted(false);
                modifier.setPreferredSize(new Dimension(120, 35));
                modifier.addActionListener(e -> {
                    // passe au formulaire de modification
                    fenetre.setContentPane(new ModifierPatientPanel(fenetre, admin, p));
                    fenetre.revalidate();
                    fenetre.repaint();
                });

                actions.add(voirRDV);
                actions.add(modifier);

                // assemblage de la carte
                card.add(gauche, BorderLayout.CENTER);
                card.add(actions, BorderLayout.EAST);

                liste.add(card);
                liste.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        } else {
            // message si pas de patient
            JLabel vide = new JLabel("Aucun patient enregistré.");
            vide.setFont(new Font("Arial", Font.ITALIC, 16));
            vide.setAlignmentX(Component.CENTER_ALIGNMENT);
            liste.add(vide);
        }

        // scroll pour la liste
        JScrollPane scrollPane = new JScrollPane(liste);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // footer admin
        add(new FooterAdmin(fenetre, admin, "patients"), BorderLayout.SOUTH);
    }
}