package vue.admin;

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
 * AgendaSpecialisteAdminPanel affiche l'agenda hebdomadaire d'un spécialiste pour l'administrateur.
 *
 * <p>Permet de visualiser les créneaux pris, passés et libres,
 * et d'annuler ou de bloquer des créneaux selon les droits.</p>
 *
 * @author Keis <keisaissaoui2@gmail.com>
 */
public class AgendaSpecialisteAdminPanel extends JPanel {

    /**
     * Décalage en semaines pour la navigation dans l'agenda.
     */
    private int semaineOffset = 0;

    /**
     * Constructeur qui initialise et affiche l'agenda du spécialiste.
     *
     * @param fenetre      la fenêtre principale de l'application
     * @param admin        l'administrateur connecté
     * @param specialiste  le spécialiste dont on affiche l'agenda
     */
    public AgendaSpecialisteAdminPanel(JFrame fenetre, Utilisateur admin, Utilisateur specialiste) {
        afficherAgenda(fenetre, admin, specialiste);
    }

    /**
     * Construit et rafraîchit l'affichage de l'agenda avec les boutons d'action.
     *
     * @param fenetre      la fenêtre principale
     * @param admin        l'administrateur connecté
     * @param specialiste  le spécialiste sélectionné
     */
    private void afficherAgenda(JFrame fenetre, Utilisateur admin, Utilisateur specialiste) {
        // 1. Nettoyage et configuration de base
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // 2. Récupération des rendez-vous du spécialiste
        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(specialiste.getId());

        // 3. Création du tableau 8 colonnes : heure + 7 jours
        JPanel tableau = new JPanel(new GridLayout(10, 8));
        tableau.setBackground(Color.WHITE);
        tableau.add(new JLabel("Heure / Date", SwingConstants.CENTER));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        for (int i = 0; i < 7; i++) {
            LocalDateTime jour = LocalDateTime.now().plusWeeks(semaineOffset).plusDays(i);
            tableau.add(new JLabel(jour.format(dateFormatter), SwingConstants.CENTER));
        }

        // 4. Remplissage des lignes horaires de 9h à 17h
        for (int h = 9; h <= 17; h++) {
            tableau.add(new JLabel(h + ":00", SwingConstants.CENTER));
            for (int j = 0; j < 7; j++) {
                LocalDateTime cible = LocalDateTime.now()
                        .plusWeeks(semaineOffset)
                        .plusDays(j)
                        .withHour(h)
                        .withMinute(0);
                LocalDateTime maintenant = LocalDateTime.now();

                // Recherche d'un RDV à ce créneau précis
                RendezVous rdv = rdvs.stream()
                        .filter(r -> r.getDateHeure().withSecond(0).withNano(0)
                                .equals(cible.withSecond(0).withNano(0)))
                        .findFirst()
                        .orElse(null);

                JButton bouton = new JButton();

                if (rdv != null) {
                    // Créneau occupé par un rendez-vous
                    bouton.setBackground(new Color(200, 0, 0));
                    bouton.setText("RDV");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        // Affichage des détails et option d'annulation
                        Utilisateur patient = UtilisateurDAO.getUtilisateurParId(rdv.getIdPatient());
                        String details = "Nom : " + patient.getNom()
                                + "\nEmail : " + patient.getEmail()
                                + "\nDate : " + rdv.getDateHeure()
                                + "\nMotif : " + rdv.getMotif();
                        if (rdv.getDateHeure().isAfter(LocalDateTime.now())) {
                            int confirm = JOptionPane.showConfirmDialog(fenetre,
                                    details + "\n\nSouhaitez-vous annuler ce rendez-vous ?",
                                    "Annulation du RDV",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                ControleurRendezVous.annulerRendezVous(rdv.getId());
                                JOptionPane.showMessageDialog(fenetre, "Rendez-vous annulé ❌");
                                afficherAgenda(fenetre, admin, specialiste);
                                fenetre.revalidate();
                                fenetre.repaint();
                            }
                        } else {
                            JOptionPane.showMessageDialog(fenetre, details, "Détail du RDV", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else if (cible.toLocalDate().equals(maintenant.toLocalDate()) && cible.isBefore(maintenant)) {
                    // Créneau déjà passé aujourd'hui
                    bouton.setBackground(new Color(120, 0, 0));
                    bouton.setText("PASSÉ");
                    bouton.setForeground(Color.WHITE);
                } else {
                    // Créneau libre -> possibilité de bloquer
                    bouton.setBackground(new Color(0, 200, 0));
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
                                    "créneau bloqué par l'admin"
                            );
                            if (ok) {
                                JOptionPane.showMessageDialog(fenetre, "Créneau bloqué avec succès ✅");
                                afficherAgenda(fenetre, admin, specialiste);
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

        // 5. Construction du panneau central avec scroll et navigation
        JPanel centre = new JPanel(new BorderLayout());
        centre.add(new JScrollPane(tableau), BorderLayout.CENTER);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton precedent = new JButton("< Semaine précédente");
        JButton suivant   = new JButton("Semaine suivante >");

        precedent.setEnabled(semaineOffset > 0);
        precedent.addActionListener(e -> {
            semaineOffset--;
            afficherAgenda(fenetre, admin, specialiste);
            fenetre.revalidate();
            fenetre.repaint();
        });
        suivant.addActionListener(e -> {
            semaineOffset++;
            afficherAgenda(fenetre, admin, specialiste);
            fenetre.revalidate();
            fenetre.repaint();
        });

        navigation.add(precedent);
        navigation.add(suivant);
        centre.add(navigation, BorderLayout.SOUTH);

        add(centre, BorderLayout.CENTER);
        add(new FooterAdmin(fenetre, admin, "specialistes"), BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}