package vue.patient;

import dao.UtilisateurDAO;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PrendreRDVPanel extends JPanel {
    public PrendreRDVPanel(JFrame fenetre, Utilisateur patient) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new HeaderPatient(patient), BorderLayout.NORTH);

        JLabel titre = new JLabel("Choisissez un spécialiste", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(Color.WHITE);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        ArrayList<Utilisateur> specialistes = UtilisateurDAO.getUtilisateursParRole("specialiste");
        for (Utilisateur s : specialistes) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)));
            card.setBackground(Color.WHITE);

            JLabel nom = new JLabel(s.getNom());
            nom.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel image;
            if (s.getPhoto() != null && new File(s.getPhoto()).exists()) {
                ImageIcon icone = new ImageIcon(s.getPhoto());
                Image img = icone.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                image = new JLabel(new ImageIcon(img));
            } else {
                image = new JLabel("👤");
                image.setFont(new Font("Arial", Font.PLAIN, 40));
                image.setForeground(Color.GRAY);
            }

            JButton voirDispo = new JButton("📅 Voir les créneaux");
            voirDispo.setFont(new Font("Arial", Font.PLAIN, 14));
            voirDispo.addActionListener(e -> {
                fenetre.setContentPane(new TableauCreneauxPanel(fenetre, patient, s.getId()));
                fenetre.revalidate();
                fenetre.repaint();
            });

            JPanel gauche = new JPanel();
            gauche.setBackground(Color.WHITE);
            gauche.add(image);

            JPanel centre = new JPanel();
            centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
            centre.setBackground(Color.WHITE);
            centre.add(nom);

            JPanel droite = new JPanel();
            droite.setBackground(Color.WHITE);
            droite.add(voirDispo);

            card.add(gauche, BorderLayout.WEST);
            card.add(centre, BorderLayout.CENTER);
            card.add(droite, BorderLayout.EAST);

            scrollPanel.add(card);
            scrollPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(scrollPanel);
        scroll.setBorder(null);

        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Color.WHITE);
        centre.add(titre, BorderLayout.NORTH);
        centre.add(scroll, BorderLayout.CENTER);

        add(centre, BorderLayout.CENTER);
        add(new FooterPatient(fenetre, patient, "rdv"), BorderLayout.SOUTH);
    }
}
