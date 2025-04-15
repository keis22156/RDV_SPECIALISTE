package vue.patient;

import controleur.ControleurRendezVous;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Affiche la grille des créneaux disponibles pour prise de rendez-vous.
 *
 * <p>Présente un calendrier hebdomadaire de 9h à 17h, indiquant
 * si chaque créneau est passé, disponible ou déjà pris.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class TableauCreneauxPanel extends JPanel {
    private int semaineOffset = 0; // permet de changer de semaine

    /**
     * Constructeur qui initialise et affiche la grille des créneaux.
     *
     * @param fenetre       la fenêtre principale de l'application
     * @param patient       l'utilisateur patient connecté
     * @param idSpecialiste l'identifiant du spécialiste ciblé
     */
    public TableauCreneauxPanel(JFrame fenetre, Utilisateur patient, int idSpecialiste) {
        afficherGrille(fenetre, patient, idSpecialiste);
    }

    /**
     * Construit ou reconstruit la grille des créneaux selon l'offset de semaine.
     *
     * @param fenetre       la fenêtre principale de l'application
     * @param patient       l'utilisateur patient connecté
     * @param idSpecialiste l'identifiant du spécialiste ciblé
     */
    private void afficherGrille(JFrame fenetre, Utilisateur patient, int idSpecialiste) {
        removeAll(); // vide l'ancien contenu
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // header avec les infos patient
        add(new HeaderPatient(patient), BorderLayout.NORTH);

        // récupère les rendez-vous du spécialiste
        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(idSpecialiste);

        // création de la grille : 8 colonnes (heure + 7 jours)
        JPanel tableau = new JPanel(new GridLayout(10, 8));
        tableau.setBackground(Color.WHITE);
        tableau.add(new JLabel("Heure / Date", SwingConstants.CENTER));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        for (int i = 0; i < 7; i++) {
            LocalDateTime jour = LocalDateTime.now()
                    .plusWeeks(semaineOffset)
                    .plusDays(i);
            tableau.add(new JLabel(jour.format(dateFormatter), SwingConstants.CENTER));
        }

        // lignes horaires
        for (int h = 9; h <= 17; h++) {
            tableau.add(new JLabel(h + ":00", SwingConstants.CENTER));
            for (int j = 0; j < 7; j++) {
                LocalDateTime cible = LocalDateTime.now()
                        .plusWeeks(semaineOffset)
                        .plusDays(j)
                        .withHour(h)
                        .withMinute(0);
                LocalDateTime maintenant = LocalDateTime.now();

                // vérifie si le créneau est déjà pris
                boolean dejaPris = rdvs.stream()
                        .anyMatch(r -> r.getDateHeure()
                                .withSecond(0)
                                .withNano(0)
                                .equals(cible.withSecond(0).withNano(0)));

                JButton bouton = new JButton();

                if (cible.isBefore(maintenant)) {
                    // créneau déjà passé
                    bouton.setBackground(new Color(120, 0, 0));
                    bouton.setText("PASSÉ");
                    bouton.setForeground(Color.WHITE);
                } else if (!dejaPris) {
                    // créneau libre
                    bouton.setBackground(new Color(0, 200, 0));
                    bouton.setText("DISPO");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        String motif = demanderMotif();
                        if (motif != null && !motif.isEmpty()) {
                            boolean ok = ControleurRendezVous.reserverCreneau(
                                    cible, patient.getId(), idSpecialiste, motif);
                            if (ok) {
                                JOptionPane.showMessageDialog(fenetre, "Rendez-vous réservé ✅");
                                fenetre.setContentPane(new TableauCreneauxPanel(fenetre, patient, idSpecialiste));
                                fenetre.revalidate();
                                fenetre.repaint();
                            }
                        }
                    });
                } else {
                    // créneau indisponible
                    bouton.setBackground(new Color(200, 0, 0));
                    bouton.setText("INDISPO");
                    bouton.setForeground(Color.WHITE);
                }
                tableau.add(bouton);
            }
        }

        // panneau central avec scroll
        JPanel centre = new JPanel(new BorderLayout());
        centre.add(new JScrollPane(tableau), BorderLayout.CENTER);

        // navigation entre semaines
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton precedent = new JButton("< Semaine précédente");
        JButton suivant = new JButton("Semaine suivante >");

        precedent.setEnabled(semaineOffset > 0);
        precedent.addActionListener(e -> {
            if (semaineOffset > 0) {
                semaineOffset--;
                afficherGrille(fenetre, patient, idSpecialiste);
                fenetre.revalidate();
                fenetre.repaint();
            }
        });
        suivant.addActionListener(e -> {
            semaineOffset++;
            afficherGrille(fenetre, patient, idSpecialiste);
            fenetre.revalidate();
            fenetre.repaint();
        });
        navigation.add(precedent);
        navigation.add(suivant);
        centre.add(navigation, BorderLayout.SOUTH);

        add(centre, BorderLayout.CENTER);
        // footer patient
        add(new FooterPatient(fenetre, patient, "rdv"), BorderLayout.SOUTH);
    }

    /**
     * Ouvre une boîte pour choisir le motif du rendez-vous.
     *
     * @return le motif sélectionné ou saisi, ou null si annulé
     */
    private String demanderMotif() {
        String[] options = {"Urgence", "Visite de routine", "Demande d'arrêt maladie", "Suivi", "Autre"};
        String choix = (String) JOptionPane.showInputDialog(
                null,
                "Quel est le motif de votre RDV ?",
                "Motif du rendez-vous",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if ("Autre".equals(choix)) {
            return JOptionPane.showInputDialog(
                    null,
                    "Veuillez indiquer votre motif :",
                    "Motif personnalisé",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
        return choix;
    }
}