package vue.patient;

import modele.Utilisateur;
import vue.Accueil.AccueilPanel;
import javax.swing.*;
import java.awt.*;

public class FooterPatient extends JPanel {

    public FooterPatient(JFrame fenetre, Utilisateur patient, String pageActive) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JButton btnAccueil = new JButton("🏠 Accueil");
        JButton btnRDV = new JButton("📆 Prendre RDV");
        JButton btnHistorique = new JButton("📜 Historique");
        JButton btnDeco = new JButton("🚪 Déconnexion");


        styliser(btnAccueil, pageActive.equals("dashboard"));
        styliser(btnRDV, pageActive.equals("rdv"));
        styliser(btnHistorique, pageActive.equals("historique"));
        styliser(btnDeco, pageActive.equals("deconnexion"));add(btnAccueil);
        add(btnRDV);
        add(btnHistorique);
        add(btnDeco);


        btnAccueil.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordPatient(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnRDV.addActionListener(e -> {
            fenetre.setContentPane(new PrendreRDVPanel(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnHistorique.addActionListener(e -> {
            fenetre.setContentPane(new HistoriquePatientPanel(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        btnDeco.addActionListener(e -> {
            fenetre.setContentPane(new vue.Accueil.AccueilPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });
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
