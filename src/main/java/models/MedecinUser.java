package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MedecinUsers",schema = "PUBLIC",catalog = "PUBLIC")
public class MedecinUser {

	@Id
	@Column ( name = "ID" )
    private Integer id;
    
	@Column ( name = "PRENOM" )
    private String prenom;
    
	@Column ( name = "NOM" )
    private String nom;
    
	@Column ( name = "MATRICULE" )
    private String matricule;
    
	@Column ( name = "USERNAME" )
    private String username;
    
	@Column ( name = "PASSWORD" )
    private String password;
    
	@OneToMany ( mappedBy = "suiviPar" )
    private Collection<Patients> patientsCollection;
    
	@OneToMany ( mappedBy = "idMedecin" )
    private Collection<Consultation> consultationCollection;
    
	@OneToMany ( cascade = CascadeType.ALL , mappedBy = "idMedecin" )
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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
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

	public Collection<Patients> getPatientsCollection() {
		return patientsCollection;
	}

	public void setPatientsCollection(Collection<Patients> patientsCollection) {
		this.patientsCollection = patientsCollection;
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

	public MedecinUser(String prenom, String nom, String matricule, String username, String password,
			Collection<Patients> patientsCollection, Collection<Consultation> consultationCollection,
			Collection<Ordonnance> ordonnanceCollection) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.matricule = matricule;
		this.username = username;
		this.password = password;
		this.patientsCollection = patientsCollection;
		this.consultationCollection = consultationCollection;
		this.ordonnanceCollection = ordonnanceCollection;
	}

	public MedecinUser() {
		super();
	}
	
	
	
	
}
