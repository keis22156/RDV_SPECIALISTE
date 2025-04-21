package vue.admin;

import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * HeaderAdmin affiche la bannière en haut des écrans de l'administrateur.
 *
 * <p>Affiche le logo à gauche, le nom de l'admin et sa photo ou une icône par défaut à droite.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class HeaderAdmin extends JPanel {

    /**
     * Constructeur qui initialise le header avec les informations de l'admin.
     *
     * @param admin l'objet Utilisateur représentant l'administrateur connecté
     */
    public HeaderAdmin(Utilisateur admin) {
        // layout en border et fond noir
        setLayout(new BorderLayout());
        setBackground(Color.decode("#000000"));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // logo à gauche
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/Documents/RDVSPECIALISTE/logoadmin.png");
        Image image = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        // nom de l’admin
        JLabel nom = new JLabel(admin.getNom());
        nom.setFont(new Font("Arial", Font.PLAIN, 20));
        nom.setForeground(Color.LIGHT_GRAY);

        // panel pour le nom
        JPanel infos = new JPanel();
        infos.setLayout(new BoxLayout(infos, BoxLayout.Y_AXIS));
        infos.setBackground(getBackground());
        infos.add(nom);

        // photo à droite ou icône par défaut si pas de fichier trouvé
        JLabel photo;
        if (admin.getPhoto() != null && new File(admin.getPhoto()).exists()) {
            ImageIcon icone = new ImageIcon(admin.getPhoto());
            Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            photo = new JLabel(new ImageIcon(img));
        } else {
            photo = new JLabel("👤");
            photo.setFont(new Font("Arial", Font.PLAIN, 50));
            photo.setForeground(Color.WHITE);
        }
        photo.setHorizontalAlignment(SwingConstants.RIGHT);

        // panel droit contenant le nom et la photo
        JPanel blocDroit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        blocDroit.setBackground(getBackground());
        blocDroit.add(infos);
        blocDroit.add(photo);

        // ajout des composants au panel
        add(blocDroit, BorderLayout.EAST);
        add(logo, BorderLayout.WEST);
    }
}