package ma.kapisoft.gjurd.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "action")
@PrimaryKeyJoinColumn(name = "id")
public class Action extends Etape {
    private static final long serialVersionUID = 1L;
    
    
    
	 @Basic(optional = false)
	 @Column(name = "nom", length = 50)
  private String nom;  
	 
	 @Column(name = "description", length = 550)
  private String description;
    
    @JoinColumn(name = "service", referencedColumnName = "id")
  	 @ManyToOne
  	 private Departement service;
    
    @JoinColumn(name = "profile", referencedColumnName = "id")
	  @ManyToOne
	 private Profile profile;
    
    @JoinColumn(name = "etape", referencedColumnName = "id")
	  @ManyToOne
	 private Etape etape;
	
    public Action() {
		// TODO Auto-generated constructor stub
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

	public Departement getService() {
		return service;
	}

	public void setService(Departement service) {
		this.service = service;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	

	public Etape getEtape() {
		return etape;
	}

	public void setEtape(Etape etape) {
		this.etape = etape;
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}

	@Override
	public boolean valide() {
		// TODO Auto-generated method stub
		if(etape==null ||this.equals(etape))
			return false;
		else
			return true;
	}
    
    

}
