import javax.swing.*;
import vue.Accueil.AccueilPanel;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // ✅ Thème moderne
        } catch (Exception e) {
            System.out.println("Échec du style FlatLaf");
        }

        JFrame fenetre = new JFrame("Application de rendez-vous spécialiste");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1000, 700);
        fenetre.setLocationRelativeTo(null);
        fenetre.setContentPane(new AccueilPanel(fenetre));
        fenetre.setVisible(true);
    }
}
