package models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ClasseAntecedent",schema = "PUBLIC",catalog = "PUBLIC")
public class ClasseAntecedent {

	@Id
    @Column ( name = "ID" )
    private Integer id;
   
	@Column ( name = "LIBELLE" )
    private String libelle;
    
	@OneToMany ( mappedBy = "classeId" )
    private Collection<Antecedents> antecedentsCollection;

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

	public Collection<Antecedents> getAntecedentsCollection() {
		return antecedentsCollection;
	}

	public void setAntecedentsCollection(Collection<Antecedents> antecedentsCollection) {
		this.antecedentsCollection = antecedentsCollection;
	}

	public ClasseAntecedent(String libelle, Collection<Antecedents> antecedentsCollection) {
		super();
		this.libelle = libelle;
		this.antecedentsCollection = antecedentsCollection;
	}

	public ClasseAntecedent() {
		super();
	}
	
	
	
}
