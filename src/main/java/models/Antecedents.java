package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Antecedents",schema = "PUBLIC",catalog = "PUBLIC")
public class Antecedents {

		@Id
		@Column ( name = "id" )
	    private Integer id;

	 	@Column ( name = "LIBELLE" )
	    private String libelle;
	    
	 	@Column ( name = "CIM10" )
	    private String cim10;
	    
	 	@OneToMany ( cascade = CascadeType.ALL , mappedBy = "idAntecedent" )
	    private Collection<PatientAntecedent> patientAntecedentCollection;
	    
	 	@JoinColumn ( name = "CLASSE_ID" , referencedColumnName = "ID" )
	    @ManyToOne
	    private ClasseAntecedent classeId;

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public String getCim10() {
			return cim10;
		}

		public void setCim10(String cim10) {
			this.cim10 = cim10;
		}

		public Collection<PatientAntecedent> getPatientAntecedentCollection() {
			return patientAntecedentCollection;
		}

		public void setPatientAntecedentCollection(Collection<PatientAntecedent> patientAntecedentCollection) {
			this.patientAntecedentCollection = patientAntecedentCollection;
		}

		public ClasseAntecedent getClasseId() {
			return classeId;
		}

		public void setClasseId(ClasseAntecedent classeId) {
			this.classeId = classeId;
		}

		
		public Antecedents(String libelle, String cim10) {
			super();
			this.libelle = libelle;
			this.cim10 = cim10;
		}

		public Antecedents() {
			super();
		}
	 	
	 	

}
