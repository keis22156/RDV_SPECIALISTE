package vue.patient;

import controleur.ControleurRendezVous;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableauCreneauxPanel extends JPanel {
    private int semaineOffset = 0;

    public TableauCreneauxPanel(JFrame fenetre, Utilisateur patient, int idSpecialiste) {
        afficherGrille(fenetre, patient, idSpecialiste);
    }

    private void afficherGrille(JFrame fenetre, Utilisateur patient, int idSpecialiste) {
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new HeaderPatient(patient), BorderLayout.NORTH);

        JLabel titre = new JLabel("📆 Créneaux disponibles", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        List<RendezVous> rdvs = ControleurRendezVous.getRendezVousParSpecialiste(idSpecialiste);

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

                boolean dejaPris = rdvs.stream()
                        .anyMatch(r -> r.getDateHeure().withSecond(0).withNano(0).equals(cible.withSecond(0).withNano(0)));

                JButton bouton = new JButton();

                if (cible.isBefore(maintenant)) {
                    bouton.setBackground(new Color(120, 0, 0));
                    bouton.setText("PASSÉ");
                    bouton.setForeground(Color.WHITE);
                } else if (!dejaPris) {
                    bouton.setBackground(new Color(0, 200, 0));
                    bouton.setText("DISPO");
                    bouton.setForeground(Color.WHITE);
                    bouton.addActionListener(e -> {
                        String motif = demanderMotif();
                        if (motif != null && !motif.isEmpty()) {
                            boolean ok = ControleurRendezVous.reserverCreneau(cible, patient.getId(), idSpecialiste, motif);
                            if (ok) {
                                JOptionPane.showMessageDialog(fenetre, "Rendez-vous réservé ✅");
                                fenetre.setContentPane(new TableauCreneauxPanel(fenetre, patient, idSpecialiste));
                                fenetre.revalidate();
                                fenetre.repaint();
                            }
                        }
                    });
                } else {
                    bouton.setBackground(new Color(200, 0, 0));
                    bouton.setText("INDISPO");
                    bouton.setForeground(Color.WHITE);
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

        JButton retour = new JButton("⬅ Retour");
        retour.addActionListener(e -> {
            fenetre.setContentPane(new PrendreRDVPanel(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });
        add(new FooterPatient(fenetre, patient, "rdv"), BorderLayout.SOUTH);
    }

    private String demanderMotif() {
        String[] options = {"Urgence", "Visite de routine", "Demande d'arrêt maladie", "Suivi", "Autre"};
        String choix = (String) JOptionPane.showInputDialog(null,
                "Quel est le motif de votre RDV ?",
                "Motif du rendez-vous",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (choix != null && choix.equals("Autre")) {
            return JOptionPane.showInputDialog(null, "Veuillez indiquer votre motif :", "Motif personnalisé", JOptionPane.PLAIN_MESSAGE);
        }

        return choix;
    }
}
