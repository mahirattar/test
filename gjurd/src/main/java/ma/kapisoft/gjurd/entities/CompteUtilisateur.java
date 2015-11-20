package ma.kapisoft.gjurd.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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
@Table(name = "compteutilisateur", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})
public class CompteUtilisateur {
	 @Id
		@GeneratedValue(strategy = IDENTITY)
	    private Integer id; 
	@Basic(optional = false)
	 @Column(name = "login", length = 50)
	    private String login;
	@Basic(optional = false)
	 @Column(name = "password", length = 100)
	    private String password;
	 @Column(name = "actif")
	    private Boolean actif;
	 @Column(name = "datepassword")
	 private Date datepassword;
	 @JoinColumn(name = "utilisateur", referencedColumnName = "id")
	  @ManyToOne
	 private Utilisateur utilisateur;
	 @JoinColumn(name = "profile", referencedColumnName = "id")
	  @ManyToOne
	 private Profile profile;
	 

	public CompteUtilisateur() {
		// TODO Auto-generated constructor stub
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}


	public Date getDatepassword() {
		return datepassword;
	}


	public void setDatepassword(Date datepassword) {
		this.datepassword = datepassword;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Profile getProfile() {
		return profile;
	}


	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime * result
				+ ((datepassword == null) ? 0 : datepassword.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result
				+ ((utilisateur == null) ? 0 : utilisateur.hashCode());
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
		CompteUtilisateur other = (CompteUtilisateur) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (datepassword == null) {
			if (other.datepassword != null)
				return false;
		} else if (!datepassword.equals(other.datepassword))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}



	
	

}
