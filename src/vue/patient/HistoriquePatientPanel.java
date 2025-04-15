package vue.patient;

import dao.UtilisateurDAO;
import controleur.ControleurHistorique;
import modele.RendezVous;
import modele.Utilisateur;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Panel affichant l'historique des rendez-vous du patient connecté.
 *
 * <p>Présente un tableau avec les détails de chaque rendez-vous,
 * incluant le statut, la note et une action pour noter les rendez-vous passés.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class HistoriquePatientPanel extends JPanel {

    /**
     * Constructeur qui initialise l'interface d'historique.
     *
     * @param fenetre la fenêtre principale de l'application
     * @param patient l'utilisateur patient connecté
     */
    public HistoriquePatientPanel(JFrame fenetre, Utilisateur patient) {
        // layout principal et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec infos patient
        add(new HeaderPatient(patient), BorderLayout.NORTH);

        // panneau central blanc
        JPanel panneauCentral = new JPanel(new BorderLayout(10, 10));
        panneauCentral.setBackground(Color.WHITE);
        panneauCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // définition des colonnes
        String[] entetes = {
                "Date", "Spécialiste", "Email", "Téléphone", "Adresse",
                "Spécialité", "Motif", "Statut", "Note", "Action"
        };
        DefaultTableModel modeleTableau = new DefaultTableModel(entetes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // pas d’édition directe
            }
        };
        JTable tableau = new JTable(modeleTableau);
        tableau.setRowHeight(35);
        tableau.setFont(new Font("Arial", Font.PLAIN, 14));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // remplissage du tableau
        List<RendezVous> historique = ControleurHistorique.getHistorique(patient.getId());
        for (RendezVous rdv : historique) {
            Utilisateur spec = UtilisateurDAO.getUtilisateurParId(rdv.getIdSpecialiste());
            String nomSpec   = spec != null ? spec.getPrenom() + " " + spec.getNom() : "[inconnu]";
            String emailSpec = spec != null ? spec.getEmail() : "";
            String telSpec   = spec != null ? spec.getTelephone() : "";
            String adrSpec   = spec != null ? spec.getAdresse() : "";
            String speSpec   = spec != null ? spec.getSpecialite() : "";

            String statut = rdv.getDateHeure().isBefore(LocalDateTime.now()) ? "Passé" : "À venir";
            String note   = rdv.getNote() > 0 ? rdv.getNote() + " ⭐" : "Non noté";
            String action = rdv.getNote() == 0 && statut.equals("Passé") ? "Noter" : "";

            modeleTableau.addRow(new Object[]{
                    rdv.getDateHeure(), nomSpec, emailSpec, telSpec, adrSpec, speSpec,
                    rdv.getMotif(), statut, note, action
            });
        }

        // scroll du tableau
        JScrollPane defileur = new JScrollPane(tableau);
        defileur.setBorder(null);
        panneauCentral.add(defileur, BorderLayout.CENTER);

        // barre de recherche
        JPanel panneauRecherche = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panneauRecherche.setBackground(Color.WHITE);
        panneauRecherche.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JLabel etiquetteRecherche = new JLabel("Recherche : ");
        JTextField champRecherche = new JTextField(20);
        panneauRecherche.add(etiquetteRecherche);
        panneauRecherche.add(champRecherche);
        panneauCentral.add(panneauRecherche, BorderLayout.SOUTH);

        // filtrage dynamique
        TableRowSorter<DefaultTableModel> trieur = new TableRowSorter<>(modeleTableau);
        tableau.setRowSorter(trieur);
        champRecherche.getDocument().addDocumentListener(new DocumentListener() {
            private void majFiltre() {
                String texte = champRecherche.getText();
                if (texte.trim().isEmpty()) {
                    trieur.setRowFilter(null);
                } else {
                    trieur.setRowFilter(RowFilter.regexFilter("(?i)" + texte));
                }
            }
            @Override public void insertUpdate(DocumentEvent e) { majFiltre(); }
            @Override public void removeUpdate(DocumentEvent e) { majFiltre(); }
            @Override public void changedUpdate(DocumentEvent e) { majFiltre(); }
        });

        add(panneauCentral, BorderLayout.CENTER);
        // footer avec navigation historique
        add(new FooterPatient(fenetre, patient, "historique"), BorderLayout.SOUTH);
    }

    /**
     * Affiche une boîte de dialogue pour noter un rendez-vous passé.
     *
     * @return la note choisie (1-5) ou 0 si annulé
     */
    private int demanderNote() {
        String[] options = {"1", "2", "3", "4", "5"};
        int choix = JOptionPane.showOptionDialog(
                this,
                "Notez ce rendez-vous :",
                "Évaluation",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[4]
        );
        return (choix >= 0) ? choix + 1 : 0;
    }
}
