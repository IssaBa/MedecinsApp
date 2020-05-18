package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Patient_Antecedent", schema = "PUBLIC", catalog = "PUBLIC")
public class PatientAntecedent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String commentaire;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateEntree;

    @ManyToOne
    @JoinColumn(name = "antecedent_id", nullable = false)
    private Antecedent antecedent;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public PatientAntecedent() {
    }

    public PatientAntecedent(Long id) {
        this.id = id;
    }

    public PatientAntecedent(String commentaire, Date dateEntree, Antecedent antecedent, Patient patient) {
        this.commentaire = commentaire;
        this.dateEntree = dateEntree;
        this.antecedent = antecedent;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public Antecedent getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(Antecedent antecedent) {
        this.antecedent = antecedent;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
