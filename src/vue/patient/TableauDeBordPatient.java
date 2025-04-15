package vue.patient;

import modele.Utilisateur;
import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel affichant le tableau de bord du patient connecté.
 *
 * <p>Cette vue montre les prochains rendez-vous à venir
 * et les informations personnelles de l’utilisateur.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class TableauDeBordPatient extends JPanel {

    /**
     * Constructeur qui initialise et affiche le tableau de bord.
     *
     * @param fenetre la fenêtre principale de l’application
     * @param patient l’utilisateur patient connecté
     */
    public TableauDeBordPatient(JFrame fenetre, Utilisateur patient) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec logo et nom du patient
        add(new HeaderPatient(patient), BorderLayout.NORTH);

        // conteneur central en 2 colonnes
        JPanel contenu = new JPanel(new GridLayout(1, 2, 20, 20));
        contenu.setBackground(Color.decode("#f5f7fb"));
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- carte des prochains RDV ---
        JPanel cardRDV = new JPanel(new BorderLayout());
        cardRDV.setBackground(Color.WHITE);
        cardRDV.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // titre de la carte
        JLabel titreCard = new JLabel("📅 Vos prochains RDV", SwingConstants.CENTER);
        titreCard.setFont(new Font("Arial", Font.BOLD, 18));
        titreCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        cardRDV.add(titreCard, BorderLayout.NORTH);

        // liste des rdv
        JPanel listeRDV = new JPanel();
        listeRDV.setLayout(new BoxLayout(listeRDV, BoxLayout.Y_AXIS));
        listeRDV.setBackground(Color.WHITE);

        // récupérer les rdv futurs
        List<RendezVous> rdvs = ControleurRendezVous.getHistoriqueParPatient(patient.getId())
                .stream()
                .filter(r -> r.getDateHeure() != null && r.getDateHeure().isAfter(java.time.LocalDateTime.now()))
                .limit(5)
                .collect(Collectors.toList());

        if (!rdvs.isEmpty()) {
            for (RendezVous rdv : rdvs) {
                // panel pour chaque rdv
                JPanel rdvPanel = new JPanel(new GridLayout(3, 1));
                rdvPanel.setBackground(new Color(245, 247, 251));
                rdvPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                // récupérer le nom du spécialiste
                Utilisateur specialiste = UtilisateurDAO.getUtilisateurParId(rdv.getIdSpecialiste());
                String nomSpec = specialiste != null ? specialiste.getNom() : "[inconnu]";

                // labels rdv
                JLabel spec  = new JLabel("👨 Spécialiste : " + nomSpec);
                JLabel date  = new JLabel("📅 Date : "       + rdv.getDateHeure());
                JLabel motif = new JLabel("📌 Motif : "      + rdv.getMotif());
                spec.setFont(new Font("Arial", Font.PLAIN, 13));
                date.setFont(new Font("Arial", Font.PLAIN, 13));
                motif.setFont(new Font("Arial", Font.PLAIN, 13));

                // ajouter au panel
                rdvPanel.add(spec);
                rdvPanel.add(date);
                rdvPanel.add(motif);

                // espace entre les panels
                listeRDV.add(rdvPanel);
                listeRDV.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else {
            // message si aucun rdv
            JLabel aucun = new JLabel("Aucun rendez-vous à venir.");
            aucun.setFont(new Font("Arial", Font.PLAIN, 14));
            aucun.setHorizontalAlignment(SwingConstants.CENTER);
            listeRDV.add(aucun);
        }

        // scroll pour la liste
        JScrollPane scrollPane = new JScrollPane(listeRDV);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        cardRDV.add(scrollPane, BorderLayout.CENTER);

        // --- carte des infos personnelles ---
        JPanel cardInfos = new JPanel();
        cardInfos.setLayout(new BoxLayout(cardInfos, BoxLayout.Y_AXIS));
        cardInfos.setBackground(Color.WHITE);
        cardInfos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // titre infos
        JLabel titreInfos = new JLabel("👤 Informations personnelles");
        titreInfos.setFont(new Font("Arial", Font.BOLD, 18));
        titreInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardInfos.add(titreInfos);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));

        // création des labels info
        JLabel nom     = new JLabel("Nom : "    + patient.getNom());
        JLabel prenom  = new JLabel("Prénom : " + patient.getPrenom());
        JLabel mail    = new JLabel("Email : "  + patient.getEmail());
        JLabel age     = new JLabel("Âge : "    + patient.getAge());
        JLabel poids   = new JLabel("Poids : "  + patient.getPoids() + " kg");
        JLabel sexe    = new JLabel("Sexe : "   + patient.getSexe());
        JLabel adresse = new JLabel("Adresse : "+ patient.getAdresse());
        JLabel secu    = new JLabel("N° Sécu : " + patient.getNumeroSecu());
        JLabel phone   = new JLabel("Téléphone : " + patient.getTelephone());

        JLabel[] infos = { nom, prenom, mail, age, poids, sexe, adresse, secu, phone };
        for (JLabel info : infos) {
            info.setFont(new Font("Arial", Font.PLAIN, 14));
            info.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardInfos.add(info);
            cardInfos.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // --- boutons d’actions ---
        JButton changerMdp = new JButton("🔐 Modifier le mot de passe");
        changerMdp.setFont(new Font("Arial", Font.BOLD, 14));
        changerMdp.setAlignmentX(Component.CENTER_ALIGNMENT);
        changerMdp.addActionListener(e -> {
            String nouveau = JOptionPane.showInputDialog(this, "Entrez un nouveau mot de passe :");
            if (nouveau != null && !nouveau.trim().isEmpty()) {
                UtilisateurDAO.mettreAJourMotDePasse(patient.getId(), nouveau);
                JOptionPane.showMessageDialog(this, "Mot de passe mis à jour ✅");
            }
        });

        JButton changerPhoto = new JButton("Changer la photo de profil");
        changerPhoto.setFont(new Font("Arial", Font.BOLD, 14));
        changerPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        changerPhoto.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String chemin = fc.getSelectedFile().getAbsolutePath();
                patient.setPhoto(chemin);
                UtilisateurDAO.mettreAJourPhoto(patient.getId(), chemin);
                JOptionPane.showMessageDialog(this, "Photo mise à jour ✅");
                fenetre.setContentPane(new TableauDeBordPatient(fenetre, patient));
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        JButton modifierInfos = new JButton("Modifier mes informations");
        modifierInfos.setFont(new Font("Arial", Font.BOLD, 14));
        modifierInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        modifierInfos.addActionListener(e -> {
            // champs pré-remplis pour modif
        });

        // ajout des boutons sous les infos
        cardInfos.add(Box.createRigidArea(new Dimension(0, 20)));
        cardInfos.add(changerMdp);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));
        cardInfos.add(changerPhoto);
        cardInfos.add(Box.createRigidArea(new Dimension(0, 10)));
        cardInfos.add(modifierInfos);

        // assembler tout
        contenu.add(cardRDV);
        contenu.add(cardInfos);
        add(contenu, BorderLayout.CENTER);

        // footer patient
        add(new FooterPatient(fenetre, patient, "dashboard"), BorderLayout.SOUTH);
    }
}