package vue.specialiste;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HeaderSpecialiste extends JPanel {
    public HeaderSpecialiste(Utilisateur specialiste) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#2f4050"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/TP JAVA 2025/RDVSPECIALISTE/logospecialiste.png");
        Image image = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel nom = new JLabel(specialiste.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 20));
        nom.setForeground(Color.LIGHT_GRAY);

        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setBackground(getBackground());
        infos.add(nom);

        JLabel photo;
        if (specialiste.getPhoto() != null && new File(specialiste.getPhoto()).exists()) {
            ImageIcon icone = new ImageIcon(specialiste.getPhoto());
            Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            photo = new JLabel(new ImageIcon(img));
        } else {
            photo = new JLabel("👤");
            photo.setFont(new Font("Arial", Font.PLAIN, 50));
            photo.setForeground(Color.WHITE);
        }
        photo.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel blocDroit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        blocDroit.setBackground(getBackground());
        blocDroit.add(infos);
        blocDroit.add(photo);

        add(blocDroit, BorderLayout.EAST);
        add(logo, BorderLayout.WEST);
    }
}
