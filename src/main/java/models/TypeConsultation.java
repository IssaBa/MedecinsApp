package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Profession",schema = "PUBLIC",catalog = "PUBLIC")
public class TypeConsultation {

	
	  	@Id
	    @Column ( name = "ID" )
	    private Integer id;
	    
	    @Column ( name = "LIBELLE" )
	    private String libelle;
	    @OneToMany ( cascade = CascadeType.ALL , mappedBy = "idTypeConsultation" )
	    private Collection<Consultation> consultationCollection;
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
		public Collection<Consultation> getConsultationCollection() {
			return consultationCollection;
		}
		public void setConsultationCollection(Collection<Consultation> consultationCollection) {
			this.consultationCollection = consultationCollection;
		}
		public TypeConsultation(String libelle, Collection<Consultation> consultationCollection) {
			super();
			this.libelle = libelle;
			this.consultationCollection = consultationCollection;
		}
	
		public TypeConsultation() {
			super();
		}
	    
	    
}
