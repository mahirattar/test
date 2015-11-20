package ma.kapisoft.gjurd.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;



@Entity  
@Table(name = "profile", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"libelle"})})
public class Profile {

	 @Id
		@GeneratedValue(strategy = IDENTITY)
	    private Integer id;  
	 
	 @Basic(optional = false)
	 @Column(name = "libelle")
	 private String libelle;
	 
	 @Column(name = "descrition")
	 private String descrition;
	 @Column(name = "standard")
	 
	 private Boolean standard;
	 @Column(name = "haveuser")
	 
	 private Boolean haveuser;
	  @JoinTable(name="profile_previlege",   
		        joinColumns = {@JoinColumn(name="profile_id", referencedColumnName="id")},  
		        inverseJoinColumns = {@JoinColumn(name="previlege_id", referencedColumnName="id")}  
		    )  
		    @ManyToMany(fetch = FetchType.EAGER)
	    private List<Previlege> previleges;  //les  les privileges du profile dans l'application

	  @JoinColumn(name = "cabinet", referencedColumnName = "id")
		 @ManyToOne
		 private Cabinet cabinet;
	
	public Profile() {
		// TODO Auto-generated constructor stub
	}



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



	public String getDescrition() {
		return descrition;
	}



	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}



	

	
	public Boolean getStandard() {
		return standard;
	}



	public void setStandard(Boolean standard) {
		this.standard = standard;
	}



	public Boolean getHaveuser() {
		return haveuser;
	}



	public void setHaveuser(Boolean haveuser) {
		this.haveuser = haveuser;
	}



	public Cabinet getCabinet() {
		return cabinet;
	}



	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}



	@XmlTransient
		public List<Previlege> getPrevileges() {
		return previleges;
	}



	public void setPrevileges(List<Previlege> previleges) {
		this.previleges = previleges;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descrition == null) ? 0 : descrition.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
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
		Profile other = (Profile) obj;
		if (descrition == null) {
			if (other.descrition != null)
				return false;
		} else if (!descrition.equals(other.descrition))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		
		return true;
	}
	
	

}
