package models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Consultation",schema = "PUBLIC",catalog = "PUBLIC")
public class Consultation {
	
	
	  	@Id
	    @Column ( name = "ID" )
	    private Integer id;
	  
	  	@Column ( name = "DATE_CONSULTATION" )
	    @Temporal ( TemporalType.TIMESTAMP )
	    private Date dateConsultation;
	   
	  	@Column ( name = "DONNEES" )
	    private String donnees;
	  	
	    @JoinColumn ( name = "ID_MEDECIN" , referencedColumnName = "ID" )
	    @ManyToOne
	    private MedecinUser idMedecin;
	    
	    @JoinColumn ( name = "ID_PATIENT" , referencedColumnName = "ID" )
	    @ManyToOne ( optional = false )
	    private Patients idPatient;
	    
	    @JoinColumn ( name = "ID_TYPE_CONSULTATION" , referencedColumnName = "ID" )
	    @ManyToOne ( optional = false )
	    private TypeConsultation idTypeConsultation;

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

		public MedecinUser getIdMedecin() {
			return idMedecin;
		}

		public void setIdMedecin(MedecinUser idMedecin) {
			this.idMedecin = idMedecin;
		}

		public Patients getIdPatient() {
			return idPatient;
		}

		public void setIdPatient(Patients idPatient) {
			this.idPatient = idPatient;
		}

		public TypeConsultation getIdTypeConsultation() {
			return idTypeConsultation;
		}

		public void setIdTypeConsultation(TypeConsultation idTypeConsultation) {
			this.idTypeConsultation = idTypeConsultation;
		}

		public Consultation(Date dateConsultation, String donnees, MedecinUser idMedecin, Patients idPatient,
				TypeConsultation idTypeConsultation) {
			super();
			this.dateConsultation = dateConsultation;
			this.donnees = donnees;
			this.idMedecin = idMedecin;
			this.idPatient = idPatient;
			this.idTypeConsultation = idTypeConsultation;
		}

		public Consultation() {
			super();
		}

	    

}
