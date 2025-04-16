package vue.specialiste;

import controleur.ControleurRendezVous;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistoriqueSpecialistePanel extends JPanel {
    public HistoriqueSpecialistePanel(JFrame fenetre, Utilisateur specialiste) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        JLabel titre = new JLabel("📜 Historique des consultations", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        String[] colonnes = {"Date", "Patient", "Motif"};
        DefaultTableModel modele = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(modele);
        table.setRowHeight(30);

        ArrayList<RendezVous> rdvs = ControleurRendezVous.getHistoriqueParSpecialiste(specialiste.getId());
        for (RendezVous r : rdvs) {
            modele.addRow(new Object[]{r.getDateHeure(), r.getIdPatient(), r.getMotif()});
        }

        JScrollPane scroll = new JScrollPane(table);
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        centre.setBackground(Color.WHITE);
        centre.add(titre, BorderLayout.NORTH);
        centre.add(scroll, BorderLayout.CENTER);

        add(centre, BorderLayout.CENTER);

        add(new FooterSpecialiste(fenetre, specialiste, "historique"), BorderLayout.SOUTH);
    }
}
