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
@Table(name = "Ordonnance",schema = "PUBLIC",catalog = "PUBLIC")
public class Ordonnance {

	 	@Id
	    @Column ( name = "ID" )
	    private Integer id;
	  
	 	@Column ( name = "DATE_ORDONNANCE" )
	    @Temporal ( TemporalType.TIMESTAMP )
	    private Date dateOrdonnance;
	 
	    @Column ( name = "DONNEES" )
	    private String donnees;
	  
	    @JoinColumn ( name = "ID_MEDECIN" , referencedColumnName = "ID" )
	    @ManyToOne ( optional = false )
	    private MedecinUser idMedecin;
	    
	    @JoinColumn ( name = "ID_PATIENT" , referencedColumnName = "ID" )
	    @ManyToOne ( optional = false )
	    private Patients idPatient;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
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

		public Ordonnance(Date dateOrdonnance, String donnees, MedecinUser idMedecin, Patients idPatient) {
			super();
			this.dateOrdonnance = dateOrdonnance;
			this.donnees = donnees;
			this.idMedecin = idMedecin;
			this.idPatient = idPatient;
		}

		public Ordonnance() {
			super();
		}
	    
	    
	    
}
