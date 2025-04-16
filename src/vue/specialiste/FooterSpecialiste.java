package vue.specialiste;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class FooterSpecialiste extends JPanel {
    public FooterSpecialiste(JFrame fenetre, Utilisateur specialiste, String pageActive) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JButton btnAccueil = new JButton("🏠Accueil");
        JButton btnAgenda = new JButton("🗓Mon agenda");
        JButton btnHistorique = new JButton("📜Historique");
        JButton btnDeco = new JButton("🚪Déconnexion");

        styliser(btnAccueil, pageActive.equals("accueil"));
        styliser(btnAgenda, pageActive.equals("agenda"));
        styliser(btnHistorique, pageActive.equals("historique"));
        styliser(btnDeco, pageActive.equals("deconnexion"));

        btnAccueil.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordSpecialiste(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnAgenda.addActionListener(e -> {
            fenetre.setContentPane(new AgendaSpecialistePanel(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnHistorique.addActionListener(e -> {
            fenetre.setContentPane(new HistoriqueSpecialistePanel(fenetre, specialiste));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnDeco.addActionListener(e -> {
            fenetre.setContentPane(new vue.Accueil.AccueilPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });

        add(btnAccueil);
        add(btnAgenda);
        add(btnHistorique);
        add(btnDeco);
    }

    private void styliser(JButton bouton, boolean actif) {
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setPreferredSize(new Dimension(150, 35));
        if (actif) {
            bouton.setBackground(new Color(0, 123, 255));
            bouton.setForeground(Color.WHITE);
        } else {
            bouton.setBackground(Color.LIGHT_GRAY);
            bouton.setForeground(Color.DARK_GRAY);
        }
    }
}
