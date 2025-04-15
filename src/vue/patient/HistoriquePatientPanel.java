package vue.patient;

import controleur.ControleurHistorique;
import controleur.ControleurRendezVous;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HistoriquePatientPanel extends JPanel {
    public HistoriquePatientPanel(JFrame fenetre, Utilisateur patient) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        add(new HeaderPatient(patient), BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        String[] colonnes = {"Date", "Spécialiste", "Motif", "Statut", "Note", "Action"};
        DefaultTableModel modeleTable = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(modeleTable);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);

        ArrayList<RendezVous> rdvs = ControleurHistorique.getHistorique(patient.getId());
        for (RendezVous r : rdvs) {
            String statut = r.getDateHeure().isBefore(LocalDateTime.now()) ? "Passé" : "À venir";
            String note = r.getNote() > 0 ? r.getNote() + " ⭐" : "Non noté";
            String action = (r.getNote() == 0 && statut.equals("Passé")) ? "Noter" : "";
            modeleTable.addRow(new Object[]{r.getDateHeure(), r.getIdSpecialiste(), r.getMotif(), statut, note, action});
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 5 && table.getValueAt(row, col).equals("Noter")) {
                    String date = table.getValueAt(row, 0).toString();
                    int idSpec = Integer.parseInt(table.getValueAt(row, 1).toString());
                    int note = demanderNote();
                    if (note > 0) {
                        ControleurRendezVous.noterRendezVous(patient.getId(), date, idSpec, note);
                        table.setValueAt(note + " ⭐", row, 4);
                        table.setValueAt("✔", row, 5);
                    }
                }
            }
        });

        centre.add(scroll, BorderLayout.CENTER);
        add(centre, BorderLayout.CENTER);

        JButton retour = new JButton("⬅ Retour au tableau de bord");
        retour.addActionListener(e -> {
            fenetre.setContentPane(new TableauDeBordPatient(fenetre, patient));
            fenetre.revalidate();
            fenetre.repaint();
        });

        JPanel bas = new JPanel();
        bas.setBackground(Color.decode("#f5f7fb"));
        bas.add(retour);
        add(bas, BorderLayout.SOUTH);
        add(new FooterPatient(fenetre, patient, "historique"), BorderLayout.SOUTH);
    }

    private int demanderNote() {
        String[] options = {"1", "2", "3", "4", "5"};
        int choix = JOptionPane.showOptionDialog(null,
                "Notez ce rendez-vous :",
                "Évaluation",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[4]);
        return (choix >= 0) ? choix + 1 : 0;
    }

}
