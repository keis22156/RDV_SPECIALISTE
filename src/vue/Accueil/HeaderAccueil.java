// HeaderAccueil.java affiche la bannière de la page d’accueil

package vue.Accueil;

import javax.swing.*;
import java.awt.*;

public class HeaderAccueil extends JPanel {
    public HeaderAccueil() {
        // layout en border et fond vert sombre
        setLayout(new BorderLayout());
        setBackground(new Color(26, 57, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // création du logo à gauche
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/Users/keisaissaoui/Documents/RDVSPECIALISTE/src/images/logoaccueil.png"); // chemin à adapter si besoin
        // on redimensionne l’image en hauteur à 60 px
        Image image = logoIcon.getImage().getScaledInstance(-1, 60, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(image));

        // conteneur à gauche pour le logo
        JPanel blocGauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        blocGauche.setBackground(getBackground());
        blocGauche.add(logo);

        // on ajoute le bloc gauche au panel principal
        add(blocGauche, BorderLayout.WEST);
    }
}