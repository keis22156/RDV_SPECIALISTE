package modele;

/**
 * Modèle représentant un utilisateur de l’application.
 * <p>
 * Contient les informations communes à tous les utilisateurs (id, nom, rôle, etc.)
 * ainsi que les attributs spécifiques aux patients et aux spécialistes.
 * </p>
 */
public class Utilisateur {
    private int id;                   // identifiant unique
    private String nom;               // nom de famille
    private String prenom;            // prénom
    private String email;             // adresse email
    private String motDePasse;        // mot de passe
    private String photo;             // chemin vers la photo de profil
    private Role role;                // rôle (PATIENT, SPECIALISTE, ADMIN)

    // champs spécifiques aux patients
    private String telephone;         // numéro de téléphone
    private Integer age;              // âge
    private Double poids;             // poids en kg
    private String numeroSecu;        // numéro de sécurité sociale
    private String sexe;              // sexe
    private String adresse;           // adresse postale

    // champs spécifiques aux spécialistes
    private String specialite;        // spécialité médicale
    private String description;       // description / biographie

    /**
     * Construit un nouvel utilisateur.
     *
     * @param id          Identifiant unique en base
     * @param nom         Nom de famille
     * @param prenom      Prénom
     * @param email       Adresse email
     * @param motDePasse  Mot de passe
     * @param role        Chaîne du rôle ("PATIENT", "SPECIALISTE", "ADMIN")
     * @param photo       Chemin vers la photo de profil (peut être null)
     */
    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String role, String photo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = Role.valueOf(role.toUpperCase());
        this.photo = photo;
    }

    /** @return l’identifiant unique de l’utilisateur */
    public int getId() { return id; }

    /** @return le nom de famille */
    public String getNom() { return nom; }

    /** @return le prénom */
    public String getPrenom() { return prenom; }

    /** @return l’adresse email */
    public String getEmail() { return email; }

    /** @return le mot de passe */
    public String getMotDePasse() { return motDePasse; }

    /** @return le chemin de la photo de profil */
    public String getPhoto() { return photo; }

    /** @return le rôle de l’utilisateur */
    public Role getRole() { return role; }

    /**
     * Définit le nom de l’utilisateur.
     * @param nom Nouveau nom
     */
    public void setNom(String nom) { this.nom = nom; }

    /**
     * Définit le prénom de l’utilisateur.
     * @param prenom Nouveau prénom
     */
    public void setPrenom(String prenom) { this.prenom = prenom; }

    /**
     * Définit l’adresse email de l’utilisateur.
     * @param email Nouvelle adresse email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Définit le mot de passe de l’utilisateur.
     * @param motDePasse Nouveau mot de passe
     */
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    /**
     * Définit la photo de profil de l’utilisateur.
     * @param photo Chemin vers la nouvelle photo
     */
    public void setPhoto(String photo) { this.photo = photo; }

    /**
     * Définit le rôle de l’utilisateur.
     * @param role Nouveau rôle (enum Role)
     */
    public void setRole(Role role) { this.role = role; }

    // --- Getters / setters spécifiques aux patients ---

    /** @return le numéro de téléphone du patient */
    public String getTelephone() { return telephone; }

    /**
     * Définit le numéro de téléphone du patient.
     * @param telephone Numéro de téléphone
     */
    public void setTelephone(String telephone) { this.telephone = telephone; }

    /** @return l’âge du patient */
    public Integer getAge() { return age; }

    /**
     * Définit l’âge du patient.
     * @param age Âge en années
     */
    public void setAge(Integer age) { this.age = age; }

    /** @return le poids du patient en kilogrammes */
    public Double getPoids() { return poids; }

    /**
     * Définit le poids du patient.
     * @param poids Poids en kilogrammes
     */
    public void setPoids(Double poids) { this.poids = poids; }

    /** @return le numéro de sécurité sociale du patient */
    public String getNumeroSecu() { return numeroSecu; }

    /**
     * Définit le numéro de sécurité sociale du patient.
     * @param numeroSecu Numéro de sécurité sociale
     */
    public void setNumeroSecu(String numeroSecu) { this.numeroSecu = numeroSecu; }

    /** @return le sexe du patient */
    public String getSexe() { return sexe; }

    /**
     * Définit le sexe du patient.
     * @param sexe Sexe (par exemple "Homme" ou "Femme")
     */
    public void setSexe(String sexe) { this.sexe = sexe; }

    /** @return l’adresse postale du patient */
    public String getAdresse() { return adresse; }

    /**
     * Définit l’adresse postale du patient.
     * @param adresse Nouvelle adresse
     */
    public void setAdresse(String adresse) { this.adresse = adresse; }

    // --- Getters / setters spécifiques aux spécialistes ---

    /** @return la spécialité médicale du spécialiste */
    public String getSpecialite() { return specialite; }

    /**
     * Définit la spécialité médicale du spécialiste.
     * @param specialite Spécialité (ex. "Dermatologie")
     */
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    /** @return la description / biographie du spécialiste */
    public String getDescription() { return description; }

    /**
     * Définit la description / biographie du spécialiste.
     * @param description Texte libre de présentation
     */
    public void setDescription(String description) { this.description = description; }
}