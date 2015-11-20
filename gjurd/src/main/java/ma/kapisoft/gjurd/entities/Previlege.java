package ma.kapisoft.gjurd.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * permet d'enregistrer les infos d'un role (Privilèges d’un utilisateur sur l’application)
 */
@Entity  
@Table(name = "previlege", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"nom"})})
public class Previlege implements java.io.Serializable{  
     
	 @Id
	@GeneratedValue(strategy = IDENTITY)
    private Integer id; 
	 @Column(name = "nom", length = 50)
    private String nom;  
	 @Column(name = "description", length = 50)
    private String description;
      
    
    
    //getters and setters
   
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
	
	public String getType()
	{
		return nom.substring(nom.indexOf("_")+1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		Previlege other = (Previlege) obj;
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
		return true;
	}  
  
    
   
      
}  
