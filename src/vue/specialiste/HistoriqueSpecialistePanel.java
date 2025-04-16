package vue.specialiste;

import controleur.ControleurRendezVous;
import dao.UtilisateurDAO;
import modele.RendezVous;
import modele.Role;
import modele.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

/**
 * Affiche l’historique des consultations pour un spécialiste.
 *
 * <p>Cette vue présente un tableau listant tous les rendez-vous passés
 * du spécialiste connecté, avec filtrage par mot-clé.</p>
 *
 * @author Nicolas <nicolas.chaudemanche1@gmail.com>
 */
public class HistoriqueSpecialistePanel extends JPanel {

    /**
     * Constructeur qui initialise la vue d’historique du spécialiste.
     *
     * @param fenetre     la fenêtre principale de l’application
     * @param specialiste l’utilisateur spécialiste connecté
     */
    public HistoriqueSpecialistePanel(JFrame fenetre, Utilisateur specialiste) {
        // layout principal en border et fond clair
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f5f7fb"));

        // header avec infos du spécialiste
        add(new HeaderSpecialiste(specialiste), BorderLayout.NORTH);

        // titre de l’historique
        JLabel titre = new JLabel("📜 Historique des consultations", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // définition des colonnes du tableau
        String[] colonnes = {
                "Date", "Nom", "Prénom", "Sexe", "Email", "Téléphone", "Âge", "Poids (kg)",
                "N° Sécurité sociale", "Adresse", "Motif", "Note"
        };

        // modèle de table non éditable
        DefaultTableModel modele = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // pas d’édition directe
            }
        };

        // création et style du tableau
        JTable table = new JTable(modele);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        // récupération de l’historique
        ArrayList<RendezVous> rdvs = ControleurRendezVous.getHistoriqueParSpecialiste(specialiste.getId());

        // remplissage des lignes du tableau
        for (RendezVous r : rdvs) {
            Utilisateur patient = UtilisateurDAO.getUtilisateurParId(r.getIdPatient());
            if (patient != null && patient.getRole() == Role.PATIENT) {
                String nom    = patient.getNom();
                String prenom = patient.getPrenom();
                String sexe   = patient.getSexe();
                String email  = patient.getEmail();
                String tel    = patient.getTelephone();
                Integer age   = patient.getAge();
                Double poids  = patient.getPoids();
                String secu   = patient.getNumeroSecu();
                String addr   = patient.getAdresse();
                String motif  = r.getMotif();
                String note   = r.getNote() > 0 ? r.getNote() + " ⭐" : "Non noté";

                modele.addRow(new Object[]{
                        r.getDateHeure(), nom, prenom, sexe, email, tel,
                        age, poids, secu, addr, motif, note
                });
            }
        }

        // filtrage / recherche live
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modele);
        table.setRowSorter(sorter);

        JTextField champRecherche = new JTextField();
        champRecherche.setPreferredSize(new Dimension(200, 30));
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 14));
        champRecherche.setToolTipText("Rechercher un mot-clé");

        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filtrer() {
                String texte = champRecherche.getText();
                if (texte.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texte));
                }
            }
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
        });

        // barre de recherche à droite
        JPanel barreRecherche = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        barreRecherche.setBackground(Color.WHITE);
        barreRecherche.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        barreRecherche.add(new JLabel("🔍 Rechercher : "));
        barreRecherche.add(champRecherche);

        // scroll pour le tableau
        JScrollPane scroll = new JScrollPane(table);

        // panneau central contenant titre, recherche et tableau
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        centre.add(titre, BorderLayout.NORTH);
        centre.add(barreRecherche, BorderLayout.SOUTH);
        centre.add(scroll, BorderLayout.CENTER);

        // ajout du centre et du footer
        add(centre, BorderLayout.CENTER);
        add(new FooterSpecialiste(fenetre, specialiste, "historique"), BorderLayout.SOUTH);
    }
}
