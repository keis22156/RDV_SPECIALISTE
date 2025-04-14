package vue.patient;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Affiche le header pour l'interface patient.
 *
 * <p>Contient le logo, le nom et la photo (ou icône par défaut)
 * du patient connecté.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class HeaderPatient extends JPanel {

    /**
     * Constructeur qui initialise le header du patient.
     *
     * @param patient l'utilisateur patient connecté
     */
    public HeaderPatient(Utilisateur patient) {
        // layout en bordures et fond sombre
        setLayout(new BorderLayout());
        setBackground(Color.decode("#2f4050"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // logo à gauche
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/Documents/RDVSPECIALISTE/logopatient.png");
        Image image = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        // prénom et nom du patient
        JLabel prenom = new JLabel(patient.getPrenom());
        prenom.setFont(new Font("Arial", Font.PLAIN, 20));
        prenom.setForeground(Color.LIGHT_GRAY);

        JLabel nom = new JLabel(patient.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 20));
        nom.setForeground(Color.LIGHT_GRAY);

        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setBackground(getBackground());
        infos.add(prenom);
        infos.add(nom);

        // photo de profil ou icône par défaut
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

        // conteneur droit avec nom et photo
        JPanel blocDroit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        blocDroit.setBackground(getBackground());
        blocDroit.add(infos);
        blocDroit.add(photo);

        // ajout des composants
        add(blocDroit, BorderLayout.EAST);
        add(logo, BorderLayout.WEST);
    }
}
