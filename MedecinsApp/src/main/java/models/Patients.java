package models;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Patients",schema = "PUBLIC",catalog = "PUBLIC")
public class Patients {

	
		@Id
	    @Column ( name = "ID" )
	    private Integer id;
	    
	    @Column ( name = "PRENOM" )
	    private String prenom;
	    
	    @Column ( name = "NOM" )
	    private String nom;
	    
	    @Column ( name = "DATE_NAISSANCE" )
	    @Temporal ( TemporalType.DATE )
	    private Date dateNaissance;
	   
	    @Column ( name = "LIEU_NAISSANCE" )
	    private String lieuNaissance;
	    
	    @Column ( name = "NUMERO_DOSSIER" )
	    private String numeroDossier;
	    
	    @Column ( name = "CIVILITE" )
	    private String civilite;
	    
	    @Column ( name = "SEXE" )
	    private String sexe;
	    
	    @Column ( name = "CONNU_DEPUIS" )
	    @Temporal ( TemporalType.DATE )
	    private Date connuDepuis;
	    
	    @Column ( name = "ADRESSE" )
	    private String adresse;
	    
	    @Column ( name = "TELEPHONE_DOMICILE" )
	    private String telephoneDomicile;
	    
	    @Column ( name = "TELEPHONE_BUREAU" )
	    private String telephoneBureau;
	    
	    @Column ( name = "TELEPHONE_PORTABLE" )
	    private String telephonePortable;
	    
	    @Column ( name = "PRENOM_CONJOINT" )
	    private String prenomConjoint;
	    
	    @Column ( name = "NOM_CONJOINT" )
	    private String nomConjoint;
	    
	    @Column ( name = "TELEPHONE_CONJOINT" )
	    private String telephoneConjoint;
	    @JoinColumn ( name = "SUIVI_PAR" , referencedColumnName = "ID" )
	  
	    @ManyToOne
	    private MedecinUser suiviPar;
	  
	    @JoinColumn ( name = "PROFESSION_ID" , referencedColumnName = "ID" )
	    @ManyToOne
	    private Profession professionId;
	    
	    @JoinColumn ( name = "PROFESSION_CONJOINT" , referencedColumnName = "ID" )
	    @ManyToOne
	    private Profession professionConjoint;
	    
	    @OneToMany ( cascade = CascadeType.ALL , mappedBy = "idPatient" )
	    private Collection<PatientAntecedent> patientAntecedentCollection;
	    
	    @OneToMany ( cascade = CascadeType.ALL , mappedBy = "idPatient" )
	    private Collection<Consultation> consultationCollection;
	    
	    @OneToMany ( cascade = CascadeType.ALL , mappedBy = "idPatient" )
	    private Collection<Ordonnance> ordonnanceCollection;

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

		public String getCivilite() {
			return civilite;
		}

		public void setCivilite(String civilite) {
			this.civilite = civilite;
		}

		public String getSexe() {
			return sexe;
		}

		public void setSexe(String sexe) {
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

		public MedecinUser getSuiviPar() {
			return suiviPar;
		}

		public void setSuiviPar(MedecinUser suiviPar) {
			this.suiviPar = suiviPar;
		}

		public Profession getProfessionId() {
			return professionId;
		}

		public void setProfessionId(Profession professionId) {
			this.professionId = professionId;
		}

		public Profession getProfessionConjoint() {
			return professionConjoint;
		}

		public void setProfessionConjoint(Profession professionConjoint) {
			this.professionConjoint = professionConjoint;
		}

		public Collection<PatientAntecedent> getPatientAntecedentCollection() {
			return patientAntecedentCollection;
		}

		public void setPatientAntecedentCollection(Collection<PatientAntecedent> patientAntecedentCollection) {
			this.patientAntecedentCollection = patientAntecedentCollection;
		}

		public Collection<Consultation> getConsultationCollection() {
			return consultationCollection;
		}

		public void setConsultationCollection(Collection<Consultation> consultationCollection) {
			this.consultationCollection = consultationCollection;
		}

		public Collection<Ordonnance> getOrdonnanceCollection() {
			return ordonnanceCollection;
		}

		public void setOrdonnanceCollection(Collection<Ordonnance> ordonnanceCollection) {
			this.ordonnanceCollection = ordonnanceCollection;
		}

		public Patients(String prenom, String nom, Date dateNaissance, String lieuNaissance, String numeroDossier,
				String civilite, String sexe, Date connuDepuis, String adresse, String telephoneDomicile,
				String telephoneBureau, String telephonePortable, String prenomConjoint, String nomConjoint,
				String telephoneConjoint, MedecinUser suiviPar, Profession professionId, Profession professionConjoint,
				Collection<PatientAntecedent> patientAntecedentCollection,
				Collection<Consultation> consultationCollection, Collection<Ordonnance> ordonnanceCollection) {
			super();
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
			this.suiviPar = suiviPar;
			this.professionId = professionId;
			this.professionConjoint = professionConjoint;
			this.patientAntecedentCollection = patientAntecedentCollection;
			this.consultationCollection = consultationCollection;
			this.ordonnanceCollection = ordonnanceCollection;
		}

		public Patients() {
			super();
		}
	    
	    

}
