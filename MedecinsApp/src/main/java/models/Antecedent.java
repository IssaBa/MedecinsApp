package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Antecedent", schema = "PUBLIC", catalog = "PUBLIC")
@NamedQueries({
    @NamedQuery(name = "Antecedent.findByClasseName", query = "SELECT a FROM Antecedent a WHERE a.classe.libelle = :libelle")
})
public class Antecedent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String libelle;
    
    @ManyToOne
    @JoinColumn(name = "classe_id")
    private ClasseAntecedent classe;
    
    @Column
    private String cim10;

    public Antecedent() {
    }

    public Antecedent(Long id) {
        this.id = id;
    }

    public Antecedent(String libelle, ClasseAntecedent classe, String cim10) {
        this.libelle = libelle;
        this.classe = classe;
        this.cim10 = cim10;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ClasseAntecedent getClasse() {
        return classe;
    }

    public void setClasse(ClasseAntecedent classe) {
        this.classe = classe;
    }

    public String getCim10() {
        return cim10;
    }

    public void setCim10(String cim10) {
        this.cim10 = cim10;
    }

}
