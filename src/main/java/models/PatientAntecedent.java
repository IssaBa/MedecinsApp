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
@Table(name = "PatientAntecedent",schema = "PUBLIC",catalog = "PUBLIC")
public class PatientAntecedent {

	
	@Id
  
    @Column ( name = "ID" )
    private Integer id;
    @Column ( name = "COMMENTAIRE" )
    private String commentaire;
  
    @Column ( name = "DATE_ENTREE" )
    @Temporal ( TemporalType.DATE )
    private Date dateEntree;
    
    @JoinColumn ( name = "ID_ANTECEDENT" , referencedColumnName = "ID" )
    @ManyToOne ( optional = false )
    private Antecedents idAntecedent;
    
    @JoinColumn ( name = "ID_PATIENT" , referencedColumnName = "ID" )
    @ManyToOne ( optional = false )
    private Patients idPatient;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}

	public Antecedents getIdAntecedent() {
		return idAntecedent;
	}

	public void setIdAntecedent(Antecedents idAntecedent) {
		this.idAntecedent = idAntecedent;
	}

	public Patients getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Patients idPatient) {
		this.idPatient = idPatient;
	}

	public PatientAntecedent(String commentaire, Date dateEntree, Antecedents idAntecedent, Patients idPatient) {
		super();
		this.commentaire = commentaire;
		this.dateEntree = dateEntree;
		this.idAntecedent = idAntecedent;
		this.idPatient = idPatient;
	}

	public PatientAntecedent() {
		super();
	}
    
    
    
    

}
