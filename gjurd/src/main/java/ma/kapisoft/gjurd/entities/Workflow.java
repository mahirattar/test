package ma.kapisoft.gjurd.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "workflow", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"nom"})})
public class Workflow {
	
	public static String DRAFT="DRAFT"; 
	
	

	 @Id
	@GeneratedValue(strategy = IDENTITY)
  private Integer id; 
	 
	 @Basic(optional = false)
	 @Column(name = "nom", length = 50)
  private String nom;  
	 
	 @JoinColumn(name = "cabinet", referencedColumnName = "id")
	 @ManyToOne
	 private Cabinet cabinet;
	 
	 @Basic(optional = false)
	 @Column(name = "valide") 
	 private Boolean valide; 
	
	 
	 @Basic(optional = false)
	 @Column(name = "etat", length = 50) 
	 private String etat; 
	 
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "workflow")
	Set<Etape> etapes;
	 
	 @Column(name = "description", length = 550)
	  private String description;
	
	public Workflow() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	public Set<Etape> getEtapes() {
		return etapes;
	}

	public void setEtapes(Set<Etape> etapes) {
		this.etapes = etapes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workflow other = (Workflow) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
	
	

}
