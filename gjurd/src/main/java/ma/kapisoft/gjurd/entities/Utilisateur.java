/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.kapisoft.gjurd.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author khalid
 */
@Entity
@Table(name = "utilisateur", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email", length = 50)
  
    private String email;
    @Basic(optional = false)
    @Column(name = "nom", length = 50)
    private String nom;
    
    @Basic(optional = false)
    @Column(name = "prenom", length = 50)
    private String prenom;

     @JoinColumn(name = "service", referencedColumnName = "id")
	 @ManyToOne
	 private Departement service;
     
     
  
  
    
    public Utilisateur() {
    }
    
 

    public Utilisateur(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

   
	public void setId(Integer id) {
        this.id = id;
    }

 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

    

	public Departement getService() {
		return service;
	}



	public void setService(Departement service) {
		this.service = service;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
		Utilisateur other = (Utilisateur) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}



	@Override
    public String toString() {
        return "Utilisateur[ id=" + id + " ]";
    }
    
}
