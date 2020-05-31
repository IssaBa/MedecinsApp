package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Patient", schema = "PUBLIC", catalog = "PUBLIC")
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
})
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column
    private String lieuNaissance;

    @Column
    private String numeroDossier;

    @Column
    @Enumerated(EnumType.STRING)
    private CiviliteEnum civilite;

    @Column
    @Enumerated(EnumType.STRING)
    private SexeEnum sexe;

    @Column
    @Temporal(TemporalType.DATE)
    private Date connuDepuis;

    @Column
    private String adresse;

    @Column
    private String telephoneDomicile;

    @Column
    private String telephoneBureau;

    @Column
    private String telephonePortable;

    @Column
    private String prenomConjoint;

    @Column
    private String nomConjoint;

    @Column
    private String telephoneConjoint;

    @ManyToOne
    @JoinColumn
    private MedecinUser medecinTraitant;

    @ManyToOne
    @JoinColumn
    private Profession profession;

    @ManyToOne
    @JoinColumn
    private Profession professionConjoint;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(String prenom, String nom, Date dateNaissance, String lieuNaissance, String numeroDossier, CiviliteEnum civilite, SexeEnum sexe, Date connuDepuis, String adresse, String telephoneDomicile, String telephoneBureau, String telephonePortable, String prenomConjoint, String nomConjoint, String telephoneConjoint, MedecinUser medecinTraitant, Profession profession, Profession professionConjoint) {
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.numeroDossier = numeroDossier;
        this.civilite = civilite;
        this.sexe = sexe;
        this.connuDepuis = connuDepuis;
        this.adresse = adresse;
        this.telephoneDomicile = telephoneDomicile;
        this.telephoneBureau = telephoneBureau;
        this.telephonePortable = telephonePortable;
        this.prenomConjoint = prenomConjoint;
        this.nomConjoint = nomConjoint;
        this.telephoneConjoint = telephoneConjoint;
        this.medecinTraitant = medecinTraitant;
        this.profession = profession;
        this.professionConjoint = professionConjoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public CiviliteEnum getCivilite() {
        return civilite;
    }

    public void setCivilite(CiviliteEnum civilite) {
        this.civilite = civilite;
    }

    public SexeEnum getSexe() {
        return sexe;
    }

    public void setSexe(SexeEnum sexe) {
        this.sexe = sexe;
    }

    public Date getConnuDepuis() {
        return connuDepuis;
    }

    public void setConnuDepuis(Date connuDepuis) {
        this.connuDepuis = connuDepuis;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephoneDomicile() {
        return telephoneDomicile;
    }

    public void setTelephoneDomicile(String telephoneDomicile) {
        this.telephoneDomicile = telephoneDomicile;
    }

    public String getTelephoneBureau() {
        return telephoneBureau;
    }

    public void setTelephoneBureau(String telephoneBureau) {
        this.telephoneBureau = telephoneBureau;
    }

    public String getTelephonePortable() {
        return telephonePortable;
    }

    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public String getPrenomConjoint() {
        return prenomConjoint;
    }

    public void setPrenomConjoint(String prenomConjoint) {
        this.prenomConjoint = prenomConjoint;
    }

    public String getNomConjoint() {
        return nomConjoint;
    }

    public void setNomConjoint(String nomConjoint) {
        this.nomConjoint = nomConjoint;
    }

    public String getTelephoneConjoint() {
        return telephoneConjoint;
    }

    public void setTelephoneConjoint(String telephoneConjoint) {
        this.telephoneConjoint = telephoneConjoint;
    }

    public MedecinUser getMedecinTraitant() {
        return medecinTraitant;
    }

    public void setMedecinTraitant(MedecinUser medecinTraitant) {
        this.medecinTraitant = medecinTraitant;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Profession getProfessionConjoint() {
        return professionConjoint;
    }

    public void setProfessionConjoint(Profession professionConjoint) {
        this.professionConjoint = professionConjoint;
    }

}
