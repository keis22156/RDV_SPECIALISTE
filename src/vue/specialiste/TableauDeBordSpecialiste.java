package vue.specialiste;

import modele.Utilisateur;
import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Affiche le tableau de bord du spécialiste avec agenda et informations personnelles.
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class TableauDeBordSpecialiste extends JPanel {

    /**
     * Constructeur qui initialise la vue du tableau de bord.
     *
     * @param fenetre     la fenêtre principale de l'application
     * @param specialiste l'utilisateur spécialiste connecté
     */
    public TableauDeBordSpecialiste(JFrame fenetre, Utilisateur specialiste) {
        // layout principal et couleur de fond
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec nom du spécialiste
        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        // panneau central en grid pour agenda et infos
        JPanel contenu = new JPanel(new GridLayout(1, 2, 20, 20));
        contenu.setBackground(Color.decode("#f5f7fb"));
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // création de la carte agenda
        JPanel cardAgenda = new JPanel(new BorderLayout());
        cardAgenda.setBackground(Color.WHITE);
        cardAgenda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // titre agenda
        JLabel titreAgenda = new JLabel("Prochains Rendez-vous", SwingConstants.CENTER);
        titreAgenda.setFont(new Font("Arial", Font.BOLD, 18));
        titreAgenda.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        cardAgenda.add(titreAgenda, BorderLayout.NORTH);

        // liste des rendez-vous
        JPanel listeRDV = new JPanel();
        listeRDV.setLayout(new BoxLayout(listeRDV, BoxLayout.Y_AXIS));
        listeRDV.setBackground(Color.WHITE);

        // récupération des 5 prochains rendez-vous
        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(specialiste.getId())
                .stream()
                .filter(r -> r.getDateHeure() != null && r.getIdPatient() != specialiste.getId())
                .limit(5)
                .collect(Collectors.toList());

        if (!rdvs.isEmpty()) {
            for (RendezVous rdv : rdvs) {
                Utilisateur patient = UtilisateurDAO.getUtilisateurParId(rdv.getIdPatient());

                JPanel rdvPanel = new JPanel(new GridLayout(3, 1));
                rdvPanel.setBackground(new Color(245, 247, 251));
                rdvPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                JLabel labelPatient = new JLabel("Patient : "
                        + (patient != null ? patient.getPrenom() + " " + patient.getNom() : "[inconnu]"));
                JLabel labelDate    = new JLabel("Date : " + rdv.getDateHeure());
                JLabel labelMotif   = new JLabel("Motif : " + rdv.getMotif());

                labelPatient.setFont(new Font("Arial", Font.PLAIN, 13));
                labelDate.setFont   (new Font("Arial", Font.PLAIN, 13));
                labelMotif.setFont  (new Font("Arial", Font.PLAIN, 13));

                rdvPanel.add(labelPatient);
                rdvPanel.add(labelDate);
                rdvPanel.add(labelMotif);

                // clic sur rdv pour afficher détails
                rdvPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                rdvPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        JTextArea informations = new JTextArea(
                                "Patient : " + patient.getPrenom() + " " + patient.getNom() + "\n" +
                                        "Email   : " + patient.getEmail() + "\n" +
                                        "Tél.    : " + patient.getTelephone() + "\n" +
                                        "Adresse : " + patient.getAdresse() + "\n\n" +
                                        "Date    : " + rdv.getDateHeure() + "\n" +
                                        "Motif   : " + rdv.getMotif() + "\n" +
                                        "⭐ Note    : " + (rdv.getNote()>0? rdv.getNote():"Non noté")
                        );
                        informations.setEditable(false);
                        informations.setFont(new Font("Arial", Font.PLAIN, 14));
                        JOptionPane.showMessageDialog(
                                fenetre,
                                new JScrollPane(informations),
                                "Détails du rendez-vous",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                });

                listeRDV.add(rdvPanel);
                listeRDV.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else {
            JLabel aucun = new JLabel("Aucun rendez-vous à venir.", SwingConstants.CENTER);
            aucun.setFont(new Font("Arial", Font.PLAIN, 14));
            listeRDV.add(aucun);
        }

        JScrollPane scrollAgenda = new JScrollPane(listeRDV);
        scrollAgenda.setBorder(null);
        scrollAgenda.getVerticalScrollBar().setUnitIncrement(16);
        cardAgenda.add(scrollAgenda, BorderLayout.CENTER);

        // carte infos personnelles
        JPanel cardInfos = new JPanel();
        cardInfos.setLayout(new BoxLayout(cardInfos, BoxLayout.Y_AXIS));
        cardInfos.setBackground(Color.WHITE);
        cardInfos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titreInfos = new JLabel("Mes Informations");
        titreInfos.setFont(new Font("Arial", Font.BOLD, 18));
        titreInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardInfos.add(titreInfos);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] infos = {
                "Prénom     : " + specialiste.getPrenom(),
                "Nom        : " + specialiste.getNom(),
                "Email      : " + specialiste.getEmail(),
                "Téléphone  : " + specialiste.getTelephone(),
                "Adresse    : " + specialiste.getAdresse(),
                "Spécialité : " + specialiste.getSpecialite(),
                "Description: " + specialiste.getDescription()
        };
        for (String ligne : infos) {
            JLabel lbl = new JLabel(ligne);
            lbl.setFont(new Font("Arial", Font.PLAIN, 14));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardInfos.add(lbl);
            cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton btnModifier = new JButton("Modifier mes informations");
        btnModifier.setFont(new Font("Arial", Font.BOLD, 14));
        btnModifier.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnModifier.addActionListener(e -> {
            // champs pré-remplis pour modification
        });
        cardInfos.add(btnModifier);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));

        // ajout des cartes
        contenu.add(cardAgenda);
        contenu.add(cardInfos);

        add(contenu, BorderLayout.CENTER);
        add(new FooterSpecialiste(fenetre, specialiste, "accueil"), BorderLayout.SOUTH);
    }
}