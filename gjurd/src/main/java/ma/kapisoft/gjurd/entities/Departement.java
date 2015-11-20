package ma.kapisoft.gjurd.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "service", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"nom"})})
public class Departement {

	@Id
	@GeneratedValue(strategy = IDENTITY)
   private Integer id; 
	
   @Basic(optional = false)
   @Column(name = "nom", length = 50)
   private String nom;  
	
	@Column(name = "description", length = 550)
   private String description;
	
	 @JoinColumn(name = "responsable", referencedColumnName = "id")
	 @ManyToOne
	 private Utilisateur responsable;
	 
	 @JoinColumn(name = "cabinet", referencedColumnName = "id")
	 @ManyToOne
	 private Cabinet cabinet;
	
	public Departement() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Utilisateur getResponsable() {
		return responsable;
	}

	public void setResponsable(Utilisateur responsable) {
		this.responsable = responsable;
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cabinet == null) ? 0 : cabinet.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
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
		Departement other = (Departement) obj;
		if (cabinet == null) {
			if (other.cabinet != null)
				return false;
		} else if (!cabinet.equals(other.cabinet))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}
	
	

}
