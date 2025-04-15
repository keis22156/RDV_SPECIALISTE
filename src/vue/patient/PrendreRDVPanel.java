package vue.patient;

import dao.UtilisateurDAO;
import controleur.ControleurRendezVous;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel permettant au patient de choisir un spécialiste pour prendre un rendez-vous.
 *
 * <p>Affiche une liste de cartes de spécialistes avec photo, nom,
 * note moyenne et bouton pour consulter les créneaux disponibles.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class PrendreRDVPanel extends JPanel {

    /**
     * Constructeur qui initialise l'UI et affiche la liste des spécialistes.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param patient l'utilisateur patient connecté
     */
    public PrendreRDVPanel(JFrame fenetre, Utilisateur patient) {
        // layout en border et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec infos patient
        add(new HeaderPatient(patient), BorderLayout.NORTH);

        // titre de la page
        JLabel titre = new JLabel("Choisissez un spécialiste", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titre.setForeground(Color.decode("#333333"));

        // panel pour la liste des spécialistes
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.decode("#f5f7fb"));
        listPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // récupération des spécialistes depuis la base de données
        List<Utilisateur> specialistes = UtilisateurDAO.getTousLesSpecialistes();
        for (Utilisateur spec : specialistes) {
            listPanel.add(createSpecialisteCard(fenetre, patient, spec));
            listPanel.add(Box.createVerticalStrut(10));
        }

        // message si aucun spécialiste trouvé
        if (specialistes.isEmpty()) {
            JLabel aucun = new JLabel("Aucun spécialiste disponible.", SwingConstants.CENTER);
            aucun.setFont(new Font("Arial", Font.ITALIC, 14));
            aucun.setForeground(Color.GRAY);
            listPanel.add(Box.createVerticalGlue());
            listPanel.add(aucun);
            listPanel.add(Box.createVerticalGlue());
        }

        // scroll pour la liste
        JScrollPane scroll = new JScrollPane(
                listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.decode("#f5f7fb"));

        // centre avec titre et zone scrollable
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Color.decode("#f5f7fb"));
        centre.add(titre, BorderLayout.NORTH);
        centre.add(scroll, BorderLayout.CENTER);

        add(centre, BorderLayout.CENTER);

        // footer patient avec navigation vers prise de RDV
        add(new FooterPatient(fenetre, patient, "rdv"), BorderLayout.SOUTH);
    }

    /**
     * Crée une carte visuelle pour un spécialiste donné.
     *
     * @param fenetre    la fenêtre principale de l'application
     * @param patient    l'utilisateur patient connecté
     * @param spec       le spécialiste à afficher
     * @return un JPanel représentant la carte du spécialiste
     */
    private JPanel createSpecialisteCard(JFrame fenetre, Utilisateur patient, Utilisateur spec) {
        // calcul de la note moyenne des rendez-vous passés
        List<RendezVous> historiques = ControleurRendezVous.getHistoriqueParSpecialiste(spec.getId())
                .stream()
                .filter(r -> r.getNote() > 0)
                .collect(Collectors.toList());
        DoubleSummaryStatistics stats = historiques.stream()
                .collect(Collectors.summarizingDouble(RendezVous::getNote));
        String moyenneText = stats.getCount() > 0
                ? String.format("%.1f ★", stats.getAverage())
                : "Aucune";

        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // photo du spécialiste ou icône par défaut
        JLabel photoLabel;
        String chemin = spec.getPhoto();
        if (chemin != null && new File(chemin).exists()) {
            ImageIcon icon = new ImageIcon(chemin);
            Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            photoLabel = new JLabel(new ImageIcon(img));
        } else {
            photoLabel = new JLabel("👤");
            photoLabel.setFont(new Font("Arial", Font.PLAIN, 40));
            photoLabel.setForeground(Color.GRAY);
        }

        // nom et note moyenne
        String nomComplet = String.format("Dr. %s %s", spec.getPrenom(), spec.getNom());
        JLabel nomLabel = new JLabel(nomComplet);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nomLabel.setForeground(Color.decode("#333333"));

        JLabel moyenneLabel = new JLabel("Note moyenne : " + moyenneText);
        moyenneLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        moyenneLabel.setForeground(Color.GRAY);

        // bouton pour voir les créneaux disponibles
        JButton voirDispo = new JButton("📅 Voir les créneaux");
        voirDispo.setFont(new Font("Arial", Font.PLAIN, 14));
        voirDispo.setBackground(Color.decode("#007bff"));
        voirDispo.setForeground(Color.WHITE);
        voirDispo.setFocusPainted(false);
        voirDispo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        voirDispo.addActionListener(e -> {
            fenetre.setContentPane(new TableauCreneauxPanel(fenetre, patient, spec.getId()));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // assemblage des zones gauche, centre et droite
        JPanel gauche = new JPanel(new BorderLayout());
        gauche.setBackground(Color.WHITE);
        gauche.add(photoLabel, BorderLayout.CENTER);

        JPanel centreInfo = new JPanel();
        centreInfo.setLayout(new BoxLayout(centreInfo, BoxLayout.Y_AXIS));
        centreInfo.setBackground(Color.WHITE);
        centreInfo.add(nomLabel);
        centreInfo.add(Box.createVerticalStrut(5));
        centreInfo.add(moyenneLabel);

        JPanel droite = new JPanel(new BorderLayout());
        droite.setBackground(Color.WHITE);
        droite.add(voirDispo, BorderLayout.CENTER);

        card.add(gauche, BorderLayout.WEST);
        card.add(centreInfo, BorderLayout.CENTER);
        card.add(droite, BorderLayout.EAST);

        // clic sur la carte pour afficher les détails du spécialiste
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String infos = String.format(
                        "<html><body style='width:250px;'>" +
                                "<b>Prénom :</b> %s<br>" +
                                "<b>Nom :</b> %s<br>" +
                                "<b>Email :</b> %s<br>" +
                                "<b>Téléphone :</b> %s<br>" +
                                "<b>Adresse :</b> %s<br>" +
                                "<b>Spécialité :</b> %s<br>" +
                                "<b>Description :</b> %s<br>" +
                                "<b>Note moyenne :</b> %s" +
                                "</body></html>",
                        spec.getPrenom(),
                        spec.getNom(),
                        spec.getEmail(),
                        spec.getTelephone() != null ? spec.getTelephone() : "—",
                        spec.getAdresse() != null ? spec.getAdresse() : "—",
                        spec.getSpecialite() != null ? spec.getSpecialite() : "—",
                        spec.getDescription() != null ? spec.getDescription() : "—",
                        moyenneText
                );
                JOptionPane.showMessageDialog(
                        fenetre,
                        infos,
                        "Détails du spécialiste",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        return card;
    }
}