package modele;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String photo;
    private Role role;


    public Utilisateur(int id, String nom, String email, String motdepasse, String role, String photo) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motdepasse;
        this.role = Role.valueOf(role.toUpperCase());
        this.photo = photo;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public String getPhoto() { return photo; }
    public Role getRole() { return role; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setRole(Role role) { this.role = role; }
}
