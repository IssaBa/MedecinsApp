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
@Table(name = "Consultation", schema = "PUBLIC", catalog = "PUBLIC")
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateConsultation;

    @Column
    private String donnees;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "type_consultation_id", nullable = false)
    private TypeConsultation typeConsultation;

    public Consultation(Integer id) {
        this.id = id;
    }

    public Consultation(Date dateConsultation, String donnees, Patient patient, TypeConsultation typeConsultation) {
        this.dateConsultation = dateConsultation;
        this.donnees = donnees;
        this.patient = patient;
        this.typeConsultation = typeConsultation;
    }

    public Consultation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public String getDonnees() {
        return donnees;
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public TypeConsultation getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(TypeConsultation typeConsultation) {
        this.typeConsultation = typeConsultation;
    }
}
