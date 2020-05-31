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
@Table(name = "Ordonnance", schema = "PUBLIC", catalog = "PUBLIC")
public class Ordonnance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOrdonnance;

    @Column
    private String donnees;

    @JoinColumn(name = "medecin_id", nullable = false)
    @ManyToOne
    private MedecinUser medecin;

    @JoinColumn(name = "patient_id", nullable = false)
    @ManyToOne
    private Patient patient;

    public Ordonnance() {
    }

    public Ordonnance(Long id) {
        this.id = id;
    }

    public Ordonnance(Date dateOrdonnance, String donnees, MedecinUser medecin, Patient patient) {
        this.dateOrdonnance = dateOrdonnance;
        this.donnees = donnees;
        this.medecin = medecin;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOrdonnance() {
        return dateOrdonnance;
    }

    public void setDateOrdonnance(Date dateOrdonnance) {
        this.dateOrdonnance = dateOrdonnance;
    }

    public String getDonnees() {
        return donnees;
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    public MedecinUser getMedecin() {
        return medecin;
    }

    public void setMedecin(MedecinUser medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
