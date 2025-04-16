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

public class AgendaSpecialistePanel extends JPanel {
    private int semaineOffset = 0;

    public AgendaSpecialistePanel(JFrame fenetre, Utilisateur specialiste) {
        afficherAgenda(fenetre, specialiste);
    }

    private void afficherAgenda(JFrame fenetre, Utilisateur specialiste) {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        JLabel titre = new JLabel("🗓️ Mon agenda (créneaux réservés en rouge)", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(specialiste.getId());

        JPanel tableau = new JPanel(new GridLayout(10, 8));
        tableau.setBackground(Color.WHITE);
        tableau.add(new JLabel("Heure / Date", SwingConstants.CENTER));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

        for (int i = 0; i < 7; i++) {
            LocalDateTime jour = LocalDateTime.now().plusWeeks(semaineOffset).plusDays(i);
            tableau.add(new JLabel(jour.format(dateFormatter), SwingConstants.CENTER));
        }

        for (int h = 9; h <= 17; h++) {
            tableau.add(new JLabel(h + ":00", SwingConstants.CENTER));
            for (int j = 0; j < 7; j++) {
                LocalDateTime cible = LocalDateTime.now().plusWeeks(semaineOffset).plusDays(j).withHour(h).withMinute(0);
                LocalDateTime maintenant = LocalDateTime.now();

                RendezVous rdv = rdvs.stream()
                        .filter(r -> r.getDateHeure().withSecond(0).withNano(0).equals(cible.withSecond(0).withNano(0)))
                        .findFirst()
                        .orElse(null);

                JButton bouton = new JButton();

                if (rdv != null) {
                    bouton.setBackground(new Color(200, 0, 0));
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
                } else if (cible.toLocalDate().equals(maintenant.toLocalDate()) && cible.isBefore(maintenant)) {
                    // Créneau du jour mais déjà passé
                    bouton.setBackground(new Color(120, 0, 0));
                    bouton.setText("PASSÉ");
                    bouton.setForeground(Color.WHITE);
                } else {
                    bouton.setBackground(new Color(0, 200, 0));
                    bouton.setText("LIBRE");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        int confirmation = JOptionPane.showConfirmDialog(fenetre,
                                "Souhaitez-vous bloquer ce créneau ?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        if (confirmation == JOptionPane.YES_OPTION) {
                            boolean ok = ControleurRendezVous.reserverCreneau(cible, specialiste.getId(), specialiste.getId(),
                                    "créneau bloqué par le spécialiste");
                            if (ok) {
                                JOptionPane.showMessageDialog(fenetre, "Créneau bloqué avec succès ✅");
                                fenetre.setContentPane(new AgendaSpecialistePanel(fenetre, specialiste));
                                fenetre.revalidate();
                                fenetre.repaint();
                            } else {
                                JOptionPane.showMessageDialog(fenetre, "Erreur lors du blocage du créneau ❌", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
                tableau.add(bouton);
            }
        }

        JPanel centre = new JPanel(new BorderLayout());
        centre.add(new JScrollPane(tableau), BorderLayout.CENTER);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton precedent = new JButton("< Semaine précédente");
        JButton suivant = new JButton("Semaine suivante >");

        precedent.setEnabled(semaineOffset > 0);

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
        add(new FooterSpecialiste(fenetre, specialiste, "agenda"), BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
