package vue.specialiste;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Affiche le header avec le logo et les informations du spécialiste.
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class HeaderSpecialiste extends JPanel {

    /**
     * Constructeur qui crée la bannière du spécialiste.
     *
     * @param specialiste l’utilisateur spécialiste connecté
     */
    public HeaderSpecialiste(Utilisateur specialiste) {
        // layout en bordures et fond sombre
        setLayout(new BorderLayout());
        setBackground(Color.decode("#2f4050"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // logo à gauche
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/Documents/RDVSPECIALISTE/logospecialiste.png");
        Image image = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        // nom du spécialiste
        JLabel nom = new JLabel("Dr. " + specialiste.getPrenom() + " " + specialiste.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 20)); // police taille 20
        nom.setForeground(Color.LIGHT_GRAY);            // texte gris clair

        // panneau pour afficher le nom
        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setBackground(getBackground());
        infos.add(nom);

        // photo de profil à droite ou icône par défaut
        JLabel photo;
        if (specialiste.getPhoto() != null && new File(specialiste.getPhoto()).exists()) {
            ImageIcon icone = new ImageIcon(specialiste.getPhoto());
            Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            photo = new JLabel(new ImageIcon(img));
        } else {
            photo = new JLabel("👤");                          // icône par défaut
            photo.setFont(new Font("Arial", Font.PLAIN, 50));
            photo.setForeground(Color.WHITE);
        }
        photo.setHorizontalAlignment(SwingConstants.RIGHT);

        // conteneur droit avec le nom et la photo
        JPanel blocDroit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        blocDroit.setBackground(getBackground());
        blocDroit.add(infos);
        blocDroit.add(photo);

        // ajout du bloc droit et du logo
        add(blocDroit, BorderLayout.EAST);
        add(logo, BorderLayout.WEST);
    }
}
