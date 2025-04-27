package vue.admin;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Panel listant tous les spécialistes pour l'administrateur.
 *
 * <p>Affiche une carte pour chaque spécialiste avec son nom, son email,
 * et propose des actions pour modifier ou consulter l'agenda.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class ListeSpecialistesPanel extends JPanel {

    /**
     * Constructeur qui initialise la liste des spécialistes.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     */
    public ListeSpecialistesPanel(JFrame fenetre, Utilisateur admin) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header admin
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // panel pour la liste
        JPanel liste = new JPanel();
        liste.setLayout(new BoxLayout(liste, BoxLayout.Y_AXIS));
        liste.setBackground(Color.decode("#f5f7fb"));
        liste.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // récupère tous les spécialistes
        List<Utilisateur> specialistes = UtilisateurDAO.getTousLesSpecialistes();
        if (!specialistes.isEmpty()) {
            // création d'une carte par spécialiste
            for (Utilisateur s : specialistes) {
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
                if (s.getPhoto() != null && new File(s.getPhoto()).exists()) {
                    ImageIcon icon = new ImageIcon(s.getPhoto());
                    Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    photo = new JLabel(new ImageIcon(img));
                } else {
                    photo = new JLabel("👤");
                    photo.setFont(new Font("Arial", Font.PLAIN, 40));
                    photo.setForeground(Color.GRAY);
                }

                // nom et email du spécialiste
                JLabel nom = new JLabel(s.getNom());
                nom.setFont(new Font("Arial", Font.BOLD, 16));
                JLabel email = new JLabel(s.getEmail());
                email.setFont(new Font("Arial", Font.PLAIN, 14));
                email.setForeground(Color.GRAY);

                JPanel texte = new JPanel();
                texte.setLayout(new BoxLayout(texte, BoxLayout.Y_AXIS));
                texte.setBackground(Color.WHITE);
                texte.add(nom);
                texte.add(email);

                // zone gauche avec photo et texte
                JPanel gauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
                gauche.setBackground(Color.WHITE);
                gauche.add(photo);
                gauche.add(Box.createHorizontalStrut(10));
                gauche.add(texte);

                // zone droite avec boutons action
                JPanel droite = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                droite.setBackground(Color.WHITE);

                // bouton modifier
                JButton modifier = new JButton("🖊 Modifier");
                modifier.setFont(new Font("Arial", Font.BOLD, 13));
                modifier.setBackground(new Color(0, 123, 255));
                modifier.setForeground(Color.WHITE);
                modifier.setFocusPainted(false);
                modifier.setPreferredSize(new Dimension(120, 35));
                modifier.addActionListener(e -> {
                    fenetre.setContentPane(new ModifierSpecialistePanel(fenetre, admin, s));
                    fenetre.revalidate();
                    fenetre.repaint();
                });

                // bouton agenda
                JButton agenda = new JButton("📅 Agenda");
                agenda.setFont(new Font("Arial", Font.BOLD, 13));
                agenda.setBackground(new Color(23, 162, 184));
                agenda.setForeground(Color.WHITE);
                agenda.setFocusPainted(false);
                agenda.setPreferredSize(new Dimension(120, 35));
                agenda.addActionListener(e -> {
                    fenetre.setContentPane(new AgendaSpecialisteAdminPanel(fenetre, admin, s));
                    fenetre.revalidate();
                    fenetre.repaint();
                });

                droite.add(modifier);
                droite.add(agenda);

                // assemblage de la carte
                card.add(gauche, BorderLayout.CENTER);
                card.add(droite, BorderLayout.EAST);

                liste.add(card);
                liste.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        } else {
            // message si aucun spécialiste
            JLabel vide = new JLabel("Aucun spécialiste enregistré.");
            vide.setFont(new Font("Arial", Font.ITALIC, 16));
            vide.setAlignmentX(Component.CENTER_ALIGNMENT);
            liste.add(vide);
        }

        // scroll pour la liste
        JScrollPane scrollPane = new JScrollPane(liste);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // bouton ajouter un spécialiste
        JPanel ajouterPanel = new JPanel();
        ajouterPanel.setBackground(Color.decode("#f5f7fb"));
        ajouterPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton ajouter = new JButton("➕ Ajouter un spécialiste");
        ajouter.setFont(new Font("Arial", Font.BOLD, 15));
        ajouter.setBackground(new Color(40, 167, 69));
        ajouter.setForeground(Color.WHITE);
        ajouter.setFocusPainted(false);
        ajouter.setPreferredSize(new Dimension(240, 40));
        ajouter.addActionListener(e -> {
            fenetre.setContentPane(new AjouterSpecialistePanel(fenetre, admin));
            fenetre.revalidate();
            fenetre.repaint();
        });

        ajouterPanel.add(ajouter);

        JPanel bas = new JPanel(new BorderLayout());
        bas.setBackground(Color.decode("#f5f7fb"));
        bas.add(ajouterPanel, BorderLayout.NORTH);
        bas.add(new FooterAdmin(fenetre, admin, "specialistes"), BorderLayout.SOUTH);

        add(bas, BorderLayout.SOUTH);
    }
}