package vue.Accueil;

import javax.swing.*;
import java.awt.*;

public class HeaderAccueil extends JPanel {
    public HeaderAccueil() {
        setLayout(new BorderLayout());
        setBackground(new Color(26, 57, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/TP JAVA 2025/RDVSPECIALISTE/logoaccueil.png"); // à adapter
        Image image = logoIcon.getImage().getScaledInstance(-1, 60, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));

        JPanel blocGauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        blocGauche.setBackground(getBackground());
        blocGauche.add(logo);

        add(blocGauche, BorderLayout.WEST);
    }
}
