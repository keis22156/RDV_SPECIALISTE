package vue.Style;

import javax.swing.*;
import java.awt.*;

public class StyleGraphique {
    public static void styliserBoutonPrincipal(JButton bouton) {
        bouton.setBackground(new Color(0, 123, 255));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void styliserBoutonSecondaire(JButton bouton) {
        bouton.setBackground(new Color(230, 230, 230));
        bouton.setForeground(Color.BLACK);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("Arial", Font.PLAIN, 14));
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    public static void styliserLabelChamp(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void styliserChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setMaximumSize(new Dimension(400, 30));
    }

    public static void styliserMotDePasse(JPasswordField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setMaximumSize(new Dimension(400, 30));
    }
}
