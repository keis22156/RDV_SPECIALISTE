package vue.specialiste;

import modele.Utilisateur;
import vue.Style.StyleGraphique;
import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class TableauDeBordSpecialiste extends JPanel {
    public TableauDeBordSpecialiste(JFrame fenetre, Utilisateur specialiste) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        JPanel contenu = new JPanel(new GridLayout(1, 2, 20, 20));
        contenu.setBackground(Color.decode("#f5f7fb"));
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel cardAgenda = new JPanel(new BorderLayout());
        cardAgenda.setBackground(Color.WHITE);
        cardAgenda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titreCard = new JLabel("📅 Prochains Rendez-vous", SwingConstants.CENTER);
        titreCard.setFont(new Font("Arial", Font.BOLD, 18));
        titreCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        cardAgenda.add(titreCard, BorderLayout.NORTH);

        JPanel listeRDV = new JPanel();
        listeRDV.setLayout(new BoxLayout(listeRDV, BoxLayout.Y_AXIS));
        listeRDV.setBackground(Color.WHITE);

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

                JLabel info = new JLabel("🧍 Patient : " + (patient != null ? patient.getNom() : "[inconnu]"));
                JLabel date = new JLabel("📅 Date : " + rdv.getDateHeure());
                JLabel motif = new JLabel("📌 Motif : " + rdv.getMotif());

                info.setFont(new Font("Arial", Font.PLAIN, 13));
                date.setFont(new Font("Arial", Font.PLAIN, 13));
                motif.setFont(new Font("Arial", Font.PLAIN, 13));

                rdvPanel.add(info);
                rdvPanel.add(date);
                rdvPanel.add(motif);

                listeRDV.add(rdvPanel);
                listeRDV.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else {
            JLabel aucun = new JLabel("Aucun rendez-vous à venir.");
            aucun.setFont(new Font("Arial", Font.PLAIN, 14));
            aucun.setHorizontalAlignment(SwingConstants.CENTER);
            listeRDV.add(aucun);
        }

        JScrollPane scrollPane = new JScrollPane(listeRDV);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        cardAgenda.add(scrollPane, BorderLayout.CENTER);

        JPanel cardInfos = new JPanel();
        cardInfos.setLayout(new BoxLayout(cardInfos, BoxLayout.Y_AXIS));
        cardInfos.setBackground(Color.WHITE);
        cardInfos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titreInfos = new JLabel("👤 Informations personnelles");
        titreInfos.setFont(new Font("Arial", Font.BOLD, 18));
        titreInfos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nom = new JLabel("Nom : " + specialiste.getNom());
        JLabel mail = new JLabel("Email : " + specialiste.getEmail());

        nom.setFont(new Font("Arial", Font.PLAIN, 14));
        mail.setFont(new Font("Arial", Font.PLAIN, 14));

        nom.setAlignmentX(Component.CENTER_ALIGNMENT);
        mail.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton changerMdp = new JButton("🔐 Modifier le mot de passe");
        changerMdp.setFont(new Font("Arial", Font.BOLD, 14));
        changerMdp.setAlignmentX(Component.CENTER_ALIGNMENT);
        changerMdp.addActionListener(e -> {
            String nouveau = JOptionPane.showInputDialog(this, "Entrez un nouveau mot de passe :");
            if (nouveau != null && !nouveau.trim().isEmpty()) {
                UtilisateurDAO.mettreAJourMotDePasse(specialiste.getId(), nouveau);
                JOptionPane.showMessageDialog(this, "Mot de passe mis à jour ✅");
            }
        });

        JButton changerPhoto = new JButton("Changer la photo de profil");
        changerPhoto.setFont(new Font("Arial", Font.BOLD, 14));
        changerPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        changerPhoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int resultat = fileChooser.showOpenDialog(this);
            if (resultat == JFileChooser.APPROVE_OPTION) {
                String chemin = fileChooser.getSelectedFile().getAbsolutePath();
                specialiste.setPhoto(chemin);
                UtilisateurDAO.mettreAJourPhoto(specialiste.getId(), chemin);
                JOptionPane.showMessageDialog(this, "Photo mise à jour ✅");
                fenetre.setContentPane(new TableauDeBordSpecialiste(fenetre, specialiste));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        cardInfos.add(titreInfos);
        cardInfos.add(nom);
        cardInfos.add(mail);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 20)));
        cardInfos.add(changerMdp);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));
        cardInfos.add(changerPhoto);

        contenu.add(cardAgenda);
        contenu.add(cardInfos);

        add(contenu, BorderLayout.CENTER);
        add(new FooterSpecialiste(fenetre, specialiste, "accueil"), BorderLayout.SOUTH);
    }
}
