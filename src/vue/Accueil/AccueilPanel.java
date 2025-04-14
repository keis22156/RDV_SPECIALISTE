package vue.Accueil;
import vue.Style.StyleGraphique;
import javax.swing.*;
import java.awt.*;

public class AccueilPanel extends JPanel {
    public AccueilPanel(JFrame fenetre) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        add(new HeaderAccueil(), BorderLayout.NORTH);

        JPanel centre = new JPanel();
        centre.setBackground(Color.decode("#f5f7fb"));
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Bienvenue sur votre espace de prise de rendez-vous en ligne");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setFont(new Font("Arial", Font.BOLD, 20));
        message.setBorder(BorderFactory.createEmptyBorder(50, 10, 30, 10));
        message.setForeground(Color.DARK_GRAY);

        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        boutonsPanel.setBackground(Color.decode("#f5f7fb"));

        JButton boutonConnexion = new JButton("Connexion");
        JButton boutonInscription = new JButton("Inscription");

        StyleGraphique.styliserBoutonPrincipal(boutonConnexion);
        StyleGraphique.styliserBoutonSecondaire(boutonInscription);

        boutonsPanel.add(boutonConnexion);
        boutonsPanel.add(boutonInscription);

        centre.add(message);
        centre.add(boutonsPanel);

        add(centre, BorderLayout.CENTER);

        JPanel bas = new JPanel(new BorderLayout());
        bas.setBackground(Color.decode("#f5f7fb"));
        JLabel credits = new JLabel("2025 Keis Aissaoui, Nicolas Chaudemanche");
        credits.setFont(new Font("Arial", Font.ITALIC, 12));
        credits.setForeground(Color.GRAY);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        bas.add(credits, BorderLayout.EAST);

        add(bas, BorderLayout.SOUTH);

        boutonConnexion.addActionListener(e -> {
            fenetre.setContentPane(new ConnexionPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });

        boutonInscription.addActionListener(e -> {
            fenetre.setContentPane(new InscriptionPanel(fenetre));
            fenetre.revalidate();
            fenetre.repaint();
        });
    }
}
