package modele;

import java.time.LocalDateTime;

public class RendezVous {
    private int id;
    private int idPatient;
    private int idSpecialiste;
    private LocalDateTime dateHeure;
    private String motif;
    private int note;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdSpecialiste() { return idSpecialiste; }
    public void setIdSpecialiste(int idSpecialiste) { this.idSpecialiste = idSpecialiste; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", patient=" + idPatient +
                ", specialiste=" + idSpecialiste +
                ", dateHeure=" + dateHeure +
                ", motif='" + motif + '\'' +
                ", note=" + note +
                '}';
    }
}
