package models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Profession",schema = "PUBLIC",catalog = "PUBLIC")
public class Profession {

	
		@Id
	    @Column ( name = "ID" )
	    private Integer id;
	  
	    @Column ( name = "LIBELLE" )
	    private String libelle;
	   
	    @OneToMany ( mappedBy = "professionId" )
	    private Collection<Patients> patientsCollection;
	    
	    @OneToMany ( mappedBy = "professionConjoint" )
	    private Collection<Patients> patientsCollection1;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public Collection<Patients> getPatientsCollection() {
			return patientsCollection;
		}

		public void setPatientsCollection(Collection<Patients> patientsCollection) {
			this.patientsCollection = patientsCollection;
		}

		public Collection<Patients> getPatientsCollection1() {
			return patientsCollection1;
		}

		public void setPatientsCollection1(Collection<Patients> patientsCollection1) {
			this.patientsCollection1 = patientsCollection1;
		}

		public Profession(String libelle, Collection<Patients> patientsCollection,
				Collection<Patients> patientsCollection1) {
			super();
			this.libelle = libelle;
			this.patientsCollection = patientsCollection;
			this.patientsCollection1 = patientsCollection1;
		}

		public Profession() {
			super();
		}
	    
	    
	    
}
