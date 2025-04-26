package vue.admin;

import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Panel affichant les rendez-vous d'un patient sélectionné pour l'administrateur.
 *
 * <p>Présente un tableau listant la date, le spécialiste, le motif,
 * le statut et la note de chaque rendez-vous du patient.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class RendezVousPatientAdminPanel extends JPanel {

    /**
     * Constructeur qui initialise l'interface et remplit le tableau des rendez-vous.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param admin   l'utilisateur administrateur connecté
     * @param patient le patient dont on veut afficher les rendez-vous
     */
    public RendezVousPatientAdminPanel(JFrame fenetre, Utilisateur admin, Utilisateur patient) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header admin en haut
        add(new HeaderAdmin(admin), BorderLayout.NORTH);

        // définition des colonnes du tableau
        String[] colonnes = {"Date", "Spécialiste", "Motif", "Statut", "Note"};
        DefaultTableModel modele = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(modele);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // récupération de l'historique du patient
        ArrayList<RendezVous> rdvs = ControleurRendezVous.getHistoriqueParPatient(patient.getId());
        for (RendezVous r : rdvs) {
            String statut = r.getDateHeure().isBefore(LocalDateTime.now()) ? "Passé" : "À venir";
            String note = r.getNote() > 0 ? r.getNote() + " ⭐" : "Non noté";
            Utilisateur spec = UtilisateurDAO.getUtilisateurParId(r.getIdSpecialiste());
            String nomSpec = spec != null ? spec.getNom() : ("Spécialiste ID " + r.getIdSpecialiste());

            modele.addRow(new Object[]{
                    r.getDateHeure(),
                    nomSpec,
                    r.getMotif(),
                    statut,
                    note
            });
        }

        // ajout du tableau avec scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(scrollPane, BorderLayout.CENTER);

        // bouton retour aux patients
        JButton retour = new JButton("⬅ Retour aux patients");
        retour.setFont(new Font("Arial", Font.BOLD, 14));
        retour.setPreferredSize(new Dimension(200, 40));
        retour.addActionListener(e -> {
            fenetre.setContentPane(new ListePatientsPanel(fenetre, admin));
            fenetre.revalidate();
            fenetre.repaint();
        });

        // panel bas pour le bouton retour
        JPanel bas = new JPanel();
        bas.setBackground(Color.decode("#f5f7fb"));
        bas.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bas.add(retour);
        add(bas, BorderLayout.SOUTH);

        // footer admin avec navigation contextuelle
        add(new FooterAdmin(fenetre, admin, "patients"), BorderLayout.SOUTH);
    }
}