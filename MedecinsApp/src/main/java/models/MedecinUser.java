package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Medecin_User", schema = "PUBLIC", catalog = "PUBLIC")
public class MedecinUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String prenom;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;

    public MedecinUser() {
    }

    public MedecinUser(Long id) {
        this.id = id;
    }

    public MedecinUser(String prenom, String nom, String username, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.username = username;
        this.password = password;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
