/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.kapisoft.gjurd.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mahir
 */
@Entity
@Table(name = "consultation")

public class Consultation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic(optional = false)
	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "nom", length = 50)
	private String nom;

	@Column(name = "prenom", length = 50)
	private String prenom;

	@Column(name = "tel", length = 50)
	private String tel;

	@Basic(optional = false)
	@Column(name = "question", length = 1000)
	private String question;

	@Column(name = "cloture")
	private Boolean cloture;

	@Column(name = "datecreation")
	private Date datecreation;

	@Column(name = "datecloture")
	private Date datecloture;

	@JoinColumn(name = "utilisateur", referencedColumnName = "id")
	@ManyToOne
	private Utilisateur utilisateur;

	@JoinTable(name="consultation_message",   
			joinColumns = {@JoinColumn(name="consultation_id", referencedColumnName="id")},  
			inverseJoinColumns = {@JoinColumn(name="message_id", referencedColumnName="id")}  
			)  
	@OneToMany(fetch = FetchType.EAGER)
	private List<Message> message;  //les messgaes de la consultation dans l'application


	public Consultation() {
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Boolean getCloture() {
		return cloture;
	}

	public void setCloture(Boolean cloture) {
		this.cloture = cloture;
	}

	public Date getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}

	public Date getDatecloture() {
		return datecloture;
	}

	public void setDatecloture(Date datecloture) {
		this.datecloture = datecloture;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Message> getMessage() {
		return message;
	}

	public void setMessage(List<Message> message) {
		this.message = message;
	}

	public Consultation(Integer id) {
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((cloture == null) ? 0 : question.hashCode());
		result = prime * result + ((datecloture == null) ? 0 : question.hashCode());
		result = prime * result + ((datecreation == null) ? 0 : datecreation.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		Consultation other = (Consultation) obj;
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
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (cloture == null) {
			if (other.cloture != null)
				return false;
		} else if (!cloture.equals(other.cloture))
			return false;
		if (datecloture == null) {
			if (other.datecloture != null)
				return false;
		} else if (!datecloture.equals(other.datecloture))
			return false;
		if (datecreation == null) {
			if (other.datecreation != null)
				return false;
		} else if (!datecreation.equals(other.datecreation))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Consultation[ id=" + id + " ]";
	}

}
