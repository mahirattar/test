package ma.kapisoft.gjurd.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



@Entity
@Table(name = "condition")
@PrimaryKeyJoinColumn(name = "id")
public class Condition extends Etape {

	
	 private String attribut;
	
	 private String operateur;
	 
	 private String typevaleur;
	 private String valeur;
	 
	 
	 
	 @JoinColumn(name = "etape1", referencedColumnName = "id")
	  @ManyToOne
	 private Etape etape1;
	 
	 @JoinColumn(name = "etape2", referencedColumnName = "id")
	  @ManyToOne
	 private Etape etape2;
	 
	
	
	public Condition() {
		// TODO Auto-generated constructor stub
	}

	public String getAttribut() {
		return attribut;
	}

	public void setAttribut(String attribut) {
		this.attribut = attribut;
	}

	public String getOperateur() {
		return operateur;
	}

	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur1) {
		this.valeur = valeur1;
	}

	

	public String getTypevaleur() {
		return typevaleur;
	}

	public void setTypevaleur(String typevaleur) {
		this.typevaleur = typevaleur;
	}

	public Etape getEtape1() {
		return etape1;
	}

	public void setEtape1(Etape etape1) {
		this.etape1 = etape1;
	}

	public Etape getEtape2() {
		return etape2;
	}

	public void setEtape2(Etape etape2) {
		this.etape2 = etape2;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((attribut == null) ? 0 : attribut.hashCode());
		result = prime * result + ((etape1 == null) ? 0 : etape1.hashCode());
		result = prime * result + ((etape2 == null) ? 0 : etape2.hashCode());
		result = prime * result
				+ ((operateur == null) ? 0 : operateur.hashCode());
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		Condition other = (Condition) obj;
		if (attribut == null) {
			if (other.attribut != null)
				return false;
		} else if (!attribut.equals(other.attribut))
			return false;
		if (etape1 == null) {
			if (other.etape1 != null)
				return false;
		} else if (!etape1.equals(other.etape1))
			return false;
		if (etape2 == null) {
			if (other.etape2 != null)
				return false;
		} else if (!etape2.equals(other.etape2))
			return false;
		
		if (operateur == null) {
			if (other.operateur != null)
				return false;
		} else if (!operateur.equals(other.operateur))
			return false;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
	
		return true;
	}
	
	@Override
	public boolean valide() {
		// TODO Auto-generated method stub
		if(etape1==null ||etape2==null ||this.equals(etape1)||this.equals(etape2)||etape1.equals(etape2))
			return false;
		else
			return true;
	}

}
