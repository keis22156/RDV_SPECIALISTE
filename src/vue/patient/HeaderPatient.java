package vue.patient;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HeaderPatient extends JPanel {
    public HeaderPatient(Utilisateur patient) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#2f4050"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/TP JAVA 2025/RDVSPECIALISTE/logopatient.png");
        Image image = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel nom = new JLabel(patient.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 20));
        nom.setForeground(Color.LIGHT_GRAY);

        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setBackground(getBackground());
        infos.add(nom);



        JLabel photo;
        if (patient.getPhoto() != null && new File(patient.getPhoto()).exists()) {
            ImageIcon icone = new ImageIcon(patient.getPhoto());
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
