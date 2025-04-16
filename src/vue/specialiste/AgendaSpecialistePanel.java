package vue.specialiste;

import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel affichant l'agenda hebdomadaire du spécialiste connecté.
 *
 * <p>Affiche une grille horaire avec 7 jours et des créneaux
 * indiquant si un rendez-vous est pris, passé ou libre.
 * Permet de naviguer entre les semaines.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class AgendaSpecialistePanel extends JPanel {
    private int semaineOffset = 0; // pour naviguer entre les semaines

    /**
     * Constructeur : initialise et affiche l'agenda.
     *
     * @param fenetre     la fenêtre principale de l'application
     * @param specialiste l'utilisateur spécialiste connecté
     */
    public AgendaSpecialistePanel(JFrame fenetre, Utilisateur specialiste) {
        afficherAgenda(fenetre, specialiste); // lance l’affichage dès l’instanciation
    }

    /**
     * Construit ou reconstruit l'affichage de l'agenda en fonction
     * de l'offset de semaine.
     *
     * @param fenetre     la fenêtre principale de l'application
     * @param specialiste l'utilisateur spécialiste connecté
     */
    private void afficherAgenda(JFrame fenetre, Utilisateur specialiste) {
        removeAll();                   // vide l’ancien contenu
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // header en haut
        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        // récupération de tous les rdv du spécialiste
        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(specialiste.getId());

        // tableau 8 colonnes : heure + 7 jours
        JPanel tableau = new JPanel(new GridLayout(10, 8));
        tableau.setBackground(Color.WHITE);
        tableau.add(new JLabel("Heure / Date", SwingConstants.CENTER));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        // en-têtes de colonnes pour chaque jour de la semaine
        for (int i = 0; i < 7; i++) {
            LocalDateTime jour = LocalDateTime.now()
                    .plusWeeks(semaineOffset)
                    .plusDays(i);
            tableau.add(new JLabel(jour.format(dateFormatter), SwingConstants.CENTER));
        }

        // lignes horaires de 9h à 17h
        for (int h = 9; h <= 17; h++) {
            tableau.add(new JLabel(h + ":00", SwingConstants.CENTER));
            for (int j = 0; j < 7; j++) {
                LocalDateTime cible = LocalDateTime.now()
                        .plusWeeks(semaineOffset)
                        .plusDays(j)
                        .withHour(h)
                        .withMinute(0);
                LocalDateTime maintenant = LocalDateTime.now();

                // cherche un rdv à cette date/heure
                RendezVous rdv = rdvs.stream()
                        .filter(r -> r.getDateHeure()
                                .withSecond(0)
                                .withNano(0)
                                .equals(cible.withSecond(0).withNano(0)))
                        .findFirst()
                        .orElse(null);

                JButton bouton = new JButton();

                if (rdv != null) {
                    // créneau occupé par un rdv
                    bouton.setBackground(new Color(200, 0, 0)); // rouge vif
                    bouton.setText("RDV");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        Utilisateur patient = UtilisateurDAO.getUtilisateurParId(rdv.getIdPatient());
                        JOptionPane.showMessageDialog(fenetre,
                                "Nom : " + patient.getNom() +
                                        "\nEmail : " + patient.getEmail() +
                                        "\nDate : " + rdv.getDateHeure() +
                                        "\nMotif : " + rdv.getMotif(),
                                "Détail du RDV",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                } else if (cible.toLocalDate().equals(maintenant.toLocalDate())
                        && cible.isBefore(maintenant)) {
                    // créneau déjà passé aujourd’hui
                    bouton.setBackground(new Color(120, 0, 0)); // rouge sombre
                    bouton.setText("PASSÉ");
                    bouton.setForeground(Color.WHITE);
                } else {
                    // créneau libre
                    bouton.setBackground(new Color(0, 200, 0)); // vert
                    bouton.setText("LIBRE");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        int confirmation = JOptionPane.showConfirmDialog(fenetre,
                                "Souhaitez-vous bloquer ce créneau ?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        if (confirmation == JOptionPane.YES_OPTION) {
                            boolean ok = ControleurRendezVous.reserverCreneau(
                                    cible,
                                    specialiste.getId(),
                                    specialiste.getId(),
                                    "créneau bloqué par le spécialiste"
                            );
                            if (ok) {
                                JOptionPane.showMessageDialog(fenetre, "Créneau bloqué avec succès ✅");
                                // recharge l’agenda
                                fenetre.setContentPane(new AgendaSpecialistePanel(fenetre, specialiste));
                                fenetre.revalidate();
                                fenetre.repaint();
                            } else {
                                JOptionPane.showMessageDialog(fenetre,
                                        "Erreur lors du blocage du créneau ❌",
                                        "Erreur",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
                tableau.add(bouton);
            }
        }

        // panneau central avec scroll
        JPanel centre = new JPanel(new BorderLayout());
        centre.add(new JScrollPane(tableau), BorderLayout.CENTER);

        // navigation de semaine précédente / suivante
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton precedent = new JButton("< Semaine précédente");
        JButton suivant   = new JButton("Semaine suivante >");

        precedent.setEnabled(semaineOffset > 0); // désactivé si on est sur la semaine 0

        precedent.addActionListener(e -> {
            if (semaineOffset > 0) {
                semaineOffset--;
                afficherAgenda(fenetre, specialiste);
                fenetre.revalidate();
                fenetre.repaint();
            }
        });

        suivant.addActionListener(e -> {
            semaineOffset++;
            afficherAgenda(fenetre, specialiste);
            fenetre.revalidate();
            fenetre.repaint();
        });

        navigation.add(precedent);
        navigation.add(suivant);
        centre.add(navigation, BorderLayout.SOUTH);

        add(centre, BorderLayout.CENTER);
        // footer en bas
        add(new FooterSpecialiste(fenetre, specialiste, "agenda"), BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}